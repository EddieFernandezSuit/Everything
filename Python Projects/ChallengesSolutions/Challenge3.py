from ChallengeFunctions import ExtractAndDecode
from ChallengeFunctions import getFileData
import hashlib

def getPlace(readFile, bytes):
    position = 0
    byteIndex = 0
    while(byteIndex < len(bytes)):
        position += 1
        data = readFile.read(1)
        if(int.from_bytes(data,"big") == bytes[byteIndex]):
            byteIndex+=1
        else:
            byteIndex = 0
    return position

def repairFile(magicFilePath, inputFilePath):
    entries = ExtractAndDecode(magicFilePath)
    magicBytes = entries[0].data
    magicBytesEnd = b'\xFF\xD9'

    offset = 0
    offsets = []
    sizes = []

    file = open(inputFilePath,"rb")
    for x in range(3):
        offset += getPlace(file, magicBytes)
        offsets.append(offset)
        size = getPlace(file, magicBytesEnd)
        offset += size
        sizes.append(size)
    file.close()

    for x in range(3):
        data = getFileData(inputFilePath, offsets[x], sizes[x]-2)
        repairedData = [255,216,255]
        for byte in data:
            repairedData.append(byte)
        repairedData.append(255)
        repairedData.append(216)
        repairedData.append(255)
        repairedData = bytes(repairedData)
        newFilePath = "input_repaired\_" + str(offsets[x]) + ".jpeg"
        newFile = open(newFilePath,"wb")
        newFile.write(repairedData)
        newFile.close()
        hash = hashlib.md5(repairedData).hexdigest()
        print("Offset:", offsets[x], "Size:", sizes[x], "MD5 Hash:", hash, "File Path:", newFilePath)

magicFilePath = input("Enter File Path: ")
inputFilePath = input("Enter File Path: ")
repairFile(magicFilePath, inputFilePath)