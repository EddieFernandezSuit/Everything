import pygame
import sys
from playsound import playsound

# from pydub import AudioSegment
# from pydub.playback import plays
# for playing wav file
# song = AudioSegment.from_wav("note.wav")
# print('playing sound using  pydub')
# play(song)

# playsound('C:\Users\Eddie\source\repos\PycharmProjects\ImprovementTooln\sound.mp3')
class Habit:
    def __init__(s, name, num):
        s.name = name
        s.num = num

class Timer:
    def __init__(self):
        self.timer = 60 * 30
    def update(self):
        self.timer -= 1
        print(self.timer)
        if self.timer <= 0:
            playsound('C:/Users/Eddie/source/repos/PycharmProjects/ImprovementTooln/sound.mp3')
            if isExercised[0] == 1:
                if exerciseCount[0] == exerciseCount[1]:
                    exerciseCount[0] += 1
                else:
                    exerciseCount[1] += 1
            return True

indicatorEmail = 4
indicatorIzzy = 1

def update():

    if len(timers) > 0:
        if timers[0].update():
            timers.pop(0)

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_UP:
                habits[indicator[0]].name = " " + habits[indicator[0]].name[1:]
                if indicator[0] == 0:
                    indicator[0] = len(habits) - 1
                else:
                    indicator[0] -= 1
                habits[indicator[0]].name = "|" + habits[indicator[0]].name[1:]
            if event.key == pygame.K_DOWN:
                habits[indicator[0]].name = " " + habits[indicator[0]].name[1:]
                if indicator[0] == len(habits) - 1:
                    indicator[0] = 0
                else:
                    indicator[0] += 1
                habits[indicator[0]].name = "|" + habits[indicator[0]].name[1:]
            if event.key == pygame.K_SPACE:
                if indicator[0] == 4 and isStretched[0] == 0:
                    print("m")
                    isStretched[0] = 1
                    for x in range(17):
                        timers.append(Timer())
                elif indicator[0] == 8 and isExercised[0] == 0:
                    isExercised[0] = 1
                    exerciseOnScreen[0] = 2
                    onExercise[0] += 2
                    if onExercise[0] >= len(exercises) - 1:
                        onExercise[0] = 0
                        for i in range(len(reps)):
                            reps[i] += 1
                    tempStr = str(reps[5])
                    if exercises[onExercise[0]] == tempStr + ", 24lb Shoulders up":
                        exercises[onExercise[0]] = tempStr + ", 24lb Shoulders out"
                    elif exercises[onExercise[0]] == tempStr + ", 24lb Shoulders out":
                        exercises[onExercise[0]] = tempStr + ", 24lb Shoulders forward"
                    elif exercises[onExercise[0]] == tempStr + ", 24lb Shoulders forward":
                        exercises[onExercise[0]] = tempStr + ", 24lb Shoulders up"

                    for x in range(8):
                        t = Timer()
                        t.timer = 60 * 90
                        timers.append(t)
                else:
                    isStretched[0] = 0
                    isExercised[0] = 0
                    habits[indicator[0]].num += 1
                    habits[indicator[0]].name += "|"
                    habits[indicator[0]].name = " " + habits[indicator[0]].name[1:]
                    if indicator[0] < len(habits) - 1:
                        indicator[0] += 1
                    else:
                        if habits[indicatorEmail].num % 3 == 1 or habits[indicatorEmail].num % 3 == 0:
                            habits[indicatorEmail].num += 1
                            habits[indicatorEmail].name += "|"
                        if habits[indicatorIzzy].num % 3 == 1 or habits[indicatorIzzy].num % 3 == 0:
                            habits[indicatorIzzy].num += 1
                            habits[indicatorIzzy].name += '|'
                        indicator[0] = 0

                    habits[indicator[0]].name = "|" + habits[indicator[0]].name[1:]

                    f = open("save", "w")
                    f.write(str(indicator[0]) + '\n')
                    for x in habits:
                        f.write(str(x.num) + '\n')
                    f.write(str(onExercise[0]) + '\n')
                    for i in range(len(reps)):
                        f.write(str(reps[i]) + '\n')
                    f.close()

def draw():
    for x in range(len(habits)):
        screen.blit(myFont.render(habits[x].name, 1, color.BLACK), (0,30 * x))
    for x in range(exerciseOnScreen[0]):
        screen.blit(myFont.render(exercises[onExercise[0] + x] + " " + str(exerciseCount[x]), 1, color.BLACK), (10,530 + 30 * x))
    if exerciseOnScreen[0] == 0 and onExercise[0] < 8:
        for x in range(2):
            screen.blit(myFont.render('Next: ' + exercises[onExercise[0] + x + 2], 1, color.BLACK), (10,530 + 30 * x))

def gameRun():
    loop = 1
    while loop:
        update()
        screen = pygame.display.set_mode((width, height))
        screen.fill(color.GREY)
        draw()
        pygame.display.update()
        clock.tick(60)


class color():
    GREY = (150, 150, 150)
    BLACK = (0, 0, 0)
    RED = (255, 0, 0)
    GREEN = (0, 255, 0)
    BLUE = (0, 0, 255)
    YELLOW = (255, 255, 0)
    CYAN = (0, 255, 255)
pygame.init()
width = 1000
height = 600
clock = pygame.time.Clock()
screen = pygame.display.set_mode((width, height))
fontSize = 20
myFont = pygame.font.SysFont("monospace", fontSize)
isStretched = [0]
isExercised = [0]
exerciseOnScreen = [0]
exerciseCount = [0,0]
onShoulderExercise = 0
timers = []
reps = [0,0,0,0,0,0,0]

habitNames = ["             Bed", " Brush and Floss", "            Pray", "           Email", " Stretch/Meditate",  "   Read/Learn", "   Exercise", "     Work",
              " Improve1%", " To Do", " Brush Teeth", " Complete"]
habits = []
for x in habitNames:
    while len(x) < 17:
        x = " " + x
    habits.append(Habit(x, 0))

f = open("save")
indicator = [int(f.readline())]
for x in range(len(habits)):
    habits[x].num = int(f.readline())
    for y in range(habits[x].num):
        habits[x].name += "|"
onExercise= [int(f.readline())]
for i in range(len(reps)):
    reps[i] = int(f.readline())
f.close()

exercises = [ str(reps[0])+" Bycycle Kicks - Butterfly Kicks -1/2 Leg Raises - Mountain Climbers", str(reps[1])+" 30lb Bicep Curl",
             str(reps[2])+", 20 lb Squats", str(reps[5])+", 20lb Shoulders up", str(reps[4])+", 40 lb Tricep",  str(reps[3])+" Pushups",
             str(reps[6])+", 20lb Back", "Running", "Running", "Running"]
gameRun()

