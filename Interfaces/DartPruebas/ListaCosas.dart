List lista = [
  {'nombre': 'Producto 1', 'precio': 10, 'cantidad': 2, 'iva': 21},
  {'nombre': 'Producto 2', 'precio': 20, 'cantidad': 3, 'iva': 10},
  {'nombre': 'Producto 3', 'precio': 30, 'cantidad': 4, 'iva': 0},
];

List precioTotalesActivas(List lista) {
  return lista.map((e) {
    return {
      'productoId': e['nombre'],
      'cantidadTotal': e['precio'] * (1 + e['iva'] / 100) * e['cantidad'],
    };
  }).toList();
}

void main() {
  print(precioTotalesActivas(lista).toString().replaceAll('},', '},\n'));
  print(sumaTotal(lista));
}

int sumaTotal(List lista) {
  return lista.fold<int>(0, (a, b) {
    return a + b['cantidadTotal'] as int;
  });
}