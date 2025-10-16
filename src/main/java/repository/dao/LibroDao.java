package repository.dao;

import entities.Libro;
import jakarta.persistence.EntityManager;
import repository.ILibro;

import java.util.List;

public class LibroDao implements ILibro {
    private final EntityManager em;

    public LibroDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Libro guardar(Libro libro) {
        em.getTransaction().begin();
        if (libro.getId() == null) {
            em.persist(libro);
        } else {
            libro = em.merge(libro);
        }
        em.getTransaction().commit();
        return libro;
    }

    @Override
    public Libro buscarPorId(Long id) {
        return em.find(Libro.class, id);
    }

    @Override
    public Libro eliminar(Long id) {
        Libro libro = buscarPorId(id);
        if (libro != null) {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        }
        return libro;
    }

    @Override
    public Libro actualizar(Libro libro) {
        if (libro.getId() != null) {
            em.getTransaction().begin();
            Libro actualizado = em.merge(libro);
            em.getTransaction().commit();
            return actualizado;
        }
        return null;
    }

    @Override
    public List<Libro> listar() {
        return em.createQuery("from Libro", Libro.class).getResultList();
    }
}
