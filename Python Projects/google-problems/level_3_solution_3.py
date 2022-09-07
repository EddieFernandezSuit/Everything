# Bomb, Baby!
# ===========

# You're so close to destroying the LAMBCHOP doomsday device you can taste it! But in order to do so, you need to deploy special self-replicating bombs designed for you by the brightest scientists on Bunny Planet. There are two types: Mach bombs (M) and Facula bombs (F). The bombs, once released into the LAMBCHOP's inner workings, will automatically deploy to all the strategic points you've identified and destroy them at the same time. 

# But there's a few catches. First, the bombs self-replicate via one of two distinct processes: 
# Every Mach bomb retrieves a sync unit from a Facula bomb; for every Mach bomb, a Facula bomb is created;
# Every Facula bomb spontaneously creates a Mach bomb.

# For example, if you had 3 Mach bombs and 2 Facula bombs, they could either produce 3 Mach bombs and 5 Facula bombs, or 5 Mach bombs and 2 Facula bombs. The replication process can be changed each cycle. 

# Second, you need to ensure that you have exactly the right number of Mach and Facula bombs to destroy the LAMBCHOP device. Too few, and the device might survive. Too many, and you might overload the mass capacitors and create a singularity at the heart of the space station - not good! 

# And finally, you were only able to smuggle one of each type of bomb - one Mach, one Facula - aboard the ship when you arrived, so that's all you have to start with. (Thus it may be impossible to deploy the bombs to destroy the LAMBCHOP, but that's not going to stop you from trying!) 

# You need to know how many replication cycles (generations) it will take to generate the correct amount of bombs to destroy the LAMBCHOP. Write a function solution(M, F) where M and F are the number of Mach and Facula bombs needed. Return the fewest number of generations (as a string) that need to pass before you'll have the exact number of bombs necessary to destroy the LAMBCHOP, or the string "impossible" if this can't be done! M and F will be string representations of positive integers no larger than 10^50. For example, if M = "2" and F = "1", one generation would need to pass, so the solution would be "1". However, if M = "2" and F = "4", it would not be possible.

# -- Python cases --
# Input:
# solution.solution('4', '7')
# Output:
#     4

# Input:
# solution.solution('2', '1')
# Output:
#     1

def solution1(M,F):
    def generate(targetM, targetF, M, F, gen):
        if M == targetM and F == targetF:
            return [M,F,gen]
        if M > targetM or F > targetM:
            return [0,0,gen]
        
        gen += 1
        MAddF = generate(targetM, targetF, M+F, F, gen)
        FAddM = generate(targetM, targetF, M, F+M, gen)
        print(MAddF)
        print(FAddM)
        print()

        if MAddF[0] != 0 and FAddM[0] != 0:
            if MAddF[2] < FAddM[2]:
                return MAddF
            return FAddM
        if MAddF[0] != 0:
            return MAddF
        if FAddM[0] != 0:
            return FAddM
        return [0,0,gen]
    
    M = int(M)
    F = int(F)
    result = generate(M, F, 1, 1, 0)
    if result[0] == 0:
        return 'impossible'

    return str(result[2])

def solution(M,F):
    M = int(M)
    F = int(F)
    gen = 0
    while (M > 1 or F > 1):
        offset = 0
        if M == F:
            return 'impossible'
        if M > F:
            if M % F == 0:
                offset = 1
            numSteps = int(M/F) - offset
            M -= F * numSteps
        else:
            if F % M == 0:
                offset = 1
            numSteps = int(F/M) - offset
            F -= M * numSteps
        gen += numSteps
        # print(M, F)

        if M <= 0 or F <= 0:
            return 'impossible'
    return str(gen)

s = solution('2','1')
print(s)
s = solution('4','7')
print(s)
s = solution('2','4')
print(s)
s = solution('100','3')
print(s)
