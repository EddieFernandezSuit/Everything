def isEven(num):
    if (num % 2) == 0:
        return True
    return False

def numberOf2(num):
    count = 0
    while (num % 2) == 0:
        count += 1
        num = num / 2
    return count

def threexplusone(num):
    set = [num]
    oddOrEven = []
    eo = ''
    while num != 1:
        if isEven(num):
            num = num / 2
            eo = 'e'
        else:
            num = num * 3 + 1
            eo = 'o'

        oddOrEven.append(eo)
        set.append(int(num))
    return [oddOrEven, set]

ratioSet = []
success = []
upper = int(input("Enter a number: "))
startTotal = 4
while startTotal < upper:
    num = 1
    startNum2 = numberOf2(startTotal)
    ratio = []
    while num < startTotal:
        num2 = startNum2
        total = startTotal
        [oddOrEven, set] = threexplusone(num)
        for res in oddOrEven:
            if num2 > 0 and total / startTotal >= 1:
                if res == 'o':
                    total = total * 3 + 1
                elif res == 'e':
                    total = total / 2
                    num2 -= 1
        ratio.append(total / startTotal)
        num += 1

    ratioSet.append(ratio)
    print(ratio)
    su = 1
    for x in ratio:
        if x > 1:
            su = 0
    success.append(su)
    startTotal += 2

print(success)

for x in range(len(success)):
    if success[x] == 1:
        print('success')
        print(x)
        print(ratioSet[x])