import pygame
import sys
import random


def overlap(tl1, br1, tl2, br2):
    if tl1[0] == br1[0] or tl1[1] == br2[1] or tl2[0] == br2[0] or tl2[1] == br2[1]:
        return False

    if tl1[0] >= br2[0] or tl2[0] >= br1[0]:
        return False

    if tl1[1] >= br2[1] or tl2[1] >= br1[1]:
        return False
    return True


def rectRect(r1x, r1y, r1w, r1h, r2x, r2y, r2w, r2h):
    # are the sides of one rectangle touching the other?

    return r1x + r1w >= r2x and r1x <= r2x + r2w and r1y + r1h >= r2y and r1y <= r2y + r2h


def overlapS(p, a):
    return overlap((p.x, p.y), (p.x + p.size, p.y + p.size), (a.x, a.y), (a.x + a.size, a.y + a.size))


class flyingNum:
    def __init__(self, x, y, damage):
        self.x = x
        self.y = y
        self.ySpeed = -damage / 50 - 1
        self.xSpeed = random.uniform(-1, 1)
        self.size = int(damage / 2) + 20
        self.damage = -damage
        self.timer = 20
        self.color = color.BLACK


class minion:
    def __init__(self):
        self.x = random.randint(0, SCREEN_WIDTH[0])
        self.y = random.randint(0, SCREEN_HEIGHT[0])
        self.size = 30
        self.hp = 100
        self.maxHp = 100


class player:
    def __init__(self, x, y, left, right, up, down, kAbility1, kAbility2, color, ability):
        self.x = x
        self.y = y
        self.size = self.sizeX = self.sizeY = 50
        self.speed = 2
        self.right = right
        self.left = left
        self.up = up
        self.down = down
        self.direction = "right"
        self.kAbilities = [kAbility1, kAbility2]
        self.cooldownTimers = [0, 0]
        self.color = color
        self.health = 200
        self.ability = ability
        self.fireMode = 0
        self.mana = 100
        self.manaRegen = .4
        self.exp = 0
        self.level = 1
        self.abilityStage = 0
        self.dx = 0
        self.dy = 0
        self.dxdyTimer = 0
        self.isBodyDamage = 0
        self.bodyDamageTimer = 0
        self.bodyDamage = 20
        self.isBallOut = 0
        self.ball = 0


class ability:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.size = 50
        self.lifeTimer = 5
        self.ySpeed = 0
        self.xSpeed = 0
        self.friendlyPlayer = 0
        self.sizeIncrease = 0
        self.damage = 10
        self.damageIncrease = 0
        self.exp = 0
        self.bounce = 0
        self.lifeSteal = 0
        self.unstoppableMinions = 0
        self.hitPlayers = []
        self.hitMinions = []
        self.isDamageSelf = 0
        self.destroyOnHit = 1
        self.bounceOffWalls = 0


def checkMana(p, manaUse):
    if p.mana >= manaUse:
        p.mana -= manaUse
        return True


def do0(p):
    if checkMana(p, 15):
        abilityDistance = p.size + 10
        if p.direction == "right":
            ab = ability(p.x + abilityDistance, p.y)
        elif p.direction == "left":
            ab = ability(p.x - abilityDistance, p.y)
        elif p.direction == "up":
            ab = ability(p.x, p.y - abilityDistance)
        else:
            ab = ability(p.x, p.y + abilityDistance)
        ab.friendlyPlayer = p
        ab.lifeTimer = 15
        ab.damage = p.level * 4 + 8
        abilities.append(ab)
        return 1
    return 0


def do1(players):
    if checkMana(players, 15):
        abilityDistance = players.size
        sp = players.level * 4 + 5
        if players.direction == "right":
            ab = ability(players.x + abilityDistance, players.y)
            ab.xSpeed = sp
        if players.direction == "left":
            ab = ability(players.x - abilityDistance, players.y)
            ab.xSpeed = -sp
        if players.direction == "up":
            ab = ability(players.x, players.y - abilityDistance)
            ab.ySpeed = -sp
        if players.direction == "down":
            ab = ability(players.x, players.y + abilityDistance)
            ab.ySpeed = sp
        ab.lifeTimer = 120
        ab.damage = players.level * 10 + 10
        ab.friendlyPlayer = players
        abilities.append(ab)
        return 1
    return 0


