const http = require('http')
const fs = require('fs')
const port = 3000

const server = http.createServer(function(req, res){
    res.writeHead(200, {'Content-Type': 'image/png'})
    fs.readFile('astronaut.glb', function(error, data){
        if (error){
            res.writeHead(404)
            res.write('Error: File Not Found')
        } else{
            res.write(data)
        }
        res.end()
    })
})

server.listen(port, function(error){
    if(error){
        console.log('Someting went wrong', error)
    } else {
        console.log('Server is listening on port ' + port)
    }
})