package repository;

import entities.Autor;
import java.util.List;

public interface IAutor {
    Autor guardar(Autor autor);
    Autor buscarPorId(Long id);
    Autor eliminar(Long id);
    Autor actualizar(Autor autor);
    List<Autor> listar();
}
