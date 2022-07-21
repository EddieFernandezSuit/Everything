import discord
import random
import pprint
import json
import time
from pymongo import MongoClient
from replit import db


def addCreature(name, totalPower, url):
    creatures.append({"name": name, "power": totalPower, "total power": totalPower, "img url": url})


mongoClient = MongoClient(
    "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false")
db = mongoClient.explore
client = discord.Client()

startStr = '.'
creatures = []
addCreature("squirrel", 1, "https://i.guim.co.uk/img/media/3aa0344c8039b86f4f7bd3981ae81d54dc985119/0_137_2741_1644/master/2741.jpg?width=1200&height=1200&quality=85&auto=format&fit=crop&s=fbc84162450a71e07d326f9c694eb678")
addCreature("rabbit", 2,"https://cdn.britannica.com/76/26076-004-FE22DEA7/Eastern-cottontail-rabbit.jpg")
addCreature("skunk", 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuERRjKUmXR9Ck0RSOb23ZogMnVzRXpfs2aA&usqp=CAU")
addCreature("cat", 4, "https://images.indianexpress.com/2020/09/hunting-cat-1200.jpg")
addCreature("deer", 5, "https://upload.wikimedia.org/wikipedia/commons/d/d5/Odocoileus_virginianus_%28white-tailed_deer_-_buck_in_velvet%29_%2817_July_2018%29_%28Newark%2C_Ohio%2C_USA%29_3_%2829623674898%29.jpg")
addCreature("camel", 6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQMpnFPXqQxpRb7jrV-QcbDct7tV5Z3wW0z-g&usqp=CAU")
addCreature("wolf", 7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz8dlpFYkGAz1mQVBlQZuCET6pSE7_8vNdhg&usqp=CAU")
addCreature("horse", 8, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzc_6KF6V3TGuHRYp8dAJXr1CpLimsWdklqA&usqp=CAU")
addCreature("Eagle", 10,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2MgIKSOmSQNLcXpp_vKVp9QWdgJ_kCcky2w&usqp=CAU")
addCreature("crocodile", 12,"https://media.nationalgeographic.org/assets/photos/279/385/d22fa87f-c6c5-4dc6-b6e1-e03450e9b4d3.jpg")
addCreature("bear", 16,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF74zXZggtm2Sm-gPGussCPNZFJx2KXI_E4Q&usqp=CAU")
addCreature("Lion", 20,"https://media.vanityfair.com/photos/5d2750b1abb5c9000873bced/1:1/w_1165,h_1165,c_limit/lion-king-review.jpg")
addCreature("gorilla", 25,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScefadbMcHVyOjO0Lcre7nL0NyfCuC2f1aZA&usqp=CAU")
addCreature("elephant", 30,"https://www.motherjones.com/wp-content/uploads/2019/12/Getty121719.jpg?w=990")
addCreature("dragon", 50,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ__5DdKU2p-AhsbVpG1ZkwYfn8QL0BFPcfA&usqp=CAU")
addCreature("elder dragon", 100,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH-ijbQZP4W2d588qsYssb9WaqP6fWYdvUYw&usqp=CAU")
choice = " Fight, run, or team up\n"


@client.event
async def on_ready():
    print("We have logged in as {0.user}".format(client))


@client.event
async def on_message(message):
    def setSleep():
        db.data.update_one({"player": username}, {"$set": {"state": "sleep"}})
        db.data.update_one({"player": username}, {"$set": {"encounters": "none"}})
        return f'You rest up back to full power\n' + setPlayerStat(getPlayer(), "power", getStat("total power"))

    def setPlayerStat(player, stat, val):
        chat_ = ""
        if type(val) == type(1.1) or type(val) == type(1):
            change = val - player[stat]
            if change >= 0:
                gainOrLose = "gains"
            else:
                gainOrLose = "loses"
                change *= -1
            chat_ = f'{player["player"]} {gainOrLose} {change} {stat} and is now {val} {stat}\n'
            val = round(val)
        player[stat] = val
        db.data.replace_one({"player": player["player"]}, player)
        return chat_

    def getPlayer():
        return db.data.find_one({"player": username})

    def getStat(stat):
        return db.data.find_one({"player": username})[stat]

    def explore():
        newEncounter = random.choice(creatures)
        player = db.data.find_one({"player": username})
        return setPlayerStat(player, "state", "encounter") + setPlayerStat(player, "encounters", newEncounter) + f"You explore the lands and encounter a {newEncounter['name']}\n You have {getStat('power')} power. {choice} {newEncounter['img url']}"

    msg = message.content

    if message.author == client.user:
        return
    if msg.startswith(startStr):
        username = str(message.author)
        chat = f"<@{message.author.id}>\n"
        if getPlayer() is None:
            db.data.insert_one({"player": username, "state": 'sleep', "power": 3, "total power": 3, "encounters": "none", "xp": 0, "team up": 0, "party": [username]})
            chat += f'Welcome to edWorld, {username}\n'

        if db.data.find_one({"player": username}) is not None:
            if db.data.find_one({"player": username})['state'] == 'sleep':
                if msg.startswith(startStr + "explore"):
                    chat += explore()

            if db.data.find_one({"player": username})['state'] == 'encounter':
                if msg.startswith(startStr + "fight"):
                    encounter = getStat("encounters")
                    for member in getStat("party"):
                        player = db.data.find_one({"player": member})
                        encounter["power"] = encounter["power"] - player['power']
                        chat += f'{player["player"]} strikes the {encounter["name"]}. It Loses {player["power"]} power and is now {encounter["power"]}\n'
                        player["encounters"] = encounter
                        db.data.replace_one({"player": player["player"]}, player)

                    if encounter["power"] > 0:
                        for member in getStat("party"):
                            player = db.data.find_one({"player": member})
                            chat += setPlayerStat(player, "power", player["power"] - encounter["power"])
                            if player["power"] <= 0:
                                chat += f'{player["player"]} dies to the {encounter["name"]}\n'
                                db.data.delete_one({"player": player["player"]})
                            else:
                                chat += choice
                            db.data.replace_one({"player": player["player"]}, player)

                    elif encounter["power"] <= 0:
                        for member in getStat("party"):
                            chat += f'{member} '
                        chat += f"defeats the {encounter['name']}\n"
                        for member in getStat("party"):
                            player = db.data.find_one({"player": member})
                            xpGain = round(100 * encounter["total power"]/player["total power"])
                            chat += setPlayerStat(player, "xp", player["xp"] + xpGain)
                            while player["xp"] >= 100:
                                chat += f"{player['player']}'s experience makes them stronger\n {setPlayerStat(player, 'xp', player['xp'] - 100)} {setPlayerStat(player, 'total power', player['total power'] + 1)}"
                            player["team up"] = 0
                            player["party"] = [player["player"]]
                            db.data.replace_one({"player": player["player"]}, player)
                        chat += explore()

                if msg.startswith(startStr + "run"):
                    chat += " You run away from the " + getStat("encounters")["name"] + "\n"
                    chat += setSleep()

        if msg.startswith(startStr + "team up"):
            player = db.data.find_one({"player": username})
            setPlayerStat(player, "team up", 1)
            party = []
            for document in db.data.find({}):
                if document["team up"] == 1:
                    party.append(document["player"])
            chat += 'You are in a party of '
            for name in party:
                chat += f'{name} '
            setPlayerStat(player, "party", party)
            db.data.replace_one({"player": player["player"]}, player)

        if chat != "":
            await message.channel.send(chat)


client.run('ODgzODI2MjU4NTg3MzEyMTQ5.YTPlWA.sgL4icm5FP3C6n6EVTNUavUSNhc')
