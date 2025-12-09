let datos = [
  { nombre: "Nacho", telefono: "966112233", edad: 40 },
  { nombre: "Ana", telefono: "911223344", edad: 35 },
  { nombre: "Mario", telefono: "611998877", edad: 15 },
  { nombre: "Laura", telefono: "633663366", edad: 17 },
];

function nuevaPersona(persona) {
  console.log(persona.telefono != null);
  if (!(persona.telefono != null)) return;
  if (datos.filter((e) => e.telefono == persona.telefono).length > 0) return;
  datos.push(persona);
}

function borrarPersona(telefono) {
  datos = datos.filter((e) => e.telefono != telefono);
}

nuevaPersona({ nombre: "Juan", telefono: "965661564", edad: 60 });
nuevaPersona({ nombre: "Rodolfo", telefono: "910011001", edad: 20 });
nuevaPersona({ nombre: "Rodolfo2", telefono: "910011001", edad: 20 });
nuevaPersona({ nombre: "Rodolfo3", edad: 20 });
borrarPersona("965661564");

console.log(datos);
