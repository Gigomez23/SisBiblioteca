package repository;

import entities.Categoria;

import java.util.List;

public interface ICategoria {
    Categoria guardar(Categoria categoria);
    Categoria buscarPorId(Long id);
    Categoria eliminar(Long id);
    Categoria actualizar(Categoria categoria);
    List<Categoria> listar();
}
