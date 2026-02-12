const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const port = 8080;
const app = express();
app.use(bodyParser.json());


mongoose.connect('mongodb://localhost:27017/contactos');

let contactosSchema = new mongoose.Schema({
    nombre: String,
    telefono: String,
    edad: Number
});

let Contacto = mongoose.model('Contacto', contactosSchema);

app.get('/', (req, res) => {
    res.send(`
    <html>
      <body>
        <h1>Hello World</h1>
        <p>Welcome to the home page</p>
        <a href="/contactos">Contactos</a>
      </body>
    </html>
    `);
});

app.get('/contactos', (req, res) => {
    Contacto.find().then(contactos => {
        res.json(contactos);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.post('/contactos', (req, res) => {

    console.log(req.body);

    let contacto = new Contacto({
        nombre: req.body.nombre,
        telefono: req.body.telefono,
        edad: req.body.edad
    });
    contacto.save().then(contacto => {
        res.json(contacto);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.put('/contactos/:nombre', (req, res) => {
    Contacto.findOneAndUpdate({ nombre: req.params.nombre }, req.body).then(contacto => {
        res.json(contacto);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});

app.delete('/contactos/:nombre', (req, res) => {
    Contacto.findOneAndDelete({ nombre: req.params.nombre }).then(contacto => {
        res.json(contacto);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});


app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});