List lista = [
  {
    'nombre': 'Producto 1',
    'precio': 10,
    'cantidad': 2,
    'iva': 21,
    'rechazada': false,
  },
  {
    'nombre': 'Producto 2',
    'precio': 20,
    'cantidad': 3,
    'iva': 10,
    'rechazada': false,
  },
  {
    'nombre': 'Producto 3',
    'precio': 30,
    'cantidad': 4,
    'iva': 0,
    'rechazada': false,
  },
  {
    'nombre': 'Producto 4',
    'precio': 40,
    'cantidad': 5,
    'iva': 16,
    'rechazada': false,
  },
  {
    'nombre': 'Producto 5',
    'precio': 50,
    'cantidad': 6,
    'iva': 16,
    'rechazada': true,
  },
  {
    'nombre': 'Producto 6',
    'precio': 60,
    'cantidad': 7,
    'iva': 16,
    'rechazada': true,
  },
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
  // print(precioTotalesActivas(lista).toString().replaceAll('},', '},\n'));
  print(sumarTransaccionesNoRechazadas(lista));
}

Map sumaTotal(List lista) {
  return lista.fold<Map>({}, (a, b) {
    return {
      'total': a['total'] != null
          ? a['total'] + b['cantidadTotal']
          : b['cantidadTotal'],
      'contador': a['contador'] != null ? a['contador'] + 1 : 1,
    };
  });
}

double sumarTransaccionesNoRechazadas(List lista) {
  return lista
      .where((e) => e['rechazada'] == false)
      .map((e) => e['precio'] * (1 + e['iva'] / 100) * e['cantidad'])
      .fold(0.0, (a, b) => a + b);
}

const datos = '''
fecha;tmin;tmax;
;;;
;
  2025/10/28;20.5;32;
2025/10/28;10.5;22;
2025/10/28;20.5;22;
2025/10/28;20.5;22;

2025/10/28;20.5;22;
''';

List<String> cosas({required String datos, String separador = '\n'}) {
  return datos
      .split(separador)
      .map((e) => e.trim())
      .where((e) => e.isNotEmpty)
      .toList();
}

List<String> cosas2({required List datos, String separador = ';'}) {
  return datos.removeAt(0).split(separador).map((e) => e.trim()).toList();
}
