import pygame
import sys
import random

def gridStep(game):
    newGrid = makeGrid(game)
    for i in range(game.gridSize):
        for j in range(game.gridSize):
            count = 0
            if i != 0:
                if game.grid[i - 1][j] == 'O':
                    count += 1
            if i != game.gridSize - 1:
                if game.grid[i + 1][j] == 'O':
                    count += 1
            if j != game.gridSize - 1:
                if game.grid[i][j + 1] == 'O':
                    count += 1
            if j != 0:
                if game.grid[i][j - 1] == 'O':
                    count += 1
            if j != 0 and i != game.gridSize - 1:
                if game.grid[i + 1][j - 1] == 'O':
                    count += 1
            if j != game.gridSize - 1 and i != game.gridSize - 1:
                if game.grid[i + 1][j + 1] == 'O':
                    count += 1
            if j != 0 and i != 0:
                if game.grid[i - 1][j - 1] == 'O':
                    count += 1
            if j != game.gridSize - 1 and i != 0:
                if game.grid[i - 1][j + 1] == 'O':
                    count += 1
            newGrid = myRules(newGrid, count, i, j)
    game.grid = newGrid

def makeGrid(g):
    grid = []
    for i in range(g.gridSize):
        tempArray = []
        for j in range(g.gridSize):
            tempArray.append('_')
        grid.append(tempArray)
    return grid

def randomizeGridSection(grid, size):
    for i in range(0, size, 1):
        for j in range(0, size, 1):
            temp = random.randint(0, 1)
            if temp:
                grid[i][j] = 'O'

def conwayRules(grid, newGrid, count , i , j):
    if count <= 1:
        newGrid[i][j] = '_'
    elif count == 3:
        newGrid[i][j] = 'O'
    elif count == 2:
        if grid[i][j] == 'O':
            newGrid[i][j] = 'O'
    elif count > 3:
        newGrid[i][j] = '_'
    return newGrid

def myRules(newGrid, count , i , j):
    if count <= 2:
        newGrid[i][j] = '_'
    elif count == 8:
        newGrid[i][j] = '_'
    else:
        newGrid[i][j] = 'O'
    return newGrid

def update(game):
    gridStep(game)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                a = 1
def draw(game):
    for i in range(len(game.grid)):
        for j in range(len(game.grid[i])):
            game.screen.blit(game.myFont.render(game.grid[i][j], 1, game.colors.BLACK), (6 * i, 6 * j))
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
class Game:
    def start(self):

        self.gridSize = 80
        self.grid = makeGrid(self)
        randomizeGridSection(self.grid, 40)

    def __init__(self):
        Game.start(self)
        pygame.init()
        self.SCREEN_WIDTH = 1000
        self.SCREEN_HEIGHT = 1000
        self.screen = pygame.display.set_mode((self.SCREEN_WIDTH, self.SCREEN_HEIGHT))
        self.clock = pygame.time.Clock()
        self.colors = Color()
        self.fontSize = 10
        self.myFont = pygame.font.SysFont("freesansbold", self.fontSize)

        while self.SCREEN_WIDTH < 10000:

            update(self)
            self.screen = pygame.display.set_mode((self.SCREEN_WIDTH, self.SCREEN_HEIGHT))
            self.screen.fill(self.colors.GREY)
            draw(self)
            pygame.display.update()
            self.clock.tick(60)
game = Game()