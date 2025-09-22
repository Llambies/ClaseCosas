package com.example.servlet;

import com.example.model.HelloWorldModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador Hello World para Payara Server
 * Implementa patrón MVC: recibe petición, crea modelo, pasa a vista
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/hello", "/hola"})
public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Procesar parámetros de la petición
        String nombre = request.getParameter("nombre");
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Mundo";
        }

        // Crear el modelo con los datos
        HelloWorldModel model = new HelloWorldModel(nombre);

        // Pasar el modelo a la vista
        request.setAttribute("model", model);

        // Forward a la vista JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/hello.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir POST requests a GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "HelloWorldServlet - Controlador MVC para Payara Server";
    }
}
