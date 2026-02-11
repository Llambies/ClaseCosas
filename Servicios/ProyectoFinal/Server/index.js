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
  tipo: { type: String, required: true }, // coche, moto, etc.
});

const estacionamientoSchema = new mongoose.Schema({
  plazaId: { type: mongoose.Schema.Types.ObjectId, ref: "Plaza", required: true },
  tipoVehiculo: { type: String, required: true },
  entrada: { type: Date, default: Date.now },
  salida: { type: Date },
});

const Plaza = mongoose.model("Plaza", plazaSchema);
const Estacionamiento = mongoose.model("Estacionamiento", estacionamientoSchema);

const ADMIN_PIN = process.env.ADMIN_PIN || "1234";
const TARIFAS = { normal: 0.05, electrico: 0.04, discapacitado: 0.03 }; // €/minuto

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
    endpoints: [
      "POST /auth/admin",
      "GET /plazas",
      "GET /plazas/disponibles",
      "POST /plazas",
      "PUT /plazas/:id",
      "DELETE /plazas/:id",
      "POST /estacionamientos",
      "GET /estacionamientos/:id",
      "PUT /estacionamientos/:id/salir",
      "GET /tarifas",
    ],
  });
});

// POST /auth/admin - Verificar PIN admin
app.post("/auth/admin", (req, res) => {
  const { pin } = req.body || {};
  if (String(pin) === String(ADMIN_PIN)) {
    return respOk(res, { ok: true });
  }
  respError(res, 401, "PIN incorrecto");
});

// GET /tarifas - Obtener tarifas por tipo
app.get("/tarifas", (req, res) => {
  respOk(res, TARIFAS);
});

// GET /plazas - Listar todas
app.get("/plazas", (req, res) => {
  Plaza.find()
    .then((plazas) => respOk(res, plazas))
    .catch((err) => respError(res, 500, "Error al listar: " + err.message));
});

// GET /plazas/disponibles?tipo=X - Plazas libres por tipo
app.get("/plazas/disponibles", (req, res) => {
  const tipo = (req.query.tipo || "").trim().toLowerCase();
  if (!tipo) return respError(res, 400, "Falta parámetro: tipo (normal, electrico, discapacitado)");
  Estacionamiento.find({ salida: null })
    .select("plazaId")
    .then((activos) => {
      const idsOcupadas = activos.map((a) => a.plazaId);
      return Plaza.find({ tipo: new RegExp("^" + tipo + "$", "i"), _id: { $nin: idsOcupadas } });
    })
    .then((plazas) => respOk(res, { count: plazas.length, plazas }))
    .catch((err) => respError(res, 500, "Error: " + err.message));
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

// POST /estacionamientos - Iniciar estacionamiento (asignar plaza automática)
app.post("/estacionamientos", (req, res) => {
  const { tipoVehiculo } = req.body || {};
  const tipo = String(tipoVehiculo || "").trim().toLowerCase();
  if (!tipo) return respError(res, 400, "Falta tipoVehiculo");
  Estacionamiento.find({ salida: null })
    .select("plazaId")
    .then((activos) => {
      const idsOcupadas = activos.map((a) => a.plazaId);
      return Plaza.findOne({ tipo: new RegExp("^" + tipo + "$", "i"), _id: { $nin: idsOcupadas } });
    })
    .then((plaza) => {
      if (!plaza) {
        respError(res, 409, "No hay plazas disponibles para " + tipoVehiculo);
        return null;
      }
      const est = new Estacionamiento({ plazaId: plaza._id, tipoVehiculo: tipo });
      return est.save().then((saved) => ({ est: saved, plaza }));
    })
    .then((result) => {
      if (!result) return;
      const { est, plaza } = result;
      respOk(res, {
        id: est._id.toString(),
        plazaId: plaza._id,
        numero: plaza.numero,
        planta: plaza.planta,
        tipo: plaza.tipo,
        entrada: est.entrada,
      });
    })
    .catch((err) => {
      if (!res.headersSent) respError(res, 500, "Error: " + err.message);
    });
});

// GET /estacionamientos/activo - Obtener estacionamiento activo por plaza (para contador)
// En cliente se usa el id del estacionamiento guardado al aparcar
app.get("/estacionamientos/:id", (req, res) => {
  const { id } = req.params;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return respError(res, 400, "ID inválido");
  }
  Estacionamiento.findById(id)
    .populate("plazaId")
    .then((doc) => {
      if (!doc) return respError(res, 404, "Estacionamiento no encontrado");
      if (doc.salida) return respError(res, 400, "Estacionamiento ya finalizado");
      const tarifa = TARIFAS[doc.tipoVehiculo] || TARIFAS.normal;
      const minutos = (Date.now() - new Date(doc.entrada).getTime()) / 60000;
      respOk(res, {
        id: doc._id,
        plaza: doc.plazaId,
        entrada: doc.entrada,
        minutos: Math.ceil(minutos),
        precioMinuto: tarifa,
        total: Math.ceil(minutos) * tarifa,
      });
    })
    .catch((err) => respError(res, 500, "Error: " + err.message));
});

// PUT /estacionamientos/:id/salir - Finalizar estacionamiento
app.put("/estacionamientos/:id/salir", (req, res) => {
  const { id } = req.params;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return respError(res, 400, "ID inválido");
  }
  Estacionamiento.findOneAndUpdate({ _id: id, salida: null }, { salida: new Date() }, { new: true })
    .populate("plazaId")
    .then((doc) => {
      if (!doc) return respError(res, 404, "Estacionamiento no encontrado o ya finalizado");
      const tarifa = TARIFAS[doc.tipoVehiculo] || TARIFAS.normal;
      const minutos = (new Date(doc.salida) - new Date(doc.entrada)) / 60000;
      respOk(res, {
        id: doc._id,
        minutos: Math.ceil(minutos),
        total: Math.ceil(minutos) * tarifa,
        salida: doc.salida,
      });
    })
    .catch((err) => respError(res, 500, "Error: " + err.message));
});

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});
