package repository.dao;

import entities.Categoria;
import jakarta.persistence.EntityManager;
import repository.ICategoria;

import java.util.List;


public class CategoriaDao implements ICategoria {
    private final EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        em.getTransaction().begin();
        if (categoria.getId() == null) {
            em.persist(categoria);
        } else {
            categoria = em.merge(categoria);
        }
        em.getTransaction().commit();
        return categoria;
    }

    @Override
    public Categoria buscarPorId(Long id) {
        return em.find(Categoria.class, id);
    }

    @Override
    public Categoria eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        if (categoria != null) {
            em.getTransaction().begin();
            em.remove(categoria);
            em.getTransaction().commit();
        }
        return categoria;
    }

    @Override
    public Categoria actualizar(Categoria categoria) {
        if (categoria.getId() != null) {
            em.getTransaction().begin();
            Categoria actualizado = em.merge(categoria);
            em.getTransaction().commit();
            return actualizado;
        }
        return null;
    }

    @Override
    public List<Categoria> listar() {
        return em.createQuery("from Categoria", Categoria.class).getResultList();
    }
}
