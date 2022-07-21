"use strict";
const express = require("express");
const api = express();
const https = require('https');

// api.get("/mech3d", (req, res) => {
//   res.download('mech.glb')
// })

// api.get("/astronaut", (req, res) => {
//   res.download('astronaut.glb')
// })

// api.listen(3000, err => {
//   if (err) {
//     console.log("there was a problem", err);
//     return;
//   }
//   console.log("listening on port 3000");
// });

const http = require('http');
const server = http.createServer(function(req, res) {
  if (req.url === '/'){
    res.write('Astronaut.glb')
    res.end();
  }
});

server.on('connection', (socket) => {
  console.log('New connection...');
})
server.listen(3000);

console.log('Listening on port 3000... ');
