package repository;


import entities.Libro;

import java.util.List;

public interface ILibro {
    Libro guardar(Libro libro);
    Libro buscarPorId(Long id);
    Libro eliminar(Long id);
    Libro actualizar(Libro libro);
    List<Libro> listar();
}
