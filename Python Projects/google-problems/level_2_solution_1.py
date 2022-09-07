def solution(xs):
    if len(xs) == 1:
        return str(xs[0])

    hasMultiplied = 0
    maximumPower = 1
    negativeCount = 0
    for i in xs:
        if i < 0:
            negativeCount += 1
    hasOddNumNegative = negativeCount % 2

    if hasOddNumNegative == 0:
        for i in xs:
            if i != 0:
                maximumPower *= i
                hasMultiplied = 1
    else:
        weakestNegative = 0
        weakestNegativeIndex = 0
        for index, i in enumerate(xs):
            if i < 0:
                if i > weakestNegative:
                    weakestNegative = i
                    weakestNegativeIndex = index

        for index, i in enumerate(xs):
            if i != 0 and index != weakestNegativeIndex:
                maximumPower *= i
                hasMultiplied = 1
    if hasMultiplied == 0:
        return str(0)
    return str(maximumPower)
        


print(solution([2,0,2,2,0]))
print(solution([-2,-3,4,-5]))
print(solution([0,0,0,0]))
print(solution([-1,0]))
print(solution([-10,1,2,3,-2,-4]))
print(solution([-1]))
