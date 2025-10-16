package repository.dao;

import entities.Autor;
import jakarta.persistence.EntityManager;
import repository.IAutor;

import java.util.List;

public class AutorDao implements IAutor {

    private final EntityManager em;

    public AutorDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Autor guardar(Autor autor) {
        em.getTransaction().begin();
        if (autor.getId() == null) {
            em.persist(autor);
        } else {
            autor = em.merge(autor);
        }
        em.getTransaction().commit();
        return autor;
    }

    @Override
    public Autor buscarPorId(Long id) {
        return em.find(Autor.class, id);
    }

    @Override
    public Autor eliminar(Long id) {
        Autor autor = buscarPorId(id);
        if (autor != null) {
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        }
        return autor;
    }

    @Override
    public Autor actualizar(Autor autor) {
        if (autor.getId() != null) {
            em.getTransaction().begin();
            Autor actualizado = em.merge(autor);
            em.getTransaction().commit();
            return actualizado;
        }
        return null;
    }

    @Override
    public List<Autor> listar() {
        return em.createQuery("from Autor", Autor.class).getResultList();
    }
}