def do2(p):
    if p.fireMode == 0:
        p.fireMode = 1
    else:
        p.fireMode = 0
    return 1


def do3(players):
    if checkMana(players, 15):
        abilityDistance = players.size
        ab = ability(0, 0)
        ab.size = 1
        ab.sizeIncrease = .05 * players.level + .1
        sp = 2
        if players.direction == "right":
            ab.x = players.x + abilityDistance
            ab.y = players.y + abilityDistance / 2
            ab.xSpeed = sp
            ab.ySpeed = -ab.sizeIncrease / 2
        if players.direction == "left":
            ab.x = players.x - ab.size
            ab.y = players.y + abilityDistance / 2
            ab.xSpeed = -sp
            ab.ySpeed = -ab.sizeIncrease / 2
        if players.direction == "up":
            ab.x = players.x + abilityDistance / 2
            ab.y = players.y - ab.size
            ab.ySpeed = -sp
            ab.xSpeed = -ab.sizeIncrease / 2
        if players.direction == "down":
            ab.x = players.x + abilityDistance / 2
            ab.y = players.y + abilityDistance
            ab.ySpeed = sp
            ab.xSpeed = -ab.sizeIncrease / 2
        ab.lifeTimer = 8000
        ab.friendlyPlayer = players
        ab.damage = 2
        ab.damageIncrease = .05 * players.level + .01
        ab.unstoppableMinions = 1
        abilities.append(ab)
        return 1
    return 0


def do4(p):
    if checkMana(p, 10):
        ab = ability(p.x, p.y)
        abilityDistance = 60
        if p.direction == "left":
            ab = ability(p.x + abilityDistance, p.y)
        elif p.direction == "right":
            ab = ability(p.x - abilityDistance, p.y)
        elif p.direction == "down":
            ab = ability(p.x, p.y - abilityDistance)
        else:
            ab = ability(p.x, p.y + abilityDistance)
        ab.size = p.size
        ab.lifeTimer = 5000
        ab.damage = 8 * p.level + 10
        ab.friendlyPlayer = p
        ab.isDamageSelf = 1
        abilities.append(ab)
        return 1
    return 0


def do5(p):
    if len(players) <= 25:
        if checkMana(p, 100):
            space = 150
            playerNew = player(random.randint(int(p.x - space), int(p.x + space)),
                               random.randint(int(p.y - space), int(p.y + space)), p.left, p.right, p.up, p.down,
                               p.kAbilities[0], p.kAbilities[1], p.color, p.ability)
            if p.level > 1:
                playerNew.level = p.level
            else:
                p.level = 1
            playerNew.cooldownTimers[1] = 1500
            playerNew.health = p.health - 10
            playerNew.exp = p.exp
            playerNew.mana = p.mana
            playerNew.speed = p.speed
            players.append(playerNew)
            return 1
    return 0


def do6(p):
    if checkMana(p, 10):
        a = ability(p.x, p.y)
        a.size = 20
        a.x += a.size / 2
        a.y += a.size / 2
        a.lifeTimer = 4000
        a.friendlyPlayer = p
        a.damage = 5 * p.level + 5
        a.destroyOnHit = 0
        a.bounce = 1
        sp = 8
        if p.direction == "right":
            a.xSpeed = sp
        elif p.direction == "left":
            a.xSpeed = -sp
        elif p.direction == "up":
            a.ySpeed = -sp
        elif p.direction == "down":
            a.ySpeed = sp
        abilities.append(a)
        return 1
    return 0


def do7(p):
    if checkMana(p, 30):
        heal = 20
        p.health += heal
        f = flyingNum(p.x, p.y, heal)
        f.color = color.GREEN
        flyingNums.append(f)
        return 1
    return 0


