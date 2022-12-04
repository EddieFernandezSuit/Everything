import csv
import pandas as pd
import math
import pygame
import sys
import io
from mtgsdk import Card
from urllib.request import urlopen
from datetime import date
import re
import time
import datetime
# s = "01-12-2011"
# t = time.mktime(datetime.datetime.strptime(s, "%m-%d-%Y").timetuple())
# print(t)

# pyinstaller --onefile main.py
class Color:
    GREY = (150, 150, 150)
    BLACK = (0, 0, 0)
    RED = (255, 0, 0)
    GREEN = (0, 255, 0)
    BLUE = (0, 0, 255)
    YELLOW = (255, 255, 0)
    CYAN = (0, 255, 255)
    PINK = (220, 20, 60)
    PURPLE = (138, 43, 226)
    ORANGE = (255, 165, 0)
    WHITE = (255, 255, 255)

class Game:
    def __init__(self):
        pygame.init()
        self.SCREEN_WIDTH = 1600
        self.SCREEN_HEIGHT = 700
        self.screen = pygame.display.set_mode((self.SCREEN_WIDTH, self.SCREEN_HEIGHT))
        self.clock = pygame.time.Clock()
        self.colors = Color()
        self.fontSize = 20
        self.myFont = pygame.font.SysFont("freesansbold", self.fontSize)
        start(self)
        while True:
            update(self)
            draw(self)
            self.clock.tick(60)

def getProf():
    sales = pd.read_csv("sales.csv")
    profits = {}

    for x in range(-1, 71):
        profits[str(x)] = 0

    for x in sales.index:
        profits[str(int(sales["Lot"][x]))] += sales["Price"][x]
    
    allData = []
    for key in profits:
        data = {}
        data['Lot'] = key
        data['Profit'] = round(profits[key],2)
        allData.append(data)

    dfProfits = pd.DataFrame(allData)
    dfProfits.to_csv('profits.csv', index=False, header=True)

def replace(addCards):
    addCards["Name"] = addCards["Name"].str.replace(' - C', '')
    addCards["Name"] = addCards["Name"].str.replace(' - L', '')
    addCards["Name"] = addCards["Name"].str.replace(' - R', '')
    addCards["Name"] = addCards["Name"].str.replace(' - U', '')
    addCards["Name"] = addCards["Name"].str.replace(' - T', '')
    for x in reversed(range(500)):
        temp = ' - #' + str(x)
        addCards["Name"] = addCards["Name"].str.replace(temp, '')

def orderSets():
    newCards = pd.read_csv("new.csv")
    setOrder = pd.read_csv("setOrder.csv")

    setOrderRows = setOrder.index
    newCardsRows = newCards.index

    for x in setOrderRows:
        for y in newCardsRows:
            if newCards['Set'][y] == setOrder['Set'][x]:
                newRow = {'Product Line': newCards['Product Line'][y], 'Product Name': newCards['Product Name'][y], 'Condition': newCards['Condition'][y], 'Number': newCards['Number'][y],
                                'Set': newCards['Set'][y], 'Rarity': newCards['Rarity'][y], 'Quantity': newCards['Quantity'][y], 'Main Photo URL': newCards['Main Photo URL'][y]}
                newCards = newCards.append(newRow, ignore_index=True)

    for x in newCardsRows:
        newCards = newCards.drop([x])

    newCards.to_csv("new.csv", index=False)

def removeParentheses(str):
    return re.sub(r"\([^()]*\)", "", str)

def newHeaders():
    csvFile = pd.read_csv("new.csv")
    csvFile = csvFile.rename(columns={"Quantity":"Add to Quantity","Name":"Product Name","Set":"Set Name","SKU":"TCGplayer Id","Price Each":"TCG Marketplace Price"})
    
    def addEmptyColumns(csvFile, arr):
        emptyColumn = []
        for x in csvFile.index:
            emptyColumn.append('')
        for x in arr:
            csvFile[x] = emptyColumn

    addEmptyColumns(csvFile, ["Product Line", "Title", "Number", "Rarity", "TCG Market Price","TCG Direct Low","TCG Low Price With Shipping","TCG Low Price","Total Quantity","Photo URL"])
    for x in csvFile.index:
        csvFile["Product Line"][x] = 'Magic'
        if csvFile["Printing"][x] != 'Normal':
            csvFile["Condition"][x] += ' ' + csvFile["Printing"][x]

        if csvFile["Language"][x] != 'English':
            csvFile["Condition"][x] += ' - ' + csvFile["Language"][x]
        csvFile["TCG Marketplace Price"][x] = csvFile["TCG Marketplace Price"][x][1:]
    
    csvFile.to_csv("new.csv", index = False)

