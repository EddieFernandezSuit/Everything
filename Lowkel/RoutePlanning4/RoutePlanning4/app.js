class person {
    constructor(type) {
        this.type = type;
        this.long = getRandomArbitrary(-122.749674, -122.285354);
        this.lat = getRandomArbitrary(45.251939, 45.542692);
        this.storeOrder = [];
        this.storeOrderTime = getRandomInt(9, 21);
        this.marker = null;
        this.includesIceCream = [];
        let temp = [];
        let tempRand = getRandomInt(0, numStores - 1);
        let check = 1;
        for (let i = 0; i < getRandomInt(1,4); i++) {
            for (let j = 0; j < temp.length; j++) {
                if (temp[j] == tempRand) {
                    check = 0;
                }
            }
            if (check == 1) {
                this.storeOrder.push(tempRand);
                this.includesIceCream.push(0);
                temp.push(tempRand);
                tempRand = getRandomInt(0, numStores - 1);
            } else {
                check = 1
            }
        }
    }
}

let numCustomers = 200;
let numDrivers = 40;
let numStores = 20;
let movedDrivers = [];
let storeId = 0;
let timeOfDay = 9;
let api_key = "AIzaSyA44QO98-mvTJM1LgrMNRPDYD5i4oRdAs8";
let totalProfit = 0;
let customers = [];
let drivers = [];
let stores = [];
let totalMiles = 0;
let totalTime = 0;
for (let i = 0; i < numCustomers; i++) {
    customers.push(new person("customer"));
}
for (let i = 0; i < numStores; i++) {
    stores.push(new person("store"));
    stores[i].storeOrder = i;
}
for (let i = 0; i < numDrivers; i++) {
    drivers.push(new person("driver"));
}
let map;
let lat1 = (45.542692 + 45.251939) / 2;
let long1 = (-122.749674 + -122.285354) / 2;
let portlandCenter = { lat: lat1, lng: long1 };
let zoom = 10;
let directionsService;
let directionsRenderer;
let request;
let route = [];
let orderDistance = [];
let maxMiles = 30;

parseCSV('StoreLocation.csv', ',', (res) => {
    console.log(res)
})