def do8(p):
    if checkMana(p, 5):
        a = ability(p.x, p.y)
        sp = 10
        a.lifeTimer = 8
        a.damage = p.level * 3
        r = 20
        if p.direction == "right":
            a.xSpeed = sp
            a.y += random.randint(-r, r)
        elif p.direction == "left":
            a.xSpeed = -sp
            a.y += random.randint(-r, r)
        elif p.direction == "up":
            a.ySpeed = -sp
            a.x += random.randint(-r, r)
        elif p.direction == "down":
            a.ySpeed = sp
            a.x += random.randint(-r, r)
        a.lifeSteal = 1
        a.friendlyPlayer = p
        abilities.append(a)
        return 1
    return 0


def do9(p):
    if checkMana(p, 70):
        d = 120 + p.level * 40
        if p.direction == "right":
            p.x += d
        elif p.direction == "left":
            p.x -= d
        elif p.direction == "up":
            p.y -= d
        elif p.direction == "down":
            p.y += d
        return 1
    return 0


def do10(p):
    if checkMana(p, 20):
        a = ability(p.x, p.y)
        a.lifeTimer = 30
        a.damage = 20 + p.level * 5
        a.lifeSteal = 5
        abilityDistance = 140
        if p.abilityStage == 0:
            a.size = 50
            p.abilityStage += 1
        elif p.abilityStage == 1:
            abilityDistance /= 2
            a.size = 100
            p.abilityStage += 1
        elif p.abilityStage == 2:
            abilityDistance = 0
            a.size = 100
            p.abilityStage = 0
            p.cooldownTimers[0] = 500
        if p.direction == "right":
            a.x += abilityDistance + p.size
            a.y -= (a.size - p.size) / 2

        elif p.direction == "left":
            a.x -= abilityDistance + a.size
            a.y -= (a.size - p.size) / 2
        elif p.direction == "up":
            a.y -= abilityDistance + a.size
            a.x -= (a.size - p.size) / 2
        else:
            a.y += abilityDistance + p.size
            a.x -= (a.size - p.size) / 2
        a.friendlyPlayer = p
        a.destroyOnHit = 0
        abilities.append(a)
        return 1
    return 0


def do11(p):
    if checkMana(p, 30):
        s = 14
        if p.direction == "right":
            p.dx = s
        elif p.direction == "left":
            p.dx = -s
        elif p.direction == "up":
            p.dy = -s
        else:
            p.dy = s
        p.dxdyTimer = p.bodyDamageTimer = 8
        p.isBodyDamage = 1
        p.bodyDamage = 15 * p.level + 5
        return 1
    return 0


def do12(p):
    if checkMana(p, 40):
        sizePlus = p.level * 2
        p.size += sizePlus
        p.x -= sizePlus / 2
        p.y -= sizePlus / 2
        p.isBodyDamage = 1
        p.bodyDamage = 25 + p.level * 9
        p.bodyDamageTimer = 300
        return 1
    return 0


def do13(p):
    if checkMana(p, 40):
        if p.isBallOut == 0:
            a = ability(p.x, p.y)
            sp = 4
            a.xSpeed = random.randint(-sp, sp)
            a.ySpeed = random.randint(-sp, sp)
            a.destroyOnHit = 0
            a.bounceOffWalls = 1
            a.size = 50
            a.friendlyPlayer = p
            p.ball = a
            p.isBallOut = 1
            abilities.append(a)
            a.lifeTimer = 100000
        else:
            p.ball.xSpeed *= 1.03 + .02 * p.level
            p.ball.ySpeed *= 1.03 + .02 * p.level
            p.ball.damage = p.level * 10
            p.ball.hitPlayers = []
            p.ball.hitMinions = []
        return 1
    return 0


