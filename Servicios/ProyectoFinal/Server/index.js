const express = require("express");
const app = express();
app.use(express.json());

const mongoose = require("mongoose");

mongoose
  .connect("mongodb://localhost:27017/parkineo", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => console.log("Conectado a MongoDB: parkineo"))
  .catch((err) => console.error("Error conexión MongoDB:", err));

const plazaSchema = new mongoose.Schema({
  numero: { type: Number, required: true },
  planta: { type: Number, required: true },
  tipo: { type: String, required: true },
});

const Plaza = mongoose.model("Plaza", plazaSchema);

// Respuestas JSON consistentes
function respOk(res, data) {
  res.status(200).json(data);
}

function respError(res, status, mensaje) {
  res.status(status).json({ error: mensaje });
}

// GET / - Info API
app.get("/", (req, res) => {
  respOk(res, {
    api: "Parkineo API",
    version: "1.0",
    endpoints: ["GET /plazas", "POST /plazas", "PUT /plazas/:id", "DELETE /plazas/:id"],
  });
});

// GET /plazas - Listar todas
app.get("/plazas", (req, res) => {
  Plaza.find()
    .then((plazas) => respOk(res, plazas))
    .catch((err) => respError(res, 500, "Error al listar: " + err.message));
});

// POST /plazas - Insertar
app.post("/plazas", (req, res) => {
  const { numero, planta, tipo } = req.body;
  if (numero === undefined || planta === undefined || !tipo) {
    return respError(res, 400, "Faltan campos: numero, planta, tipo");
  }
  const p = new Plaza({ numero: Number(numero), planta: Number(planta), tipo: String(tipo).trim() });
  p.save()
    .then((saved) => respOk(res, saved))
    .catch((err) => respError(res, 500, "Error al insertar: " + err.message));
});

// PUT /plazas/:id - Actualizar por _id
app.put("/plazas/:id", (req, res) => {
  const { id } = req.params;
  const { numero, planta, tipo } = req.body;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return respError(res, 400, "ID inválido");
  }
  Plaza.findByIdAndUpdate(
    id,
    { numero: Number(numero), planta: Number(planta), tipo: String(tipo || "").trim() },
    { new: true, runValidators: true }
  )
    .then((doc) => {
      if (!doc) return respError(res, 404, "Plaza no encontrada");
      respOk(res, doc);
    })
    .catch((err) => respError(res, 500, "Error al actualizar: " + err.message));
});

// DELETE /plazas/:id - Borrar por _id
app.delete("/plazas/:id", (req, res) => {
  const { id } = req.params;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return respError(res, 400, "ID inválido");
  }
  Plaza.findByIdAndDelete(id)
    .then((doc) => {
      if (!doc) return respError(res, 404, "Plaza no encontrada");
      respOk(res, { deleted: true, id });
    })
    .catch((err) => respError(res, 500, "Error al borrar: " + err.message));
});

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});
