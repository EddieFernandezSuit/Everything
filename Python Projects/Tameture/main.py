import pygame
import engine
import sys
import random

# class Color:
#     GREY = (150, 150, 150)
#     BLACK = (0, 0, 0)
#     RED = (255, 0, 0)
#     GREEN = (0, 255, 0)
#     BLUE = (0, 0, 255)
#     YELLOW = (255, 255, 0)
#     CYAN = (0, 255, 255)
#     PINK = (220, 20, 60)
#     PURPLE = (138, 43, 226)
#     ORANGE = (255, 165, 0)
#     WHITE = (255, 255, 255)

class GameObject:
    def __init__(self, gameState, components):
        self.components = components
        gameState.objects.append(self)

    def getComp(self, comp):
        for component in self.components:
            if comp == type(component):
                return component

class PositionComp:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class ImageComp:
    def __init__(self, image):
        self.image = pygame.image.load(image)

class ControlMovementComp:
    def __init__(self, speed):
        self.speed = speed

class TextComp:
    def __init__(self, str, color):
        self.str = str
        self.color = color

class TimerDeleteComp:
    def __init__(self, timeEnd):
        self.time = 0
        self.timeEnd = timeEnd

class unlearnedMove:
    def __init__(self, damage, name, bleedDamage, recoilDamage):
        self.damage = damage
        self.name = name
        self.bleedDamage = bleedDamage
        self.recoilDamage = recoilDamage

class CreatureComp:
    def __init__(self, gameState, x, y, name, enemyCreature, isPlayer, level):
        distance = 25
        self.x = x
        self.y = y
        self.isPlayer = isPlayer
        self.health = 100
        self.level = level
        self.exp = 0
        self.bleedDamage = 0
        self.enemyCreature = enemyCreature
        self.gameState = gameState
        self.nameText = GameObject(gameState, [TextComp('Name: ' + name, engine.Color.BLACK), PositionComp(x, y)])
        self.healthText = GameObject(gameState,
                                    [TextComp(str(self.health), engine.Color.BLACK), PositionComp(x, y + distance)])
        self.levelText = GameObject(gameState,
                                    [TextComp(str(self.level), engine.Color.BLACK), PositionComp(x, y + distance * 2)])
        self.expText = GameObject(gameState,
                                    [TextComp(str(self.exp), engine.Color.BLACK), PositionComp(x, y + distance * 3)])
        self.moves = []

        for i in range(self.level):
            self.moves.append(Move(self.moveAction, i,self))

    def __getitem__(self, item):
        return getattr(self, item)

    def __setitem__(self, key, value):
        setattr(self, key, value)

    def changeStat(self, creatureComp, stat, add):
        flyingNumDx = 2
        flyingNumDy = random.uniform(-1, 1)
        flyingNumTime = 70

        if add != 0:
            color = engine.Color.RED
            plus = ''
            if add > 0:
                color = engine.Color.GREEN
                plus = '+'
            creatureComp[stat] = creatureComp[stat] + add
            GameObject(self.gameState, [PositionComp(creatureComp[stat + 'Text'].getComp(PositionComp).x + 30,
                                                    creatureComp[stat + 'Text'].getComp(PositionComp).y),
                                        TextComp(plus + str(add), color), MovementComp(flyingNumDx, flyingNumDy),
                                        TimerDeleteComp(flyingNumTime)])

    def moveAction(self, damage, bleedDamage, recoilDamage):
        self.enemyCreature.getComp(CreatureComp).bleedDamage += bleedDamage
        self.changeStat(self, 'health', self.bleedDamage * -1)
        self.changeStat(self.enemyCreature.getComp(CreatureComp), 'health', damage * -1)
        self.changeStat(self, 'health', recoilDamage * -1)

        if self.isPlayer:
            random.choice(self.enemyCreature.getComp(CreatureComp).moves).action()
            if self.enemyCreature.getComp(CreatureComp).health <= 0:
                playerCreature = self.enemyCreature.getComp(CreatureComp).enemyCreature
                self.gameState.objects.remove(self.enemyCreature.getComp(CreatureComp).nameText)
                self.gameState.objects.remove(self.enemyCreature.getComp(CreatureComp).healthText)
                self.gameState.objects.remove(self.enemyCreature.getComp(CreatureComp).expText)
                self.gameState.objects.remove(self.enemyCreature.getComp(CreatureComp).levelText)
                for x in self.enemyCreature.getComp(CreatureComp).moves:
                    self.gameState.objects.remove(x.text)
                self.gameState.objects.remove(self.enemyCreature)

                self.enemyCreature = GameObject(self.gameState,
                                                [PositionComp(1000, 100), ImageComp('teethBlob.png'),
                                                CreatureComp(self.gameState, 1000, 400, "Bob", playerCreature, False,1)])

                self.changeStat(self, 'exp', 100)
                maxExp = 100
                if self.exp >= maxExp:
                    self.changeStat(self, 'exp', -100)

                    self.changeStat(self, 'level', 1)
                    self.changeStat(self, 'health', 100 - self.health)
                    if self.level == 2:
                        self.moves.append(Move(self.moveAction, 1, self))

            if self.health <= 0:
                # GameObject(self.gameState, [TextComp('GameOver',engine.Color.BLACK), PositionComp(600,200)])
                # self.gameState.game.pause = 1
                self.gameState.game.gameStates.pop()

                start(self.gameState.game)