def checkAbility(keys, player, knum, num):
    if keys[player.kAbilities[knum]] and player.cooldownTimers[knum] <= 0:
        a = 0
        if num == 0:
            a = do0(player)
        elif num == 1:
            a = do1(player)
        elif num == 2:
            a = do2(player)
        elif num == 3:
            a = do3(player)
        elif num == 4:
            a = do4(player)
        elif num == 5:
            a = do5(player)
        elif num == 6:
            a = do6(player)
        elif num == 7:
            a = do7(player)
        elif num == 8:
            a = do8(player)
        elif num == 9:
            a = do9(player)
        elif num == 10:
            a = do10(player)
        elif num == 11:
            a = do11(player)
        elif num == 12:
            a = do12(player)
        elif num == 13:
            a = do13(player)
        if a:
            player.cooldownTimers[knum] = abilitiesCooldown[num]


def update():
    keys = pygame.key.get_pressed()

    for x in reversed(range(len(players))):

        players[x].speed = 4 - players[x].health / 200

        if players[x].bodyDamageTimer > 0:
            players[x].bodyDamageTimer -= 1
        else:
            players[x].isBodyDamage = 0
        if players[x].dxdyTimer > 0:
            players[x].dxdyTimer -= 1
        else:
            players[x].dx = 0
            players[x].dy = 0
        players[x].x += players[x].dx
        players[x].y += players[x].dy
        if players[x].exp >= 100:
            players[x].exp -= 100
            players[x].level += 1
            if players[x].color == color.YELLOW:
                players[x].speed += .5
        if players[x].mana < 100:
            players[x].mana += players[x].manaRegen
        if players[x].health < 200:
            players[x].health += .04

        if players[x].x <= -players[x].size or players[x].x >= SCREEN_WIDTH[0] or players[x].y <= -players[x].size or \
                players[x].y >= SCREEN_HEIGHT[0]:
            players.pop(x)
            break

        for y in reversed(range(len(minions))):
            if overlapS(players[x], minions[y]):
                if players[x].isBodyDamage == 1:
                    minions[y].hp -= players[x].bodyDamage
                    players[x].isBodyDamage = 0
                    flyingNums.append(flyingNum(minions[y].x, minions[y].y - 20, players[x].bodyDamage))
                    if minions[y].hp <= 0:
                        players[x].exp += 70 / players[x].level
                        minions.pop(y)

        players[x].x += (keys[players[x].right] - keys[players[x].left]) * players[x].speed
        players[x].y += (keys[players[x].down] - keys[players[x].up]) * players[x].speed

        for y in range(len(players[x].cooldownTimers)):
            players[x].cooldownTimers[y] -= 1

        if keys[players[x].left]:
            players[x].direction = "left"
        if keys[players[x].right]:
            players[x].direction = "right"
        if keys[players[x].up]:
            players[x].direction = "up"
        if keys[players[x].down]:
            players[x].direction = "down"

        for y in range(2):
            checkAbility(keys, players[x], y, players[x].ability[y])

        if players[x].fireMode == 1:
            ab = ability(players[x].x + players[x].size / 2, players[x].y + players[x].size / 2)
            ab.size = 5
            ab.damage = 1 * players[x].level
            ab.x -= ab.size / 2
            ab.y -= ab.size / 2
            sp = .8 * players[x].level + 4
            ab.xSpeed += random.uniform(sp * -1, sp)
            ab.ySpeed += random.uniform(sp * -1, sp)
            ab.lifeTimer = 2 * players[x].level + 9
            ab.friendlyPlayer = players[x]
            if players[x].mana <= 0:
                players[x].fireMode = 0
            else:
                players[x].mana -= .2
            abilities.append(ab)
        skip = 0
        for y in reversed(range(len(abilities))):
            h = 0
            for z in reversed(range(len(abilities[y].hitPlayers))):
                if abilities[y].hitPlayers[z] == players[x]:
                    h = 1
            if overlapS(players[x], abilities[y]) and (
                    abilities[y].friendlyPlayer != players[x] or abilities[y].isDamageSelf == 1) and h == 0:
                if abilities[y].friendlyPlayer.color == color.BLACK and players[x].color == color.BLACK:
                    break

                players[x].health -= abilities[y].damage
                abilities[y].friendlyPlayer.exp += abilities[y].exp
                f = flyingNum(players[x].x, players[x].y - 20, abilities[y].damage)
                flyingNums.append(f)
                if abilities[y].lifeSteal > 0:
                    abilities[y].friendlyPlayer.health += 1
                    f = flyingNum(players[x].x, players[x].y - 20, abilities[y].lifeSteal)
                    f.color = color.GREEN
                    flyingNums.append(f)
                if players[x].health <= 0:
                    abilities[y].friendlyPlayer.exp += 100 * players[x].level / abilities[y].friendlyPlayer.level
                    players.pop(x)
                    break
                if abilities[y].bounce == 0:
                    if abilities[y].destroyOnHit == 1:
                        abilities.pop(y)
                        skip = 1
                    else:
                        abilities[y].hitPlayers.append(players[x])
                else:
                    abilities[y].xSpeed *= -1
                    abilities[y].ySpeed *= -1
                    abilities[y].x += abilities[y].xSpeed * 3
                    abilities[y].y += abilities[y].ySpeed * 3
        if skip == 1:
            break
        for y in reversed(range(len(players))):
            if overlapS(players[x], players[y]) and players[x] != players[y]:
                if players[y].isBodyDamage == 1:
                    players[x].health -= players[y].bodyDamage
                    players[y].isBodyDamage = 0
                    flyingNums.append(flyingNum(players[x].x, players[x].y - 20, players[y].bodyDamage))
                    if players[x].health <= 0:
                        players[y].exp += 100 * players[x].level / players[y].level
                        players.pop(x)
                        break
                if players[x].isBodyDamage == 1:
                    players[y].health -= players[x].bodyDamage
                    players[x].isBodyDamage = 0
                    flyingNums.append(flyingNum(players[y].x, players[y].y - 20, players[x].bodyDamage))
                    if players[y].health <= 0:
                        players[x].exp += 100 * players[y].level / players[x].level
                        players.pop(x)
                        break

                if players[x].x < players[y].x - players[x].size + 7:
                    players[x].x = players[y].x - players[x].size - 1
                elif players[x].x > players[y].x + players[y].size - 7:
                    players[x].x = players[y].x + players[y].size + 1
                elif players[x].y < players[y].y - players[x].size + 7:
                    players[x].y = players[y].y - players[y].size - 1
                elif players[x].y > players[y].y + players[x].size - 7:
                    players[x].y = players[y].y + players[y].size + 1

    for x in reversed(range(len(abilities))):
        abilities[x].lifeTimer -= 1
        abilities[x].y += abilities[x].ySpeed
        abilities[x].x += abilities[x].xSpeed
        abilities[x].size += abilities[x].sizeIncrease
        abilities[x].damage += abilities[x].damageIncrease
        if abilities[x].bounceOffWalls == 1:
            if abilities[x].x < 0 or abilities[x].x + abilities[x].size > SCREEN_WIDTH[0]:
                abilities[x].xSpeed *= -1
                abilities[x].x += 2 * (SCREEN_WIDTH[0] / 2 - abilities[x].x) / SCREEN_WIDTH[0]
            if abilities[x].y < 0 or abilities[x].y + abilities[x].size > SCREEN_HEIGHT[0]:
                abilities[x].ySpeed *= -1
                abilities[x].y += 2 * (SCREEN_HEIGHT[0] / 2 - abilities[x].y) / SCREEN_HEIGHT[0]
        if abilities[x].lifeTimer <= 0:
            abilities.pop(x)

    for x in reversed(range(len(minions))):
        for y in reversed(range(len(abilities))):
            h = 1
            for z in range(len(abilities[y].hitMinions)):
                if abilities[y].hitMinions[z] == minions[x]:
                    h = 0
            if overlapS(minions[x], abilities[y]) and h == 1:
                flyingNums.append(flyingNum(minions[x].x, minions[x].y, abilities[y].damage))
                minions[x].hp -= abilities[y].damage
                if abilities[y].lifeSteal > 0:
                    abilities[y].friendlyPlayer.health += 1
                    f = flyingNum(abilities[y].friendlyPlayer.x, abilities[y].friendlyPlayer.y - 20,
                                  abilities[y].lifeSteal)
                    f.color = color.GREEN
                    flyingNums.append(f)
                if abilities[y].unstoppableMinions == 0:
                    if abilities[y].bounce == 0:
                        abilities[y].hitMinions.append(minions[x])
                    else:
                        abilities[y].xSpeed *= -1
                        abilities[y].ySpeed *= -1
                        abilities[y].x += abilities[y].xSpeed * 3
                        abilities[y].y += abilities[y].ySpeed * 3
                if minions[x].hp <= 0:
                    minions.pop(x)
                    abilities[y].friendlyPlayer.exp += 70 / (abilities[y].friendlyPlayer.level)
                if abilities[y].destroyOnHit == 1:
                    abilities.pop(y)
                break

    for x in reversed(range(len(flyingNums))):
        flyingNums[x].y += flyingNums[x].ySpeed
        flyingNums[x].x += flyingNums[x].xSpeed
        flyingNums[x].timer -= 1
        if flyingNums[x].timer <= 0:
            flyingNums.pop(x)

    screenShrinkTimer[0] -= 1
    if screenShrinkTimer[0] <= 0:
        SCREEN_WIDTH[0] -= 1
        SCREEN_HEIGHT[0] -= 1
        screenShrinkTimer[0] = 30
    minionTimer[0] -= 1
    if minionTimer[0] <= 0:
        c = 0
        for x in range(len(players)):
            c += players[x].level
        minionTimer[0] = 60 - c
        minions.append(minion())


