// imports
const express = require('express');
const bodyParser = require('body-parser');
const ejs = require('ejs');
const jwt = require('jsonwebtoken');

const secreto = "pinga"

let generarToken = (login) => {
    return jwt.sign({login}, secreto, {expiresIn: '2 hours'});
}

// lista de usuarios
let usuarios = [
    {login:'nacho', password:'1234'},
    {login:'arturo', password:'5678'}
];

// crear el servidor
let app = express();

// configurar el motor de plantillas
app.set('view engine', 'ejs');
app.use('/public', express.static('./public'));

// parsear el body de las peticiones
app.use(bodyParser.json());
    
// rutas
app.get('/', (req, res) => {
    res.render('index');
});

app.get('/inicio', (req, res) => {
    res.render('inicio');
});

app.get('/login', (req, res) => {
    res.render('login');
});

// chekar login y generar un token
app.post('/login', (req, res) => {
    let login = req.body.login;
    let password = req.body.password;
    let usuario = usuarios.filter(u => {
        return u.login === login && u.password === password;
    });
    if (usuario.length === 1) {
        res.json({error: false, token: generarToken(login), status: 200});
    } else {
        res.json({error: true, message: 'Usuario o contraseña incorrectos', status: 401});
    }
});

// proteger una ruta
let protegerRuta = (req, res, next) => {
    let token = req.headers['authorization'];
    if (validarToken(token)) {
        next();
    } else {
        res.render('login');
    }
}

// validar un token
let validarToken = (token) => {
    try {
        let resultado = jwt.verify(token, secreto);
        return resultado;
    } catch (err) {
        return null;
    }
}

// ruta protegida con un token
app.get('/protegido', protegerRuta, (req, res) => {
    res.render('protegido');
});


// iniciar el servidor
app.listen(8080);