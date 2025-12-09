let datos = [
  { nombre: "Nache", telefono: "123", edad: 43 },
  { nombre: "Mache", telefono: "123", edad: 17 },
  { nombre: "Aache", telefono: "123", edad: 23 },
  { nombre: "Vsache", telefono: "123", edad: 13 },
];

let mayores = datos.filter(function (e) {
  return e.edad >= 18;
});

console.log(mayores);
let menores = datos.filter((e) => e.edad < 18);
console.log(menores);


