import requests


miles = float(input("Enter Miles: "))
orderValue = float(input("Enter Order Value: "))

state = "Oregon"
headers = {'authorization': "apikey 4yL4pbuOmvw81DhVtDHxp3:3nzbXTEQ8IvpjUo4S2P8TW"}
pars = {"state": state}
r = requests.get("https://api.collectapi.com/gasPrice/stateUsaPrice", headers = headers, params=pars).json()

avgPriceGas = float(r["result"]["state"]["gasoline"])
avgMPG = 25
carRepairPerMile = .052
insurancePerMonth = 126
avgMilesDrivenMonth = 1000

numberOfUniqueItems = 3
numberOfDuplicateItems = 3
numberOfItemsOver7lbs = 1
numberOfSeaFoodItems = 0
numberOfFreshVegetableItems =0

avgTimePerUnique = .5
avgTimePerDuplicateItem = .17
avgTimePerHeavyItem = .75
avgTimePerSeaFoodItem = 2
avgTimePerFreshVegetableItem = 1
avgTimeAlcohol = 1

totalDriveTime = 1.37 * miles + 5.09
loadUnloadTime = 5
standardRate = .3
isLowkelMerchant = 1
isLimitedMerchant = 0
TotalTimeSpentOnOrder = totalDriveTime + loadUnloadTime + isLimitedMerchant * avgTimeAlcohol
if isLowkelMerchant == 0:
    TotalTimeSpentOnOrder += (numberOfUniqueItems *avgTimePerUnique + numberOfDuplicateItems * avgTimePerDuplicateItem + numberOfItemsOver7lbs * avgTimePerHeavyItem + numberOfSeaFoodItems * avgTimePerSeaFoodItem + numberOfFreshVegetableItems * avgTimePerFreshVegetableItem)


carExpenses = ((miles/avgMPG)*avgPriceGas) + (carRepairPerMile * miles) + ((insurancePerMonth/avgMilesDrivenMonth) * miles)
driverTip = 3
lowkelPayToDriver = carExpenses + (TotalTimeSpentOnOrder * standardRate)
driverCreditCardProcessingFee = .029 * (lowkelPayToDriver + driverTip) + .3
FinalCostToDriver = carExpenses + driverCreditCardProcessingFee
FinalRevenueToDriver = driverTip + lowkelPayToDriver

if orderValue < 35:
    isDiscounted= 0
else:
    isDiscounted=1

if isDiscounted== 0:
    if miles < 5:
        lowkelPerDeliveryRevenue = 5
    elif miles < 10:
        lowkelPerDeliveryRevenue = 10
    elif miles < 15:
        lowkelPerDeliveryRevenue = 12.99
    elif miles < 20:
        lowkelPerDeliveryRevenue = 16.99
    elif miles < 25:
        lowkelPerDeliveryRevenue = 19.99
    elif miles < 30:
        lowkelPerDeliveryRevenue = 22.99
elif isDiscounted == 1:
    if miles < 5:
        lowkelPerDeliveryRevenue = 2.99
    elif miles < 10:
        lowkelPerDeliveryRevenue = 6.99
    elif miles < 15:
        lowkelPerDeliveryRevenue = 9.99
    elif miles < 20:
        lowkelPerDeliveryRevenue = 13.99
    elif miles < 25:
        lowkelPerDeliveryRevenue = 16.99
    elif miles < 30:
        lowkelPerDeliveryRevenue = 19.99


merchantRevenuePercent = .08
merchantRevenue = isLowkelMerchant * merchantRevenuePercent * orderValue
creditCardProcessingFee = .029 * orderValue
customerCharge = orderValue + driverTip + lowkelPerDeliveryRevenue

orderProfit = lowkelPerDeliveryRevenue - lowkelPayToDriver - creditCardProcessingFee + merchantRevenue


print("Driver Credit Card Processing fee: " + str(round(driverCreditCardProcessingFee,2)))
print("Customer Credit Card Processing fee: " + str(round(creditCardProcessingFee,2)))
print("Money to Merchant: " + str(orderValue - merchantRevenue))
print("Lowkel Pay to Driver plus tip: " + str(round(lowkelPayToDriver + driverTip,2)))
print("Profit: " + str(round(orderProfit,2)))