def addLot(lotStr):
    csvFile = pd.read_csv("new.csv")
    newColumn = []
    for x in csvFile.index:
        newColumn.append(lotStr)
        csvFile["Price Each"][x] = '$' + str((float(csvFile["Price Each"][x][1:]) * 1))
    csvFile["Lot"] = newColumn

    inventory = pd.read_csv("inventory.csv")
    for x in csvFile.index:
        newRow = {'Quantity': csvFile["Quantity"][x], 'Name': csvFile["Name"][x], 'Set': csvFile["Set"][x], 'Foil': csvFile["Printing"][x],
            'Condition': csvFile["Condition"][x], 'Language': csvFile["Language"][x], 'SKU': csvFile["SKU"][x], 'Price': csvFile["Price Each"][x], "Lot": csvFile["Lot"][x]}
        inventory = inventory.append(newRow, ignore_index=True)
    inventory.to_csv("inventory.csv", index = False)
    csvFile.to_csv("new.csv", index = False)

def removeUnder10Cents():
    csvFile = pd.read_csv("new.csv")
    for x in csvFile.index:
        if float(csvFile["Price Each"][x][1:]) < .1:
            csvFile = csvFile.drop([x])
    csvFile.to_csv("new.csv", index = False)

def update(game):
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN and event.key == pygame.K_SPACE:
            print("Finding Cards")
            shipType = 'normal'
            
            data = ""
            with open("new.csv") as file:
                data = file.read().replace(":","")

            with open("new.csv", 'w') as file:
                file.write(data)
        
            for j in range(game.NUM_CARDS_ROW * 2):
            # for j in range(1):
                newCards = pd.read_csv("new.csv")
                for column in newCards.columns:
                    if column == 'Card Name' or column == 'Product Name':
                        if column == 'Card Name':
                            shipType = 'direct'
                        newCards = newCards.rename(columns={column: 'Name'})
                    elif column == 'Set Name':
                        newCards = newCards.rename(columns={column: 'Set'})
                
                if len(newCards.index) <= 1:
                    print('Complete')
                    break

                MTGCard = {
                    'name': newCards["Name"][0],
                    'set': newCards["Set"][0],
                    'condition': newCards["Condition"][0],
                    'quantity': int(newCards["Quantity"][0])
                }

                for x in range(MTGCard['quantity']):
                    inventory = pd.read_csv("inventory.csv")
                    newCards.loc[0, "Quantity"] = str(int(MTGCard['quantity']) - 1)
                    index = None
                    for i in range(len(inventory["Name"])):
                        if inventory["Name"][i] == MTGCard['name'] and inventory["Set"][i] == MTGCard['set']:
                            if MTGCard['condition'][-4:] == "Foil":
                                if inventory["Foil"][i] == "Foil":
                                    index = i
                            else:
                                if inventory["Condition"][i] == MTGCard['condition']:
                                    index = i
                    if index == None:
                        price = 0
                        MTGCard['lot'] = -1
                    else:
                        inventory.loc[index, "Quantity"] = str(int(inventory["Quantity"][index]) - 1)
                        price = float(inventory["Price"][index][1:])
                        MTGCard['lot'] = int(math.floor(float(inventory["Lot"][index])))
                        if int(inventory["Quantity"][index]) <= 0:
                            inventory = inventory.drop([index])
                    
                    newRow = {'Name': MTGCard['name'], 'Set': MTGCard['set'], 'Condition': MTGCard['condition'], 'Lot': MTGCard['lot'],
                                'Price': price, 'Date': date.today(), 'shipType': shipType}
                    sales = pd.read_csv("sales.csv")
                    sales = pd.concat([sales, pd.DataFrame.from_records([newRow])])
                    sales.to_csv("sales.csv", index=False)
                    inventory.to_csv("inventory.csv", index=False)
                
                MTGCard['image'] = getImage(MTGCard['name'], MTGCard['set'])
                newCards = newCards.drop([0])
                newCards.to_csv("new.csv", index=False)
                game.MTGCards.append(MTGCard)
                if len(game.MTGCards) > game.NUM_CARDS_ROW * 2:
                    game.MTGCards.pop(0)
                draw(game)

def getImage(cardName, set):
    cardName = removeParentheses(cardName).rstrip()
    print(cardName)
    set = removeParentheses(set).rstrip()
    cards = Card.where(name=cardName).all()
    image_url = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=491806&type=card"

    for card in cards:
        if card.set_name == set:
            image_url = card.image_url
            break

    image_str = urlopen(image_url).read()
    image_file = io.BytesIO(image_str)  # create a file object (stream)
    image = pygame.image.load(image_file)
    image = pygame.transform.scale(image, (150, 210))
    return image

def drawText(game, text, x, y):
    game.screen.blit(game.myFont.render(str(text), 1, game.colors.BLACK),(x, y))

