const express = require("express");
const app = express();
app.use(express.json());

const mongoose = require("mongoose");
mongoose.connect("mongodb://localhost:27017/parkineo", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const plazaSchema = new mongoose.Schema({
  numero: Number,
  planta: Number,
  tipo: String,
});

const Plaza = mongoose.model("Plaza", plazaSchema);

app.get("/", (req, res) => {
  res.send("Hello World");
});

app.get("/plazas", (req, res) => {
  Plaza.find().then(plazas => {
    res.json(plazas);
  });
});

app.listen(3000, () => {
  console.log("Server is running on port 3000");
});