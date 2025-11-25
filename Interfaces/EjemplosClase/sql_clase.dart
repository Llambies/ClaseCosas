void main() {
   const List transacciones = [
    { "id": 4534, "fecha": "2025–01–08", "producto": 112, "precio": 21, "cantidad": 2, "IVA": 21, "rechazada": false },
    { "id": 4535, "fecha": "2025–01–08", "producto": 232, "precio": 32, "cantidad": 3, "IVA": 10, "rechazada": false },
    { "id": 4536, "fecha": "2025–01–08", "producto": 554, "precio": 7, "cantidad": 100, "IVA": 10, "rechazada": true },
    { "id": 4537, "fecha": "2025–01–08", "producto": 433, "precio": 21, "cantidad": 2, "IVA": 21, "rechazada": false },
    { "id": 4538, "fecha": "2025–01–08", "producto": 112, "precio": 21, "cantidad": 4, "IVA": 21, "rechazada": false },
  ];

  final transaccionesActivas = noRechazadas(transacciones);
  print(transaccionesActivas);

final valoracion = listaResumen(transaccionesActivas);
  print(valoracion);

final total = totalContar(transaccionesActivas);
  print(total);
}

// SELECT * FROM transacciones WHERE NOT rechazada

// forEach, map, where, reduce, fold, every, any

List noRechazadas(List lista){
  return lista.where((e) => !e['rechazada']).toList();
}

// SELECT producto as productoId, precio*cantidad*IVA/100 as cantidadTotal FROM transactionesActivas
List listaResumen(List lista) {
  return lista.map((e) {
    return {
      'productoId': e['producto'],
      'cantidadTotal': e['precio'] * e['cantidad'] * (e['IVA']/100 + 1) ,
    };
  }).toList();
}

// SELECT SUM(precio*cantidad*(IVA/100 + 1)) as 'total', count(1) as 'contador' FROM transaccionesActivas

Map totalContar(List lista) {
  return lista.fold({}, (acc, e) {
    acc['total'] ??= 0;
    acc['contador'] ??= 0;

    acc['total'] += e['precio'] * e['cantidad'] * (e['IVA']/100 + 1);
    acc['contador']++;
    return acc;
  });
}

// SELECT 
// SUM(precio*cantidad*(IVA/100 + 1)) 
// FROM transactiones
// WHERE NOT rechazadas
double sumaTotal2(List datos) {
  return datos
            .where((e) => !e['rechazada'])
            .map((e) => e['precio'] * e['cantidad'] * (e['IVA']/100 + 1))
            .fold(0, (acc, e) => acc + e);
}