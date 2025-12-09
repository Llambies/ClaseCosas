const PI = 3.141592;

function sumar(x1, x2) {
  return x1 + x2;
}

function restar(x1, x2) {
  return x1 - x2;
}

function dividir(x1, x2) {
  if (x2 == 0) {
    mostrarErrorDivision();
  } else {
    return x1 / x2;
  }
}

function mostrarErrorDivision() {
  console.log("No se peude dividir por 0");
}

exports.sumar = sumar;
exports.restar = restar;
exports.dividir = dividir;
exports.PI = PI;
