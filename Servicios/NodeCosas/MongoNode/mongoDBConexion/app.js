const mongoose = require('mongoose');

// Motor de las promesas
mongoose.Promise = global.Promise;

// Conexión a la base de datos
mongoose.connect('mongodb://localhost:27017/contactos');

let contactoSchema = new mongoose.Schema({
    nombre: String,
    telefono: String,
    edad: Number
});

let Contacto = mongoose.model('contactos', contactoSchema);

let contactoACrear = new Contacto({
    nombre: 'Juan Perez',
    telefono: '1234567890',
    edad: 30
});

contactoACrear.save()
    .then(() => console.log('Contacto creado'))
    .catch((err) => console.error(' Error al crear el contacto:', err));

Contacto.findById('6945777a6f3a99204cf2d0f7')
    .then((contactos) => console.log(contactos))
    .catch((err) => console.error('Error al buscar los contactos:', err));

Contacto.findByIdAndUpdate('6945777a6f3a99204cf2d0f7', { $set: { edad: 31 } })
    .then((contactos) => console.log(contactos))
    .catch((err) => console.error('Error al buscar los contactos:', err));

/* Esta obsoleto, se debe usar deleteOne
    Contacto.remove({ nombre: 'Nacho' }).then(resultado => {
    console.log(resultado);
    }).catch(error => {
    console.log("ERROR:", error);
    }); 
*/

Contacto.deleteOne({ nombre: 'Juan Perez' })
    .then(() => console.log('Contacto eliminado'))
    .catch((err) => console.error('Error al eliminar el contacto:', err));
