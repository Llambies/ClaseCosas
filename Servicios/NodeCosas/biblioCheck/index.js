const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const port = 8080;
const app = express();
app.use(bodyParser.json());


mongoose.connect('mongodb://localhost:27017/biblioCheck');

let libroSchema = new mongoose.Schema({
    codigo: String,
    titulo: String,
    autor: String
});

let Libro = mongoose.model('librosMolone', libroSchema);

app.get('/', (req, res) => {
    res.send(`
    <html>
      <body>
        <h1>Hello World</h1>
        <p>Welcome to the biblioCheck</p>
      </body>
    </html>
    `);
});

app.get('/libros', (req, res) => {
    Libro.find().then(libros => {
        res.json(libros);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.post('/libros', (req, res) => {

    console.log(req.body);

    let libro = new Libro({
        codigo: req.body.codigo,
        titulo: req.body.titulo,
        autor: req.body.autor
    });
    libro.save().then(libro => {
        res.json(libro);
    }).catch(err => {
        res.status(500).send(err);
    });
});

app.put('/libros/:codigo', (req, res) => {
    Libro.findOneAndUpdate({ codigo: req.params.codigo }, req.body).then(libro => {
        res.json(libro);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});

app.delete('/libros/:codigo', (req, res) => {
    Libro.findOneAndDelete({ codigo: req.params.codigo }).then(libro => {
        res.json(libro);
    }).catch(err => {
        res.status(500).send(err.message);
    });
});


app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});