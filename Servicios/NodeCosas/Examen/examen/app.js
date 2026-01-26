const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const port = 8080;
const app = express();
app.use(bodyParser.json());


mongoose.connect('mongodb://localhost:27017/natacionGandia');

let nadadorSchema = new mongoose.Schema({
    codigo: Number,
    nombre: String,
    estilo: String
});

let Nadador = mongoose.model('nadadores', nadadorSchema);


app.get('/nadadores', (req, res) => {
    Nadador.find().then(nadadores => {
        res.json(nadadores);
    }).catch(err => {
        res.status(500).send(err);
    });
    });

app.post('/insertNadador', (req, res) => {
    const nadador = new Nadador({
        codigo: req.body.codigo,
        nombre: req.body.nombre,
        estilo: req.body.estilo
    });

    if (Nadador.findOne({ codigo: req.body.codigo })) {
        res.status(400).send('Nadador ya existe');
        return;
    }

    nadador.save().then(nadador => {
        res.json(nadador);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.put('/update', (req, res) => {
    Nadador.findOneAndUpdate({ codigo: req.body.codigo }, req.body).then(nadador => {
        res.json(nadador);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});

app.delete('/delete', (req, res) => {
    Nadador.findOneAndDelete({ codigo: req.body.codigo }).then(nadador => {
        res.json(nadador);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});


app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});