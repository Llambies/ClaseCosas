const express = require('express');
const http = require('http');
const socketio = require('socket.io');

let app = express();
let server = http.createServer(app);
let io = socketio(server);

app.set('view engine', 'ejs');
app.use(express.static('./public'));

app.get('/', (req, res) => {
    res.render('index');
});

io.on('connection', (socket) => {
    console.log('Un cliente se ha conectado');
    socket.emit('conectado'); 
    socket.on('enviar', (data) => {
        io.emit('difundir', data);
    });

});


server.listen(8080, '0.0.0.0', () => {
    console.log('Servidor corriendo en http://0.0.0.0:8080');
});