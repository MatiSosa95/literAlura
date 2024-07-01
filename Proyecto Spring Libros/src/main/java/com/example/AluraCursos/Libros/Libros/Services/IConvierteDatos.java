package com.example.AluraCursos.Libros.Libros.Services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
