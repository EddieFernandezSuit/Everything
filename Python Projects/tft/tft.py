
import pandas as pd

class Champ:
    def __init__(self, name, attackDamageLevels, abilityDamageLevels, attackSpeed, mana):
        self.attackDamageLevels = attackDamageLevels
        self.abilityDamageLevels = abilityDamageLevels
        self.attackSpeed = attackSpeed
        self.mana = mana
        self.name = name

items = [['name',           'attack damage', 'attack speed','ability power', 'crit chance', 'crit damage', 'damage multiplier', 'attack mana gain'],
        ['no item', 0,0,0,0,0,0,0],
        ['death blade',     95,0,0,0,0,0,0],
        ['gunblade',        10,0,10,0,0,0,0],
        ['giant slayer',    10,.1,0,0,0,.2,0],
        ['infinity edge',   10,0,0,.75,.15,0,0],
        ['spear of shojin', 10,0,0,0,0,0,8],
        ["rabadon's deathcap", 0,0,95,0,0,0,0],
        ['jeweled gauntlet',0,0,20,.15,.4,0,0],
        ["titan's resolve", 50,.1,50,0,0,0,0],
        ["runaan's hurricane", 10, .1,0,0,0,.75,0],
        ['rapid firecannon', 0,.8,0,0,0,0,0],
        ['statikk shiv', 93,.25,0,0,0,0,0],
        ['hand of justice', 35,0,35,.15,0,0,0],
        ['blue buff', 0,0,0,0,0,0,0]]

data = pd.read_csv('tftdata.csv')

def dps(champName, level, itemNames):
    champIndex = 0
    itemIndexes = [0,0,0]
    for x in range(len(data['name'])):
        if data['name'][x] == champName:
            champIndex = x

    for w in range(len(itemNames)):
        for x in range(len(items)):
            if items[x][0] == itemNames[w]:
                itemIndexes[w] = x
    
    attackDamage = data['attack damage ' + str(level)][champIndex] + items[itemIndexes[0]][1] + items[itemIndexes[1]][1] + items[itemIndexes[2]][1]
    attackSpeed = data['attack speed'][champIndex] + items[itemIndexes[0]][2]+ items[itemIndexes[1]][2]+ items[itemIndexes[2]][2]
    abilityDamage = data['ability damage ' + str(level)][champIndex]
    mana = data['mana'][champIndex]
    manaAttack = 10 + items[itemIndexes[0]][7]+ items[itemIndexes[1]][7]+ items[itemIndexes[2]][7]
    abilityPower = data['ability power'][champIndex] +  items[itemIndexes[0]][3]+  items[itemIndexes[1]][3]+  items[itemIndexes[2]][3]
    critChance = data['crit chance'][champIndex] +  items[itemIndexes[0]][4]+  items[itemIndexes[1]][4] +  items[itemIndexes[2]][4]
    critDamage = .3 + items[itemIndexes[0]][5] + items[itemIndexes[1]][5] + items[itemIndexes[2]][5]
    itemDamageMultiplier = 1 + items[itemIndexes[0]][6] + items[itemIndexes[1]][6] + items[itemIndexes[2]][6]

    if itemNames[0] == 'blue buff':
        mana -= 20
    if itemNames[1] == 'blue buff':
        mana -= 20
    if itemNames[2] == 'blue buff':
        mana -= 20

    extraCritChance = 0
    if critChance >= 1:
        extraCritChance = critChance - 1
        critChance = 1

    if itemNames[0] == 'jeweled gauntlet' or itemNames[1] == 'jeweled gauntlet' or itemNames[2] == 'jeweled gauntlet':
        critDamage += extraCritChance
        dps =  ((attackDamage) + (abilityDamage * (1/mana) * manaAttack * abilityPower / 100)) * attackSpeed * itemDamageMultiplier * (1+(critChance * critDamage))
    else:
        dps = (((1+(critChance * critDamage)) * attackDamage) + (abilityDamage * (1/mana) * 10 * abilityPower / 100)) * attackSpeed * itemDamageMultiplier

    return dps

while(True):
    champName = input('Champion Name: ')
    # itemName1 = input('Item1 Name: ')
    # itemName2 = input('Item2 Name: ')
    # itemName3 = input('Item3 Name: ')
    max = 0
    maxItem1 = ''
    maxItem2 = ''
    maxItem3 = ''


    for x in range(len(items)-1):
        for y in range(len(items)-1):
            for z in range(len(items)-1):
                damagePerSecond = dps(champName, 3, [items[x+1][0], items[y+1][0], items[z+1][0]])
                print('dps: ' + str(damagePerSecond))
                print('item1: ' + items[x+1][0])
                print('item2: ' + items[y+1][0])
                print('item3: ' + items[z+1][0])
                if damagePerSecond > max:
                    max = damagePerSecond
                    maxItem1 = items[x+1][0]
                    maxItem2 = items[y+1][0]
                    maxItem3 = items[z+1][0]

    print(max)
    print(maxItem1)
    print(maxItem2)
    print(maxItem3)

    