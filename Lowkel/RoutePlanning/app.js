class person {
    constructor(type) {
        this.type = type;
        this.long = getRandomArbitrary(-122.749674, -122.285354);
        this.lat = getRandomArbitrary(45.251939, 45.542692);
        this.storeOrder = [];
        this.storeOrderTime = getRandomInt(9, 21);
        let temp = [];
        let tempRand = getRandomInt(0, numStores - 1);
        let check = 1;
        for (let i = 0; i < getRandomInt(1, 4); i++) {
            for (let j = 0; j < temp.length;j++) {
                if (temp[j] == tempRand) {
                    check = 0;
                } 
            }
            if (check == 1) {
                this.storeOrder.push(tempRand);
                temp.push(tempRand);
                tempRand = getRandomInt(0, numStores - 1);
            } else {
                check = 1
            }
        }
    }
}

let numCustomers = 100;
let numDrivers = 40;
let numStores = 20;
let movedDrivers = [];
let storeId = 0;
let timeOfDay = 9;
let api_key = "AIzaSyA44QO98-mvTJM1LgrMNRPDYD5i4oRdAs8";
let totalProfit = 0;
let customers = []
let drivers = []
let stores = []

for (i = 0; i < numCustomers; i++) {
    customers.push(new person("customer"))
}
for (i = 0; i < numStores; i++) {
    stores.push(new person("store"))
}
for (i = 0; i < numDrivers; i++) {
    drivers.push(new person("driver"))
}

let map;
let marker;
let lat1 = (45.542692 + 45.251939) / 2;
let long1 = (-122.749674 + -122.285354) / 2;
let portlandCenter = { lat: lat1, lng: long1 };
let zoom = 11;
let directionsService;
let directionsRenderer;

document.addEventListener('keyup', event =>{
    if(event.code === 'Space'){
        calcRoute()
    }
})

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); //The maximum is exclusive and the minimum is inclusive
}

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

function initMap() {
    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer();
    map = new google.maps.Map(document.getElementById("map"), {
        center: portlandCenter,
        zoom: zoom,
    });
    directionsRenderer.setMap(map);
    
}

function calcRoute(route) {
    let miles = []
    let times = []
    let start = { lat: getRandomInt(40, 50), lng: getRandomInt(-125, -115) };
    let end = { lat: getRandomInt(40, 50), lng: getRandomInt(-125, -115) };
    let w1 = [{ location: { lat: getRandomInt(40, 50), lng: getRandomInt(-125, -115) } }, { location: { lat: getRandomInt(40, 50), lng: getRandomInt(-125, -115) } }];
    var request = {
        origin: start,
        destination: end,
        waypoints: w1,
        travelMode: 'DRIVING'
    };
    directionsService.route(request, function (result, status) {
        if (status == 'OK') {
            directionsRenderer.setDirections(result);
            console.log(result);
            for (i = 0; i < result.routes[0].legs.length; i++) {
                m = Math.floor(result.routes[0].legs[i].duration.value / 60)
                t = Math.floor(result.routes[0].legs[i].duration.value / 60)
                console.log(i + ' ' + m + ' miles');
                console.log(i + ' ' + t + ' minutes');
                miles.push(m)
                times.push(t)

            }
        }
    });

    return miles, times;
}

function keyCode(event) {
    
    var x = event.keyCode;
    if (x == 32) {
        calcRoute()
    }
}

function deg2rad(deg) {
    return deg * (Math.PI / 180)
}

function getDistanceFromLatLonInMi(lat1, lon1, lat2, lon2) {
    let R = 6371; // radius of the earth in km
    let dLat = deg2rad(lat2 - lat1)  // deg2rad below
    let dLon = deg2rad(lon2 - lon1)
    let a = math.sin(dLat / 2) * math.sin(dLat / 2) + math.cos(deg2rad(lat1)) * math.cos(deg2rad(lat2)) * math.sin(
        dLon / 2) * math.sin(dLon / 2)
    let c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    let d = R * c  // Distance in mi
    return (d * .621371)
}

function findClosestBetweenPersonGroup(person1, group) {
    maxDistance = 0
    i = 1000
    for (i = 0; i < group.length; i++) {
        currentDistance = getDistanceFromLatLonInMi(person1.lat, person1.long, group[i].lat, group[i].long);
        if (maxDistance < currentDistance) {
            maxDistance = currentDistance;
            i = x
        }
    }
    if (i = 1000) {
        print("no next")
    }
    return i
}

function routeWithoutFirst(route) {
    tempRoute = [];
    for (i = 0; i < route.length; i++) {
        tempRoute.push(route[i])
    }
    tempRoute.splice(0, 1);
    return tempRoute;
}

function addRouteCheckTime(route, potentialNext) {
    index = findClosestBetweenPersonGroup(route[-1], potentialNext)
    route.push(potentialNext[index])
    tempMile, w = calcRoute(routeWithoutFirst(route))
    if (w <= 60) {
        loop = 1;
    }
    else {
        route.splice(-1, 1);
    }
    return route, loop, index;
}

function algorithm3() {
    orderDistance = [];
    route = [];
    storeIdInRoute = [];
    potentialNext = [];

    let cIndex = -1
    while (cIndex == -1) {
        for (i = 0; i < customers.length; i++) {
            if (customers[i].storeOrderTime == timeOfDay) {
                cIndex = i;
            }
        }
        if (cIndex == -1){
            timeOfDay += 1;
        }
    }

    for (i = 0; i < customers[cIndex].storeOrder.length; i++) {
        storeIdInRoute.push(customers[cIndex].storeOrder[i]);
    }

    console.log("time of day: " + timeOfDay);
    l = []
    for (i = 0; i < customers[cIndex].storeOrder.length; i++) {
        l.push(stores[customers[cIndex].storeOrder[i]]);
    }

    ind = findFarthestBetweenPersonGroup(customers[cIndex], l);
    index = findClosestBetweenPersonGroup(l[ind], drivers);

    route.push(drivers[index]);
    drivers.splice(index, 1);
    route.push(l[ind]);

    for (i = 0; i < customers[cIndex].storeorder.length; i++) {
        if (stores[customers[cIndex].storeOrder[i]] != l[ind]) {
            potentialNext.push(stores[customers[cIndex].storeOrder[i]]);
        }
    }

    for (i = potentialNext.length; i >= 0; i--) {
        index = findClosestBetweenPersonGroup(route[route.length - 1], potentialNext)
        route.push(potentialNext[index])
        potentialNext.splice(index, 1)
    }

    route.push(customers[cIndex]);
    for (i = 0; i < customers[cIndex].storeOrder; i++) {
        tempMiles, tempTime = calcRoute([stores[customers[cIndex].storeOrder[i]], customers[cIndex]])
        orderDistance.push(tempMiles)
    }
    customers.splice(cIndex, 1)

    toDelete = []
    for (i = customers.length; i >= 0; i--) {
        for (j = customers[i].storeOrder.length; j >= 0; j++) {
            for (k = 0; k < storeIdInRoute.length; k++) {
                if (customers[i].storeOrder[j] == storeIdInRoute[k] && customers[i].storeOrderTime == timeOfDay) {
                    potentialNext.push(customers[i])
                }
            }
        }
    }
    let loop = 1;
    while (loop == 1 && potentialNext.length > 0) {
        
    }

}
