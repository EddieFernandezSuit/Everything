# Uses LSFR with initialValue to return crypted or decrypted data
def Crypt(data: bytes, initialValue: int):
    LSFRValue = initialValue
    newBytesList = []
    for byte in data:
        feedbackValue = int("0x87654321",16)
        for x in range(8):
            if(LSFRValue & 1):
                LSFRValue >>= 1
                LSFRValue ^= feedbackValue
            else:
                LSFRValue >>= 1
        key = hex(LSFRValue)[-2:]
        key = int(key, 16)
        newBytesList.append(byte ^ key)
    result = bytes(newBytesList)
    return result

# prints and returns entries with data from a file from filePath
def ExtractAndDecode(filePath):
    data = getFileData(filePath,6,4)
    entryListAddress = int.from_bytes(data,"little")

    entries = []
    file = open(filePath,"rb")
    file.read(entryListAddress)

    cont = True
    while cont:
        name = file.read(16)
        if(name[:4] == b'\xff\xff\xff\xff'):
            cont = False
        else:
            blockListAddressBytes = file.read(4)
            blockListAddress = int.from_bytes(blockListAddressBytes,"little")
            entries.append(Entry(name,blockListAddress))
            
    file.close()

    for entry in entries:
        file = open(filePath, "rb")
        file.read(entry.blockListAddress)
        data = file.read(2)
        blockSize = int.from_bytes(data,"little")
        data = file.read(4)
        blockAddress = int.from_bytes(data,"little")
        file.close()
        data = getFileData(filePath,blockAddress,blockSize)
        entry.data = Crypt(data,int("0x4F574154",16))

    for entry in entries:
        print(entry.name, entry.data)
    return entries

# returns file data bytes for a file from filePath with starting at the offset and reading data until size
def getFileData(filePath, offset, size):
    file = open(filePath,"rb")
    file.read(offset)
    data = file.read(size)
    file.close()
    return data

# Stores name, data address, and data of an entry
class Entry:
    def __init__(self, name, blockListAddress):
        self.name = name
        self.blockListAddress = blockListAddress
        self.data = []