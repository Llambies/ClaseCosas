const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const port = 8080;
const app = express();
app.use(bodyParser.json());


mongoose.connect('mongodb://localhost:27017/deportistas');

let deportistaSchema = new mongoose.Schema({
    codigo: String,
    nombre: String,
    deporte: String
});

let Deportista = mongoose.model('deportistas', deportistaSchema);


app.get('/deportistas', (req, res) => {
    Deportista.find().then(deportistas => {
        res.json(deportistas);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.post('/insertDeportista', (req, res) => {
    const deportista = new Deportista({
        codigo: req.body.codigo,
        nombre: req.body.nombre,
        deporte: req.body.deporte
    });
    deportista.save().then(deportista => {
        res.json(deportista);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.put('/update/:codigo', (req, res) => {
    Deportista.findOneAndUpdate({ codigo: req.params.codigo }, req.body).then(deportista => {
        res.json(deportista);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});

app.delete('/delete/:codigo', (req, res) => {
    Deportista.findOneAndDelete({ codigo: req.params.codigo }).then(deportista => {
        res.json(deportista);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});


app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});