def draw(game):
    game.screen = pygame.display.set_mode((game.SCREEN_WIDTH, game.SCREEN_HEIGHT))
    game.screen.fill(game.colors.GREY)
    xDistance = 155
    yDistance = 280
    ySperation = 15
    cardAttributes = ['quantity', 'lot', 'name', 'set', 'condition']
    cardAttributesShort = ['Qty: ', 'Lot: ', '', '', '']
    for i in range(min(game.NUM_CARDS_ROW, len(game.MTGCards))):
        for j in range(len(cardAttributes)):
            drawText(game, cardAttributesShort[j]  + str(game.MTGCards[i][cardAttributes[j]]), ySperation + xDistance * i, ySperation * (j + 1))
        game.screen.blit(game.MTGCards[i]['image'], (ySperation + xDistance * i, ySperation * len(cardAttributesShort)))
    
    if len(game.MTGCards) > game.NUM_CARDS_ROW:
        for i in range(game.NUM_CARDS_ROW, len(game.MTGCards)):
            for j in range(len(cardAttributes)):
                drawText(game, cardAttributesShort[j] + str(game.MTGCards[i][cardAttributes[j]]), ySperation + xDistance * (i - game.NUM_CARDS_ROW), ySperation * (j + 1) + yDistance)
            game.screen.blit(game.MTGCards[i]['image'], (ySperation + xDistance * (i - game.NUM_CARDS_ROW), ySperation * len(cardAttributesShort) + yDistance))
    pygame.display.update()

def start(game):
    game.MTGCards = []
    game.NUM_CARDS_ROW = 10

def findIn(filePath1, filePath2):
    inventory = pd.read_csv(filePath1)
    findCards = pd.read_csv(filePath2)
    for x in findCards.index:
        sum = 0
        for y in inventory.index:
            if inventory["Name"][y] == findCards["Name"][x]:
                sum += int(inventory["Quantity"][y])
        print(str(sum) + " " + findCards["Name"][x])

def combineDuplicates():
    newCardsdf = pd.read_csv("new.csv")
    aggregationFunction = {'Add to Quantity': 'sum', 'Product Name': 'first','Set Name' : 'first','Printing': 'first','Condition': 'first','Language': 'first','TCGplayer Id': 'first','TCG Marketplace Price': 'first','Lot':'first','Product Line': 'first','Title': 'first','Number': 'first','Rarity': 'first','TCG Market Price': 'first','TCG Direct Low': 'first','TCG Low Price With Shipping': 'first','TCG Low Price': 'first','Total Quantity': 'first','Photo URL': 'first'}
    newCardsdf = newCardsdf.groupby(newCardsdf['Product Name']).aggregate(aggregationFunction)
    print(newCardsdf)
    newCardsdf.to_csv('new.csv', index = False)

def removeLot2():
    sales = pd.read_csv('sales.csv')
    sales.drop('Lot', inplace=True, axis = 1)
    sales = sales.rename(columns={'Lot2': 'Lot'})
    sales.to_csv("sales.csv", index=False)

def AddInvetoryAndChangeHeaders():
    lotNumber = input('Enter Lot number: ')
    addLot(lotNumber)
    newHeaders()
    print('Lot numbers added to "new"')

def OrderCardsBasedOnSet():
    orderSets()
    print('"new" sets ordered')

def FindCardsForTCGPlayerSales():
    pd.set_option("display.max_columns", 9)
    Game()

def FindCardsInNewAndInventory():
    findIn("inventory.csv","new.csv")

while True:
    commands = [
        {'text': 'Change Headers of New then Add to "invetory.csv"', 'action': AddInvetoryAndChangeHeaders,},
        {'text': 'Order "new.csv" by set','action': orderSets},
        {'text': 'Find Cards for Tcgplayer sales', 'action': FindCardsForTCGPlayerSales},
        {'text': 'Remove cards worth less than $0.10 market price from "new.csv"','action': removeUnder10Cents},
        {'text': 'Find cards that are in "new.csv" and in "inventory.csv"','action': FindCardsInNewAndInventory},
        {'text': 'Combine duplicate cards in "new.csv"','action': combineDuplicates},
        {'text': 'Remove Lot 2 from "sales.csv" and Fix Headers','action': removeLot2},
        {'text': 'Calculate "profits.csv"','action': getProf}
    ]

    print('\nOptions: ')
    for i, command in enumerate(commands):
        print(f'{i}: {command["text"]}')
    print(f'{len(commands)}: Exit')
    commandInput = input("Enter Option: ")

    if int(commandInput) > len(commands):
        print('Goodbye')
        break

    commands[int(commandInput)]['action']()
    print('Action Completed: ' + commands[int(commandInput)]['text'])