def draws():
    for x in range(len(abilities)):
        pygame.draw.rect(screen, colors.RED, (abilities[x].x, abilities[x].y, abilities[x].size, abilities[x].size))
    for x in range(len(players)):
        screen.blit(myFont.render(str(players[x].level), 1, colors.BLACK), (players[x].x - 22, players[x].y - 18))
        pygame.draw.rect(screen, colors.GREEN, (
            players[x].x, players[x].y - 15, players[x].size * (players[x].health / 200), healthBarHeight))
        pygame.draw.rect(screen, colors.BLUE,
                         (players[x].x, players[x].y - 10, players[x].size * (players[x].mana / 100), healthBarHeight))
        pygame.draw.rect(screen, colors.CYAN,
                         (players[x].x, players[x].y - 5, players[x].size * (players[x].exp / 100), healthBarHeight))
        pygame.draw.rect(screen, players[x].color, (players[x].x, players[x].y, players[x].size, players[x].size))
        abilityIconSize = 10
        if players[x].cooldownTimers[0] <= 0:
            pygame.draw.rect(screen, players[x].color,
                             (players[x].x, players[x].y - 30, abilityIconSize, abilityIconSize))
        if players[x].cooldownTimers[1] <= 0:
            pygame.draw.rect(screen, players[x].color,
                             (players[x].x + players[x].size - abilityIconSize, players[x].y - 30, abilityIconSize,
                              abilityIconSize))
    for x in range(len(minions)):
        pygame.draw.rect(screen, colors.CYAN, (minions[x].x, minions[x].y, minions[x].size, minions[x].size))
        pygame.draw.rect(screen, colors.GREEN, (
            minions[x].x, minions[x].y - 5, minions[x].size * (minions[x].hp / minions[x].maxHp), healthBarHeight))
    for x in reversed(range(len(flyingNums))):
        newFont = pygame.font.SysFont("freesansbold", flyingNums[x].size)
        screen.blit(newFont.render(str(int(flyingNums[x].damage)), 1, flyingNums[x].color),
                    (flyingNums[x].x, flyingNums[x].y))



