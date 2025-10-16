package utils;

import com.sisbiblio.entities.Autor;
import com.sisbiblio.entities.Categoria;
import com.sisbiblio.entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private final EntityManagerFactory emf;
    private final Scanner in = new Scanner(System.in);

    public Menu(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void loop() {
        int op;
        do {
            System.out.println("------ SisBiblioteca ------");
            System.out.println("1. Cargar datos de demo");
            System.out.println("2. Listar libros (autor y categorías)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String s = in.nextLine().trim();
            op = s.isEmpty() ? -1 : parseIntSafe(s);

            switch (op) {
                case 1 -> seedDemo();
                case 2 -> listBooks();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { return -1; }
    }

    /** Inserta 2 autores, 3 libros y 3 categorías con relaciones correctas. */
    private void seedDemo() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Categorías
            Categoria ficcion = new Categoria("Ficción");
            Categoria ciencia = new Categoria("Ciencia");
            Categoria romance = new Categoria("Romance");
            em.persist(ficcion);
            em.persist(ciencia);
            em.persist(romance);

            // Autores (usa fechas pasadas si tienes @Past en la entidad)
            Autor a1 = new Autor("Gabriel García Márquez", "Colombiana", LocalDate.of(1927, 3, 6));
            Autor a2 = new Autor("Angelo Soza", "Estadounidense", LocalDate.of(1990, 1, 2));

            // Libros
            Libro l1 = new Libro("Cien años estudiando Java", 1967);
            Libro l2 = new Libro("Python para POO (Mejor)", 2018);
            Libro l3 = new Libro("C# es mejor que Java", 2020);

            //agreguen helper de autor categoria y libro
            a1.addLibro(l1);   // l1.setAutor(a1)
            a2.addLibro(l2);
            a2.addLibro(l3);

            // Relaciones Libro <-> Categoria (ManyToMany)
            l1.addCategoria(ficcion);
            l2.addCategoria(ficcion);
            l2.addCategoria(ciencia);
            l3.addCategoria(ciencia);
            l3.addCategoria(romance);

            // Persistir
            // Si tu @OneToMany en Autor TIENE cascade = CascadeType.ALL, basta con persistir autores.
            // Si NO lo tiene, descomenta también los persist de libros.
            em.persist(a1);
            em.persist(a2);
            // em.persist(l1); em.persist(l2); em.persist(l3); // <-- solo si NO tienes cascade en Autor->libros

            em.getTransaction().commit();
            System.out.println("✅ Datos de demo insertados correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error insertando datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /** Consulta: lista libros con su autor y categorías (evita N+1 con JOIN FETCH). */
    private void listBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Libro> libros = em.createQuery(
                    "SELECT DISTINCT b FROM Libro b " +
                            "JOIN FETCH b.autor " +
                            "LEFT JOIN FETCH b.categorias",
                    Libro.class
            ).getResultList();

            if (libros.isEmpty()) {
                System.out.println("No hay libros. Carga primero los datos de demo (opción 1).");
                return;
            }

            System.out.println("\n--- Libros (Autor y Categorías) ---");
            for (Libro b : libros) {
                String cats = b.getCategorias().stream()
                        .map(Categoria::getNombre)
                        .sorted()
                        .collect(Collectors.joining(", "));
                System.out.printf("- %s (%d) — Autor: %s — Categorías: [%s]%n",
                        b.getTitulo(), b.getAnioPublicacion(), b.getAutor().getNombre(), cats);
            }
        } finally {
            em.close();
        }
    }
}
