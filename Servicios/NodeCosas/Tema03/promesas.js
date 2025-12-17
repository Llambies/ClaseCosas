// Ejercicio 1

function mensajeAleatorio() {
  let tiempo = Math.random() * 2000 + 1000;
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(`El mensaje tardó ${tiempo} segundos en aparecer`);
    }, tiempo);
  });
}

mensajeAleatorio().then((mensaje) => {
  console.log(mensaje);
});

mensajeAleatorio().then((mensaje) => {
  console.log(mensaje);
});

mensajeAleatorio().then((mensaje) => {
  console.log(mensaje);
});

// Ejercicio 2

function validarPassword(password) {
  return new Promise((resolve, reject) => {
    if (password.length >= 8) {
      resolve("Contraseña valida");
    } else {
      reject("Contraseña debe tener al menos 8 caracteres");
    }
  });
}

validarPassword("12345678")
  .then((mensaje) => {
    console.log(mensaje);
  })
  .catch((mensaje) => {
    console.log(mensaje);
  });

validarPassword("1234567")
  .then((mensaje) => {
    console.log(mensaje);
  })
  .catch((mensaje) => {
    console.log(mensaje);
  });

// Ejercicio 3

const productos = [
  { nombre: "Teclado", precio: 25 },
  { nombre: "Ratón", precio: 15 },
  { nombre: "Monitor", precio: 120 },
];

function buscarProducto(nombre) {
  return new Promise((resolve, reject) => {
    const producto = productos.find((producto) => {
      return producto.nombre === nombre;
    });
    if (producto) {
      resolve(producto);
    } else {
      reject("Producto no encontrado");
    }
  });
}

buscarProducto("Teclado")
  .then((producto) => {
    console.log(producto);
  })
  .catch((mensaje) => {
    console.log(mensaje);
  });

buscarProducto("Mouse")
  .then((producto) => {
    console.log(producto);
  })
  .catch((mensaje) => {
    console.log(mensaje);
  });
