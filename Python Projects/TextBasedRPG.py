import pygame
import random
import sys
import math

class enemy:
    def __init__(self, name, lvl):
        self.name = name
        self.HP = 100
        self.damage = lvl*5
        self.lvl = lvl

class upgrade:
    def __init__(self,x,y):
        self.x = x
        self.y = y
    def draw(self):
        screen.blit(myFont.render("upgrade", 1, YELLOW), (self.x, self.y))

class flyingNumber:
    def __init__(self,x,y, number, color):
        self.x = x
        self.y = y
        self.number = number
        self.color = color

pygame.init()

SCREEN_WIDTH = 800
SCREEN_HEIGHT = 600
screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
gameOver = False
clock = pygame.time.Clock()
GREY = (150, 150, 150)
BLACK = (0, 0, 0)
RED = (255,0,0)
GREEN = (0,255,0)
YELLOW = (255, 255, 0)

fontSize = 25
myFont = pygame.font.SysFont("monospace", fontSize)

playerHP = 100
playerEXP = 0
playerLVL = 1
playerStats = ["Dude","Lvl:   " + str(playerLVL),"EXP:     " + str(playerEXP),"HP:      " + str(playerHP)]
playerDamage = 25
playerHeal = 15
playerPercentDamage = .4
petHP = 0

actionColor = [YELLOW, BLACK, BLACK, BLACK]
actionSelected = 0
action = ["deal " + str(playerDamage)]
actionNotLearned = ["heal " + str(playerHeal), "deal "+ str(math.floor(100*playerPercentDamage)) + "%", "pet"]
damageMultiplier = 1
pets = 0

topTextY = 350

enemy1 = enemy("Baddy 1", 1)
upgrades = []
upgradeTrue = 0
flyingNumbers = []

import requests

url = "https://api.tcgplayer.com/stores/storeKey/orders"

headers = {"Accept": "application/json"}

response = requests.request("GET", url, headers=headers)

print(response.text)

def draw():
    screen.fill(GREY)

    for index, x in enumerate(action):
        screen.blit(myFont.render(x, 1, actionColor[index]), (5, SCREEN_HEIGHT + index * fontSize - 200))

    for index, x in enumerate(playerStats):
        screen.blit(myFont.render(x, 1, BLACK), (5, SCREEN_HEIGHT - topTextY + index * fontSize))

    enemyStats = [enemy1.name, "Lvl: " + str(enemy1.lvl), "HP: " + str(enemy1.HP)]

    for x in range(len(enemyStats)):
        screen.blit(myFont.render(enemyStats[x], 1, BLACK), (SCREEN_WIDTH - 160, SCREEN_HEIGHT - topTextY + x * fontSize))

    for x in flyingNumbers:
        screen.blit(myFont.render(str(x.number), 1, x.color), (x.x, x.y))


    for x in upgrades:
        x.draw()
    pygame.display.update()

def checkEnemyDead(enemyHP, playerEXP, playerLVL, enemy1, upgrades, upgradeTrue, playerHP):

    if enemyHP <= 0:
        enemy1 = enemy("Baddy " + str(random.randint(1, 10)), random.randint(1,3))
        playerEXP += math.floor(100/playerLVL)
        if playerEXP >= 100:
            playerLVL += 1
            playerEXP = 0
            playerHP = 100
            upgrades = [upgrade(250, 250)]
            upgradeTrue = 1
    return enemy1, playerEXP, playerLVL, upgrades, upgradeTrue, playerHP


while not gameOver:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN:
            if upgradeTrue == 0:
                if event.key == pygame.K_DOWN:
                    actionColor[actionSelected] = BLACK
                    if actionSelected == len(action) - 1:
                        actionSelected = 0
                    else:
                        actionSelected += 1
                    actionColor[actionSelected] = YELLOW

                if event.key == pygame.K_UP:
                    actionColor[actionSelected] = BLACK
                    if actionSelected == 0:
                        actionSelected = len(action) - 1
                    else:
                        actionSelected -= 1
                    actionColor[actionSelected] = YELLOW

                if event.key == pygame.K_SPACE:
                    enemy1.HP -= pets
                    if actionSelected == 0:
                        enemy1.HP -= playerDamage
                        flyingNumbers.append(flyingNumber(SCREEN_WIDTH - 160, SCREEN_HEIGHT - topTextY,-playerDamage, RED))
                    elif actionSelected == 1:
                        playerHP += playerHeal
                        flyingNumbers.append(flyingNumber(150, SCREEN_HEIGHT - topTextY,"+"+str(playerHeal), GREEN))
                        if playerHP >= 100:
                            playerHP = 100
                    elif actionSelected == 2:
                        flyingNumbers.append(flyingNumber(SCREEN_WIDTH - 160, SCREEN_HEIGHT - topTextY,-math.floor(enemy1.HP * playerPercentDamage), RED))
                        enemy1.HP -= math.floor(enemy1.HP * playerPercentDamage)

                    elif actionSelected == 3 and pets == 0:
                        pets = 1
                        petHP = 20
                        playerStats.append("pet HP: " + str(petHP))


                    if pets == 0:
                        playerHP -= enemy1.damage
                        flyingNumbers.append(flyingNumber(100, SCREEN_HEIGHT - topTextY,-enemy1.damage, RED))
                        if playerHP <= 0:
                            gameOver = 1
                    else:
                        petHP -= enemy1.damage
                        playerStats[4] = "Pet HP: "+ str(petHP)
                        if petHP <= 0:
                            pets = 0
                            playerStats.pop(4)

                    enemy1, playerEXP, playerLVL, upgrades, upgradeTrue, playerHP = checkEnemyDead(enemy1.HP, playerEXP,
                                                                                                   playerLVL, enemy1,
                                                                                                   upgrades,
                                                                                                   upgradeTrue,
                                                                                                   playerHP)


            else:
                print(upgradeTrue)
                if event.key == pygame.K_SPACE:
                    action.append(actionNotLearned[0])
                    actionNotLearned.pop(0)
                    upgrades = []
                    upgradeTrue = 0


    playerStats[0] = "Dude"
    playerStats[1] = "Level: " + str(playerLVL)
    playerStats[2] = "EXP:   " + str(playerEXP)
    playerStats[3] = "HP:    " + str(playerHP)

    for x in range(len(flyingNumbers)):
        flyingNumbers[x].y-=2
        if flyingNumbers[x].y <0:
            flyingNumbers.pop(x)
            break
    draw()
    clock.tick(60)
