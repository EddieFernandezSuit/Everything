import pandas as pd
import math
import pygame
import sys
import io
from mtgsdk import Card
from mtgsdk import Set
from urllib.request import urlopen
from datetime import date
import re



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
            self.screen = pygame.display.set_mode((self.SCREEN_WIDTH, self.SCREEN_HEIGHT))
            self.screen.fill(self.colors.GREY)
            draw(self)
            pygame.display.update()
            self.clock.tick(60)

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
            # game.images = []
            # game.lotNum = []
            # game.name = []
            # game.set = []
            # game.condition = []
            shipType = 'normal'
            
            for j in range(4):
                inventory = pd.read_csv("inventory.csv")
                newCards = pd.read_csv("new.csv")
                for column in newCards.columns:
                    if column == 'Card Name' or column == 'Product Name':
                        # if column == 'Product Name':
                            # shipType = 'normal'
                        if column == 'Card Name':
                            shipType = 'direct'
                        
                        newCards = newCards.rename(columns={column: 'Name'})
                    elif column == 'Set Name':
                        newCards = newCards.rename(columns={column: 'Set'})
                if len(newCards.index) < 1:
                    print('Complete')
                    break
                
                name = newCards["Name"][0]
                set = newCards["Set"][0]
                condition = newCards["Condition"][0]
                quantity = newCards["Quantity"][0]
                
                if int(quantity) <= 1:
                    newCards = newCards.drop([0])
                else:
                    newCards.loc[0, "Quantity"] = str(int(quantity) - 1)
                index = None
                for x in range(len(inventory["Name"])):
                    if inventory["Name"][x] == name and inventory["Set"][x] == set:
                        if condition[-4:] == "Foil":
                            if inventory["Foil"][x] == "Foil":
                                index = x
                        else:
                            if inventory["Condition"][x] == condition:
                                index = x
                if index == None:
                    price = 0
                    lot = -1
                    lot2 = -1
                else:
                    inventory.loc[index, "Quantity"] = str(int(inventory["Quantity"][index]) - 1)
                    price = float(inventory["Price"][index][1:])
                    lot = inventory["Lot"][index]
                    lot2 = int(math.floor(float(inventory["Lot"][index])))
                    if int(inventory["Quantity"][index]) <= 0:
                        inventory = inventory.drop([index])

                newRow = {'Name': name, 'Set': set, 'Condition': condition, 'Lot': lot,
                            'Price': price, 'Lot2': lot2, 'Date': date.today(), 'shipType': shipType}
                sales = pd.read_csv("sales.csv")
                sales = sales.append(newRow, ignore_index=True)
                sales.to_csv("sales.csv", index=False)
                inventory.to_csv("inventory.csv", index=False)
                newCards.to_csv("new.csv", index=False)
                cardImage(game, name, set)
                game.lotNum.append(lot)
                game.name.append(name)
                game.set.append(set)
                game.condition.append(condition)
                if len(game.lotNum) > 12:
                    game.image.pop(0)
                    game.lotNum.pop(0)
                    game.name.pop(0)
                    game.set.pop(0)
                    game.condition.pop(0)

def cardImage(game, cardName, set):
    # cards = Card.where(name=name).where(set_name=set).all()
    cardName = removeParentheses(cardName).rstrip()
    set = removeParentheses(set).rstrip()
    print(cardName)
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
    game.image.append(image)

def draw(game):
    xDistance = 130
    for i in range(len(game.lotNum)):
        game.screen.blit(game.myFont.render(str(game.lotNum[i]), 1, game.colors.BLACK),(xDistance * i, 20))
    for i in range(len(game.lotNum)):
        game.screen.blit(game.myFont.render(str(game.name[i]), 1, game.colors.BLACK),(xDistance * i, 40))
    for i in range(len(game.set)):
        game.screen.blit(game.myFont.render(str(game.set[i]), 1, game.colors.BLACK), (xDistance * i, 60))
    for i in range(len(game.condition)):
        game.screen.blit(game.myFont.render(str(game.condition[i]), 1, game.colors.BLACK), (xDistance * i, 80))
    for i in range(len(game.image)):
        game.screen.blit(game.image[i], (xDistance * i, 100))

def start(game):
    game.image = []
    game.lotNum = []
    game.name = []
    game.set = []
    game.condition = []

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

while True:
    print("1. add Lot and change headers of 'new'\n2. Order Sets of 'new'\n3. Process TcgStore Sales\n4. Remove under 10 cents in 'new'\n5. Find cards in inventory from csv\n6. Combine Duplicates\n7. Exit")
    command = input("Enter Option: ")
    if command == '1':
        lotNumber = input('Enter Lot number: ')
        addLot(lotNumber)
        newHeaders()
        print('Lot numbers added to "new"')
    elif command == '2':
        orderSets()
        print('"new" sets ordered')
    elif command == '3':
        pd.set_option("display.max_columns", 9)
        dataSearched = pd.DataFrame()
        game = Game()
    elif command == '4':
        removeUnder10Cents()
        print('under 10 cents removed for "new"')
    elif command == '5':
        print("Found cards")
        findIn("inventory.csv","new.csv")
        cont = False
    elif command == '6':
        combineDuplicates()
    elif command == '7':
        print('GoodBye')
        break