class color:
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
pygame.init()
SCREEN_WIDTH = [1800]
SCREEN_HEIGHT = [1000]
screen = pygame.display.set_mode((SCREEN_WIDTH[0], SCREEN_HEIGHT[0]))
gameOver = False
clock = pygame.time.Clock()
colors = color()

players = []
minions = []
leftKeys = [pygame.K_LEFT, pygame.K_a, pygame.K_g, pygame.K_l]
rightKeys = [pygame.K_RIGHT, pygame.K_d, pygame.K_j, pygame.K_QUOTE]
upKeys = [pygame.K_UP, pygame.K_w, pygame.K_y, pygame.K_p]
downKeys = [pygame.K_DOWN, pygame.K_s, pygame.K_h, pygame.K_SEMICOLON]
abilityKeys = [pygame.K_DELETE, pygame.K_q, pygame.K_t, pygame.K_o]
ability2Keys = [pygame.K_PAGEDOWN, pygame.K_e, pygame.K_u, pygame.K_LEFTBRACKET]
startx = [100, SCREEN_WIDTH[0] - 200, 100, SCREEN_WIDTH[0] - 200]
starty = [100, 100, SCREEN_HEIGHT[0] - 200, SCREEN_HEIGHT[0] - 200]
playerColors = [colors.BLACK, colors.GREEN, colors.BLUE, colors.YELLOW, colors.PINK, colors.PURPLE, colors.ORANGE]
playerAbility = [[0, 5], [9, 2], [8, 3], [6, 4], [10, 11], [12, 7], [0, 13]]
playerSpeed = [2, 2, 2, 3, 2, 2, 2]

