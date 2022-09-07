def solution(data, n): 
    numCount = {}
    for i in data:
        numCount[i] = 0
    for i in data:
        numCount[i] += 1
    for key in numCount:
        if numCount[key] > n:
            for i in range(numCount[key]):
                data.remove(key)
    print(numCount)
    return data


print(solution([1,2,6,6], 2))
