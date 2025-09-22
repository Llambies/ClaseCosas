<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Hello World MVC</title>
</head>
<body>
    <h1>¡Hola, ${model.nombre}!</h1>
    <p>Hora: ${model.fechaHora}</p>
    <p><a href="?nombre=Usuario">Cambiar nombre</a></p>
    <p><a href="/">Inicio</a></p>
</body>
</html>