abilities = []
abilitiesCooldown = [30, 80, 40, 80, 25, 1500, 50, 180, 10, 30, 70, 50, 80, 90]
screenShrinkTimer = [60]
healthBarHeight = 3
fontSize = 30
myFont = pygame.font.SysFont("freesansbold", fontSize)
minionTimer = [0]
flyingNums = []


class charSelect:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.size = 50


def draw2():
    for x in range(len(charSelects)):
        pygame.draw.rect(screen, playerColors[x],
                         (charSelects[x].x, charSelects[x].y, charSelects[x].size, charSelects[x].size))
    for y in range(4):
        pygame.draw.line(screen, colors.BLACK,
                         (charSelects[isSelected[y]].x, charSelects[isSelected[y]].y + 60 + y * 10),
                         (charSelects[isSelected[y]].x + 50, charSelects[isSelected[y]].y + 60 + y * 10), 5)


def scroll(key1, key2, n, even):
    if even.key == key1:
        if isSelected[n] != 0:
            isSelected[n] -= 1
        else:
            isSelected[n] = len(isSelected) - 1
    if even.key == key2:
        if isSelected[n] != len(isSelected) - 1:
            isSelected[n] += 1
        else:
            isSelected[n] = 0


def update2():
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN:
            scroll(pygame.K_LEFT, pygame.K_RIGHT, 0, event)
            scroll(pygame.K_a, pygame.K_d, 1, event)
            scroll(pygame.K_g, pygame.K_j, 2, event)
            scroll(pygame.K_l, pygame.K_QUOTE, 3, event)
            if event.key == pygame.K_SPACE:
                charSelectScreen[0] = 0


isSelected = []
for x in range(len(playerColors)):
    isSelected.append(1)
charSelects = []
charSelectScreen = [1]
for x in range(len(playerColors)):
    charSelects.append(charSelect((100 * x) + SCREEN_WIDTH[0] / 2 - 200, SCREEN_HEIGHT[0] / 2))

while charSelectScreen[0]:
    update2()
    screen = pygame.display.set_mode((SCREEN_WIDTH[0], SCREEN_HEIGHT[0]))
    screen.fill(colors.GREY)
    draw2()
    pygame.display.update()
    clock.tick(60)

for x in range(4):
    players.append(
        player(startx[x], starty[x], leftKeys[x], rightKeys[x], upKeys[x], downKeys[x], abilityKeys[x], ability2Keys[x],
               playerColors[isSelected[x]], playerAbility[isSelected[x]]))
    players[x].speed = playerSpeed[isSelected[x]]

while not gameOver:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
            print("done")
    update()
    screen = pygame.display.set_mode((SCREEN_WIDTH[0], SCREEN_HEIGHT[0]))
    screen.fill(colors.GREY)
    draws()
    pygame.display.update()
    clock.tick(240)
