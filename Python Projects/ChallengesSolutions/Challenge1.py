from ChallengeFunctions import Crypt

def Test1():
    print("Test 1: Data: apple")
    data = bytes("apple", 'utf-8')
    initialValue = int("0x12345678",16)
    result = Crypt(data,initialValue)
    for byte in result:
        print(hex(byte))

def Test2():
    print("Test 2: Data: xcd x01 xef xd7 x30")
    data = b'\xCD\x01\xEF\xD7\x30'
    initialValue = int("0x12345678",16)
    result = Crypt(data,initialValue)
    print(result)

Test1()
Test2()