class Move:
    def __init__(self, genericAction, num, creatureComp):
        distance = 25
        unlearnedMoves = [unlearnedMove(10, 'tap', 0, 0), unlearnedMove(0, 'stomp', 5, 0),
                        unlearnedMove(30, 'wack', 0, 10)]
        self.damage = unlearnedMoves[num].damage
        self.bleedDamage = unlearnedMoves[num].bleedDamage
        self.recoilDamage = unlearnedMoves[num].recoilDamage
        self.name = unlearnedMoves[num].name
        self.text = GameObject(creatureComp.gameState,
                            [TextComp(unlearnedMoves[num].name, engine.Color.BLACK),
                                PositionComp(creatureComp.x, creatureComp.y + distance * (5 + num))])
        self.genericAction = genericAction

    def action(self):
        self.genericAction(self.damage, self.bleedDamage, self.recoilDamage)

class TextSelectorComp:
    def __init__(self, selectArray):
        self.selectArray = selectArray
        self.selectNum = 0

class MovementComp:
    def __init__(self, dx, dy):
        self.dx = dx
        self.dy = dy

class Selector:
    def __init__(self, text, action):
        self.text = text
        self.action = action

def getComponent(gameObject, comp):
    for component in gameObject.components:
        if comp == type(component):
            return component
    return None

def doComp(gameObject, compClasses, do):
    comps = []
    for comp in compClasses:
        if gameObject.getComp(comp):
            comps.append(gameObject.getComp(comp))

    if len(comps) == len(compClasses):
        do(comps)

def start(game_):
    def playAction():
        battleState = engine.GameState(game_, 'battleState')
        snek = GameObject(battleState, [PositionComp(100, 100), ImageComp('snek.png'),
                                        CreatureComp(battleState, 100, 400, 'Snek', None, True, 1)])
        bob = GameObject(battleState, [PositionComp(game_.width - 400, 100), ImageComp('teethBlob.png'),
                                    CreatureComp(battleState, game_.width - 400, 400, "Bob", snek, False, 1)])
        snek.getComp(CreatureComp).enemyCreature = bob

        GameObject(battleState, [TextSelectorComp(snek.getComp(CreatureComp).moves)])

    menuState = engine.GameState(game_, 'menuState')
    menuText = GameObject(menuState, [PositionComp(300, 300), TextComp('Play', engine.Color.BLACK)])
    menuSelector = [Selector(menuText, playAction)]

    GameObject(menuState, [TextSelectorComp(menuSelector)])

def update(game_):
    gameState = game_.gameStates[len(game_.gameStates) - 1]

    for object in gameState.objects:
        def drawImage(comps):
            game_.screen.blit(comps[1].image, (comps[0].x, comps[0].y))

        def controlPlayer(comps):
            keys = pygame.key.get_pressed()

            if keys[pygame.K_LEFT]:
                comps[0].x -= comps[1].speed
            if keys[pygame.K_RIGHT]:
                comps[0].x += comps[1].speed
            if keys[pygame.K_UP]:
                comps[0].y -= comps[1].speed
            if keys[pygame.K_DOWN]:
                comps[0].y += comps[1].speed

        def drawText(comps):
            textSurface = game_.myFont.render(comps[0].str, True, comps[0].color)
            game_.screen.blit(textSurface, (comps[1].x, comps[1].y))

        def selectText(comps):
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    sys.exit()
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_DOWN:
                        getComponent(comps[0].selectArray[comps[0].selectNum].text, TextComp).color = engine.Color.BLACK
                        comps[0].selectNum += 1
                        if comps[0].selectNum >= len(comps[0].selectArray):
                            comps[0].selectNum = 0
                    if event.key == pygame.K_UP:
                        getComponent(comps[0].selectArray[comps[0].selectNum].text, TextComp).color = engine.Color.BLACK
                        comps[0].selectNum -= 1
                        if comps[0].selectNum < 0:
                            comps[0].selectNum = len(comps[0].selectArray) - 1
                    if event.key == pygame.K_SPACE:
                        comps[0].selectArray[comps[0].selectNum].action()
            getComponent(comps[0].selectArray[comps[0].selectNum].text, TextComp).color = engine.Color.YELLOW

        def updateCreatureStats(comps):
            comps[0].healthText.getComp(TextComp).str = 'Hp: ' + str(comps[0].health)
            comps[0].expText.getComp(TextComp).str = 'Exp: ' + str(comps[0].exp)
            comps[0].levelText.getComp(TextComp).str = 'Lvl: ' + str(comps[0].level)

        def updateMove(obj):
            positionComp = obj.getComp(PositionComp)
            movementComp = obj.getComp(MovementComp)
            if positionComp and movementComp:
                positionComp.x += movementComp.dx
                positionComp.y += movementComp.dy

        def updateTimerDelete(gameObject):
            timerDeleteComp = gameObject.getComp(TimerDeleteComp)
            if timerDeleteComp:
                timerDeleteComp.time += 1
                if timerDeleteComp.time == timerDeleteComp.timeEnd:
                    gameState.objects.remove(gameObject)

        # def updateTurn(obj):

        doComp(object, [PositionComp, ImageComp], drawImage)
        doComp(object, [PositionComp, ControlMovementComp], controlPlayer)
        doComp(object, [TextComp, PositionComp], drawText)
        doComp(object, [TextSelectorComp], selectText)
        doComp(object, [CreatureComp], updateCreatureStats)

        updateMove(object)
        updateTimerDelete(object)


game = engine.Game(1400, 700, start, update)