function parseCSV(file, delimiter, callback) {
    var reader = new FileReader();

    // When the FileReader has loaded the file...
    reader.onload = function () {

        // Split the result to an array of lines
        var lines = this.result.split('\n');

        // Split the lines themselves by the specified
        // delimiter, such as a comma
        var result = lines.map(function (line) {
            return line.split(delimiter);
        });

        // As the FileReader reads asynchronously,
        // we can't just return the result; instead,
        // we're passing it to a callback function
        callback(result);
    };

    // Read the file content as a single string
    reader.readAsText(file);
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); //The maximum is exclusive and the minimum is inclusive
}
function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}
function createMarkers(group,url) {
    for (let i = 0; i < group.length; i++) {
        let label1 = '';
        label1 += group[i].type[0] + i;
        new google.maps.Marker({
            position: { lat: group[i].lat, lng: group[i].long },
            icon: {
                url: url
            },
            label: label1,
            map
        })
    }
}
function initMap() {
    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer();
    map = new google.maps.Map(document.getElementById("map"), {
        center: portlandCenter,
        zoom: zoom,
    });

    for (let i = 0; i < customers.length; i++) {
        let label1 = '';
        label1 += customers[i].type[0] + customers[i].storeOrderTime
        for (let j = 0; j < customers[i].storeOrder.length; j++) {
            label1 += '-' + customers[i].storeOrder[j].toString()
        }
        customers[i].marker = new google.maps.Marker({
            position: { lat: customers[i].lat, lng: customers[i].long },
            label: label1,
            map,
        })
    }

    for (let i = 0; i < stores.length; i++) {
        let label1 = '';
        label1 += stores[i].type[0] + i;
        stores[i].marker = new google.maps.Marker({
            position: { lat: stores[i].lat, lng: stores[i].long },
            icon: {
                url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
            },
            label: label1,
            map
        })
    }

    for (let i = 0; i < drivers.length; i++) {
        let label1 = '';
        label1 += drivers[i].type[0] + i;
        drivers[i].marker = new google.maps.Marker({
            position: { lat: drivers[i].lat, lng: drivers[i].long },
            icon: {
                url: "http://maps.google.com/mapfiles/ms/icons/green-dot.png"
            },
            label: label1,
            map
        })
    }

    directionsRenderer.setMap(map);

}
function keyCode(event) {
    let x = event.keyCode;
    if (x == 32) {
        algorithm3()
    }
    else if (x == 27) {
        console.log('running stats')
        calculateStats(totalMiles, totalTime, route, orderDistance)
    }
    else if (x == 81) {
        algorithm5()
    }

}
function deg2rad(deg) {
    return deg * (Math.PI / 180)
}
function getDistanceFromLatLonInMi(lat1, lon1, lat2, lon2) {
    let R = 6371; // radius of the earth in km
    let dLat = deg2rad(lat2 - lat1)  // deg2rad below
    let dLon = deg2rad(lon2 - lon1)
    let a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(
        dLon / 2) * Math.sin(dLon / 2)
    let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    let d = R * c  // Distance in mi
    return (d * .621371)
}
function findFarthestBetweenPersonGroup(person1, group) {
    let maxDistance = 0;
    let x = 1000;
    for (let i = 0; i < group.length; i++) {
        let currentDistance = getDistanceFromLatLonInMi(person1.lat, person1.long, group[i].lat, group[i].long)
        if (maxDistance < currentDistance) {
            maxDistance = currentDistance;
            x = i
        }
    }
    if (x == 1000) {
        console.log('no next')
    }
    return x
}
function findClosestBetweenPersonGroup(person1, group) {
    let minDistance = 100000;
    let x = 1000;

    for (let i = 0; i < group.length; i++) {
        let currentDistance = getDistanceFromLatLonInMi(person1.lat, person1.long, group[i].lat, group[i].long);
        if (minDistance > currentDistance) {
            minDistance = currentDistance;
            x = i;
        }
    }
    if (x == 1000) {
        console.log("no next");
    }
    return x;
}
function routeWithoutFirst(route) {
    let tempRoute = [];
    for (let i = 0; i < route.length; i++) {
        tempRoute.push(route[i])
    }
    tempRoute.splice(0, 1);
    return tempRoute;
}
function checkIceCreamTime() {   
    
    if (route[route.length - 1].type == 'customer') {
        let storeToCustomerDistance = 0;
        customer = route[route.length-1]
        for (let i = 0; i < customer.storeOrder.length; i++) {
            for (let j = 0; j < route.length; j++) {
                if (route[j].type == 'store') {
                    if (route[j].storeOrder == customer.storeOrder[i]) {
                        if (customer.includesIceCream[i] == 1) {

                            for (let k = i; k < route.length - 1; k++) {
                                storeToCustomerDistance += getDistanceFromLatLonInMi(route[k].lat, route[k].long, route[k + 1].lat, route[k + 1].long);
                            }
                            if (storeToCustomerDistance <= 15) {
                                return 1
                            }
                            else {
                                return 0
                            }
                        }
                    }
                }
            }
        }
    }

    return 1
}
function addRouteCheckTime(potentialNext) {
    let index = findClosestBetweenPersonGroup(route[route.length - 1], potentialNext)
    let tempMile = 0;
    let loop = 0
    if (index != 1000) {
        route.push(potentialNext[index])
        if (checkIceCreamTime() == 1) {
            for (let i = 0; i < route.length - 1; i++) {
                tempMile += getDistanceFromLatLonInMi(route[i].lat, route[i].long, route[i + 1].lat, route[i + 1].long)
            }

            if (tempMile <= maxMiles && route.length <= 10) {
                loop = 1;
            }
            else {
                route.splice(route.length - 1, 1);
                loop = 0;
            }
        }
        else {
            route.splice(route.length - 1, 1);
            loop = 0
        }
    }
    return [loop, index]
}
function endAlg(route) {
    calcRoute(route)
    //console.log("Miles: " + miles + ", Time: " + time)
    //calculateStats(miles,time,route,orderDistance)
}
function round2(num) {
    return Math.round((num + Number.EPSILON) * 100) / 100
}
function calcRoute(route) {
    let start = { lat: route[0].lat, lng: route[0].long };
    let end = { lat: route[route.length - 1].lat, lng: route[route.length - 1].long };
    let w1 = []

    for (let i = 1; i < route.length - 1; i++) {
        w1.push({ location: { lat: route[i].lat, lng: route[i].long } })
    }


    request = {
        origin: start,
        destination: end,
        waypoints: w1,
        travelMode: 'DRIVING'
    };

    directionsService.route(request, function (result, status) {
        console.log(status)
        if (status == 'OK') {
            
            for (let i = 1; i < result.routes[0].legs.length; i++) {
                totalMiles += Math.floor(result.routes[0].legs[i].distance.value / 1609.34)
                totalTime += Math.floor(result.routes[0].legs[i].duration.value / 60)
            }
            directionsRenderer.setDirections(result);

        }
    });
}
function calculateStats(miles, totalDriveTime, route, orderDistance) {
    let s = 0;
    let c = 0;
    for (let i = 0; i < route.length; i++) {
        if (route[i].type == 'customer') {
            c += 1;
        }
        else if (route[i].type == 'store') {
            s += 1;
        }
    }

    // state = "Oregon"
    // headers = { 'authorization': "apikey 4yL4pbuOmvw81DhVtDHxp3:3nzbXTEQ8IvpjUo4S2P8TW" }
    // pars = { "state": state }
    // r = requests.get("https://api.collectapi.com/gasPrice/stateUsaPrice", headers = headers, params = pars).json()
    // avgPriceGas = float(r["result"]["state"]["gasoline"])

    let orderValue = 15;
    let avgPriceGas = 3;
    let avgMPG = 25
    let carRepairPerMile = .052
    let insurancePerMonth = 126
    let avgMilesDrivenMonth = 1000

    let numberOfUniqueItems = 3
    let numberOfDuplicateItems = 3
    let numberOfItemsOver7lbs = 1
    let numberOfSeaFoodItems = 0
    let numberOfFreshVegetableItems = 0

    let avgTimePerUnique = .5
    let avgTimePerDuplicateItem = .17
    let avgTimePerHeavyItem = .75
    let avgTimePerSeaFoodItem = 2
    let avgTimePerFreshVegetableItem = 1
    let avgTimeAlcohol = 1

    let loadUnloadTime = 5 * s
    let customerDropOffTime = c
    let standardRate = .3
    let isLowkelMerchant = 1
    let isLimitedMerchant = 0
    let TotalTimeSpentOnOrder = totalDriveTime + loadUnloadTime + customerDropOffTime + isLimitedMerchant * avgTimeAlcohol

    if (isLowkelMerchant == 0) {
        TotalTimeSpentOnOrder += (numberOfUniqueItems * avgTimePerUnique + numberOfDuplicateItems * avgTimePerDuplicateItem + numberOfItemsOver7lbs * avgTimePerHeavyItem + numberOfSeaFoodItems * avgTimePerSeaFoodItem + numberOfFreshVegetableItems * avgTimePerFreshVegetableItem)
    }
    let carExpenses = ((miles / avgMPG) * avgPriceGas) + (carRepairPerMile * miles) + (
        (insurancePerMonth / avgMilesDrivenMonth) * miles)
    let driverTip = 0
    let lowkelPayToDriver = carExpenses + (TotalTimeSpentOnOrder * standardRate)
    let driverCreditCardProcessingFee = .029 * (lowkelPayToDriver + driverTip) + .3
    let finalCostToDriver = carExpenses + driverCreditCardProcessingFee
    let finalRevenueToDriver = driverTip + lowkelPayToDriver
    let isDiscounted;
    if (orderValue < 35) {
        isDiscounted = 0
    }
    else {
        isDiscounted = 1
    }

    let lowkelPerDeliveryRevenue = 0

    for (let i = 0; i < orderDistance.length; i++) {
        let nonDiscountPrices = [5, 10, 12.99, 16.99, 19.99, 22.99]
        let discountPrices = [2.99,6.99,9.99,12.99,16.99,19.99]
        let j = 1
        let loop = 1
        while (loop) {
            if (orderDistance[i] < j * 5) {
                if (j > 6) {
                    j = 6
                }
                if (isDiscounted == 0) {
                    lowkelPerDeliveryRevenue += nonDiscountPrices[j-1]
                }
                else if (isDiscounted == 1) {
                    lowkelPerDeliveryRevenue += discountPrices[j-1]
                }
                loop = 0
            }
            j++
        }
    }

    let numberOfOrders = orderDistance.length
    let merchantRevenuePercent = .08
    let merchantRevenue = isLowkelMerchant * merchantRevenuePercent * orderValue * numberOfOrders
    let creditCardProcessingFee = .029 * orderValue * numberOfOrders
    let customerCharge = orderValue * numberOfOrders + driverTip + lowkelPerDeliveryRevenue

    let orderProfit = lowkelPerDeliveryRevenue - lowkelPayToDriver - creditCardProcessingFee + merchantRevenue
    totalProfit += orderProfit

    console.log("Order Distances")
    console.log(orderDistance)
    console.log("Total Miles: " + miles)
    console.log("Total Time: " + totalDriveTime)
    console.log("number of Orders: " + numberOfOrders)
    console.log("Driver Credit Card Processing fee: " + (round2(driverCreditCardProcessingFee, 2)))
    console.log("Customer Credit Card Processing fee: " + (round2(creditCardProcessingFee, 2)))
    console.log("Money to Merchant: " + (orderValue * numberOfOrders - merchantRevenue))
    console.log("Lowkel Pay to Driver plus tip: " + (round2(finalRevenueToDriver, 2)))
    console.log("Cost to Driver: " + (round2(finalCostToDriver, 2)))
    console.log("Customer Charge: " + (round2(customerCharge)))
    console.log("Lowkel Per Delivery Revenue: " + (round2(lowkelPerDeliveryRevenue, 2)))
    console.log("Merchant revenue: " + (merchantRevenue))
    console.log("Total Order Value: " + (orderValue * numberOfOrders))
    console.log("Profit: " + (round2(orderProfit, 2)))
    console.log(" ")
}
function isInGroup(person, route) {
    for (let i = 0; i < route.length; i++) {
        if (person == route[i]) {
            return 1
        }
    }
    return 0
}
function algorithm3() {
    totalMiles = 0;
    totalTime = 0;
    route = []
    orderDistance = []
    let storeIdInRoute = [];
    let potentialNext = [];
    let cIndex = -1

    while (cIndex == -1) {
        for (i = 0; i < customers.length; i++) {
            if (customers[i].storeOrderTime == timeOfDay) {
                cIndex = i;
            }
        }
        if (cIndex == -1) {
            timeOfDay += 1;
        }
    }

    console.log("time of day: " + timeOfDay);
    for (i = 0; i < customers[cIndex].storeOrder.length; i++) {
        storeIdInRoute.push(customers[cIndex].storeOrder[i]);
    }

    let l = []
    for (let i = 0; i < customers[cIndex].storeOrder.length; i++) {
        l.push(stores[customers[cIndex].storeOrder[i]]);
    }

    let ind = findFarthestBetweenPersonGroup(customers[cIndex], l);
    let index = findClosestBetweenPersonGroup(l[ind], drivers);

    let dIndex = index
    route.push(drivers[index]);
    route.push(l[ind]);

    for (i = 0; i < customers[cIndex].storeOrder.length; i++) {
        if (stores[customers[cIndex].storeOrder[i]] != l[ind]) {
            potentialNext.push(stores[customers[cIndex].storeOrder[i]]);
        }
    }


    for (i = potentialNext.length-1; i >= 0; i--) {

        index = findClosestBetweenPersonGroup(route[route.length - 1], potentialNext)
        route.push(potentialNext[index])
        potentialNext.splice(index, 1)
    }

    route.push(customers[cIndex]);

    for (i = 0; i < customers[cIndex].storeOrder.length; i++) {
        let tempMiles;
        tempMiles = getDistanceFromLatLonInMi(stores[customers[cIndex].storeOrder[i]].lat, stores[customers[cIndex].storeOrder[i]].long, customers[cIndex].lat, customers[cIndex].long)
        orderDistance.push(tempMiles)
    }


    customers[cIndex].marker.setMap(null)
    customers.splice(cIndex, 1)

    for (let i = customers.length-1; i >= 0; i--) {
        for (let j = customers[i].storeOrder.length-1; j >= 0; j--) {
            for (k = 0; k < storeIdInRoute.length; k++) {
                if (customers[i].storeOrder[j] == storeIdInRoute[k] && customers[i].storeOrderTime == timeOfDay) {
                    potentialNext.push(customers[i])
                }
            }
        }
    }

    let toDelete = []
    let loop = 1;
    while (loop == 1 && potentialNext.length > 0) {

        values = addRouteCheckTime(potentialNext)
        loop = values[0]
        index = values[1]
        if (loop == 1) {
            for (i = 0; i < customers.length; i++) {
                if (customers[i] == potentialNext[index]) {
                    for (j = 0; j < storeIdInRoute.length; j++) {
                        for (k = 0; k < customers[i].storeOrder.length;k++) {
                            if (storeIdInRoute[j] == customers[i].storeOrder[k]) {
                                let m
                                m = getDistanceFromLatLonInMi(stores[customers[i].storeOrder[k]].lat, stores[customers[i].storeOrder[k]].long, customers[i].lat, customers[i].long)

                                orderDistance.push(m)
                                customers[i].storeOrder.splice(k, 1)
                                if (customers[i].storeOrder.length == 0) {
                                    toDelete.push(customers[i])
                                }
                                break
                            }
                        }
                    }
                }
            }
            potentialNext.splice(index, 1)
        }
    }

    for (i = toDelete.length - 1; i >= 0; i--) {
        for (j = 0; j < customers.length; j++) {
            if (customers[j] == toDelete[i]) {
                customers[j].marker.setMap(null)
                customers.splice(j, 1)
                break
            }
        }
    }

    drivers[dIndex].lat = route[route.length - 1].lat
    drivers[dIndex].long = route[route.length - 1].long
    endAlg(route)
    return
}
function algorithm5() {
    totalMiles = 0;
    totalTime = 0;
    route = []
    orderDistance = []
    let storeIdInRoute = [];
    let potentialNext = [];
    let firstCustomerIndex = -1;

    timeOfDay -= 1
    while (firstCustomerIndex == -1) {
        timeOfDay += 1
        for (let i = 0; i < customers.length; i++) {
            if (customers[i].storeOrderTime == timeOfDay) {
                firstCustomerIndex = i
                break
            }
        }
    }

    let firstStoreIndex = customers[firstCustomerIndex].storeOrder[0]

    let driverIndex = findClosestBetweenPersonGroup(stores[firstStoreIndex],drivers)

    route.push(drivers[driverIndex])
    route.push(stores[firstStoreIndex])
    storeIdInRoute.push(firstStoreIndex)


    for (let i = 0; i < customers.length; i++) {
        if (customers[i].storeOrderTime == timeOfDay) {
            for (let j = 0; j < customers[i].storeOrder.length; j++) {
                for (let k = 0; k < storeIdInRoute.length; k++) {
                    if (customers[i].storeOrder[j] == storeIdInRoute[k]) {
                        potentialNext.push(customers[i])
                    }
                }
            }
        }
    }

    for (let i = 0; i < stores.length; i++) {
        let a = 0
        for (let j = 0; j < storeIdInRoute; j++) {
            if (storeIdInRoute[j] == i) {
                a = 1
                break
            }
        }
        if (a == 0) {
            potentialNext.push(stores[i])
        }
    }

    let wasRemoved = 1;
    while (wasRemoved == 1) {

        let loop = 1;
        while (loop == 1 && potentialNext.length > 0) {
            let values = addRouteCheckTime(potentialNext);
            loop = values[0];
            let nextIndex = values[1];

            if (loop == 1) {
                if (route[route.length - 1].type == 'store') {
                    for (let h = 0; h < stores.length; h++) {
                        if (stores[h] == route[route.length - 1]) {
                            storeIdInRoute.push(h);
                            for (let i = 0; i < customers.length; i++) {
                                for (let j = 0; j < customers[i].storeOrder.length; j++) {
                                    if (customers[i].storeOrder[j] == h && customers[i].storeOrderTime == timeOfDay && isInGroup(customers[i], route) == 0 && isInGroup(customers[i], potentialNext) == 0) {
                                        potentialNext.push(customers[i])
                                    }

                                    /*for (let k = 0; k < storeIdInRoute.length; k++) {
                                        if (customers[i].storeOrder[j] == storeIdInRoute[k]) {
                                            if (customers[i].storeOrderTime == timeOfDay) {
                                                if (isInGroup(customers[i], route) == 0 && isInGroup(customers[i], potentialNext) == 0) {
                                                    potentialNext.push(customers[i])
                                                }
                                            }
                                        }
                                    }*/
                                }
                            }
                            break
                        }
                    }
                }

                potentialNext.splice(nextIndex, 1)
            }
        }


        wasRemoved = 0;
        for (let i = route.length - 1; i >= 0; i--) {
            if (route[i].type == 'store') {
                let isStoreUsed = 0;
                for (let j = 0; j < stores.length; j++) {
                    if (route[i] == stores[j]) {
                        for (let k = i; k < route.length; k++) {
                            if (route[k].type == 'customer') {
                                for (let l = 0; l < route[k].storeOrder.length; l++) {
                                    if (route[k].storeOrder[l] == j) {
                                        isStoreUsed = 1
                                        break;
                                    }
                                }
                            }
                        }
                        break
                    }
                }
                if (isStoreUsed == 0) {
                    for (let j = 0; j < stores.length; j++) {
                        if (stores[j] == route[i]){
                            for (let k = 0; k < storeIdInRoute.length; k++) {
                                if (storeIdInRoute[k] == j) {
                                    storeIdInRoute.splice(k, 1)
                                    for (let l = potentialNext.length - 1; l >= 0; l--) {
                                        if (potentialNext[l].type == 'customer') {
                                            for (let m = 0; m < potentialNext[l].storeOrder.length; m++) {
                                                if (potentialNext[l].storeOrder[m] == j) {                                                    

                                                    let check = 0;
                                                    for (let n = 0; n < potentialNext[l].storeOrder.length; n++) {
                                                        for (let o = 0; o < storeIdInRoute.length; o++) {
                                                            if (storeIdInRoute[o] == potentialNext[l].storeOrder[n]) {
                                                                
                                                                check = 1;
                                                            }
                                                        }
                                                    }
                                                    if (check == 0) {
                                                        potentialNext.splice(l, 1)
                                                    }
                                                    break
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break
                        }
                    }
                    route.splice(i, 1)
                    wasRemoved = 1
                    break
                }
                else {
                    wasRemoved = 0
                }
            }
        }
    }

    for (let i = 0; i < route.length; i++) {
        for (let j = 0; j < customers.length; j++) {
            if (route[i] == customers[j]) {
                for (let l = 0; l < storeIdInRoute.length; l++) {
                    for (let k = customers[j].storeOrder.length - 1; k >= 0; k--) {
                        if (customers[j].storeOrder[k] == storeIdInRoute[l]) {
                            orderDistance.push(getDistanceFromLatLonInMi(customers[j].lat,customers[j].long,stores[customers[j].storeOrder[k]].lat,stores[customers[j].storeOrder[k]].long))
                            customers[j].storeOrder.splice(k, 1)
                        }
                    }
                }
            }
        }
    }

    for (let i = customers.length - 1; i >= 0; i--) {
        if (customers[i].storeOrder.length == 0) {
            customers[i].marker.setMap(null)
            customers.splice(i,1)
        }
    }

    console.log(route)

    if (route.length > 1) {
        //dInd = findClosestBetweenPersonGroup(route[1], drivers);
        //route[0] = drivers[dInd];
        endAlg(route);
        drivers[driverIndex].lat = route[route.length - 1].lat;
        drivers[driverIndex].long = route[route.length - 1].long;
        drivers[driverIndex].marker.setPosition(new google.maps.LatLng(drivers[driverIndex].lat, drivers[driverIndex].long));
    }
    else {
        console.log('route too short')
    }
}
