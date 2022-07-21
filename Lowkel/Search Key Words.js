const fs = require('fs');

//getCollection from mongodb
async function getMongoCollection(dbStr, collectionStr){
    var MongoClient = require('mongodb').MongoClient;

    let collection = await new Promise((resolve, reject) =>{
        MongoClient.connect(url, { useUnifiedTopology: true }, function (err, db) {
            if (err) throw err;
            var dataBase = db.db(dbStr);
            dataBase.collection(collectionStr).find({ /*status: 'created'*/ }).toArray(function (err, coll) {
                if (err) throw err;
                resolve(coll)
            })
        });
    })
    return collection
}

let filePath = '/users/Eddie/Desktop/words_alpha.txt'
function readTextFileAndSaveToMongo(filePath){
    fs.readFile(filePath, 'utf8' , async (err, data) => {
        if (err) {
            console.error(err);
            return;
        }
        data = data.match(/\b(\w+)\b/g)
        console.log(data)
        await data.forEach(async (word)=>{
            const doc = new dictionaryWord();
            doc.word = word;
            await doc.save(err=>{if(err)return;})
        })
        console.log('done')
    })
}

async function inserDictionaryWord(word){
    const doc = new DictionaryWord();
    doc.word = word;
    await doc.save(err=>{if(err)return;})
    console.log(word + ' inserted')
}



async function insertStoreNameToDisctionary(){
    let categories = await Store.find()
    categories.forEach(cat=>{
        words = cat.name.match(/\b(\w+)\b/g)
        words.forEach(async word=> {
            const doc = new DictionaryWord();
            doc.word = word;
            await doc.save(err=>{if(err)return;})
            console.log(word + ' inserted')
        })
    })
}
async function logTime(fn){
    console.time('ex')
    await fn('this ')
    console.timeEnd('ex')
}

a()
async function a(){
    let prods = await Product.aggregate([{$limit: 40000}]);
    prods.forEach(prod=>{
        prod.name.match(/\b(\w+)\b/g).forEach(word=>{
            const doc = new dictionaryWord();
            doc.word = word.toLowerCase();
            doc.save(err=>{if(err)return;})
        })
    })
}

async function updateSearchKeywords(){
    console.time('a')
    let products = await Product.aggregate([
        {$limit: 300000},
        {$project: {categories: 1, storeId: 1, name: 1, searchKeywords: 1}},
        {$lookup: {from: Category.collection.name, localField: 'categories', foreignField: '_id', as: 'categories'}},
        {$lookup: {from: Store.collection.name, localField: 'storeId', foreignField: '_id', as: 'stores'}},
        {$lookup: {from: StoreCategory.collection.name, localField: 'stores.categories', foreignField: '_id', as: 'storeCategories'}},
        {$lookup: {from: StoreCategory.collection.name, localField: 'storeCategories.parentCatId', foreignField: '_id', as: 'storeParentCategories'}},
        // {$addFields: {searchKeywords: {type: [ {$toLower: "$name"}, "$categories.name", "$storeCategories.name", "$storeParentCategories.name"],default: [],select: false}}},
    ])

    console.log('print')
    for(let i = 0; i < products.length; i++){
        products[i].searchKeywords = {
            type: [products[i].name.toLowerCase(), products[i].stores[0].name.toLowerCase()],
            default: [],
            select: false
        }
        for(let j = 0; j < products[i].categories.length; j++){
            products[i].searchKeywords.type.push(products[i].categories[j].name.toLowerCase())
        }
        for(let j = 0; j < products[i].storeCategories.length; j++){
            products[i].searchKeywords.type.push(products[i].storeCategories[j].name.toLowerCase())
        }
        for(let j = 0; j < products[i].storeParentCategories.length; j++){
            products[i].searchKeywords.type.push(products[i].storeParentCategories[j].name.toLowerCase())
        }
        await Product.updateOne({_id: products[i]._id}, {$set: {searchKeywords: products[i].searchKeywords}})
        if (i % 100 == 0){
            console.log(i)
        }
    }

    console.timeEnd('a');
}