package utils;

import entities.Autor;
import entities.Libro;
import entities.Categoria;
import repository.dao.AutorDao;
import repository.dao.LibroDao;
import repository.dao.CategoriaDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class menuUtils {

    private menuUtils() {}

    // AUTOR
    public static void agregarAutorMenu(Scanner sc, AutorDao dao) {
        Autor autor = new Autor();
        System.out.print("Ingrese nombre del autor: ");
        autor.setNombre(sc.nextLine());
        System.out.print("Ingrese nacionalidad: ");
        autor.setNacionalidad(sc.nextLine());
        System.out.print("Ingrese fecha de nacimiento (YYYY-MM-DD): ");
        autor.setFechaNacimiento(LocalDate.parse(sc.nextLine()));
        dao.guardar(autor);
        System.out.println("Autor guardado correctamente.");
    }

    public static void buscarAutorMenu(Scanner sc, AutorDao dao) {
        listarAutoresMenu(dao);
        System.out.print("Ingrese ID del autor: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Autor autor = dao.buscarPorId(id);
        if (autor != null) System.out.println(autor);
        else System.out.println("Autor no encontrado.");
    }

    public static void actualizarAutorMenu(Scanner sc, AutorDao dao) {
        listarAutoresMenu(dao);
        System.out.print("Ingrese ID del autor a actualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Autor autor = dao.buscarPorId(id);
        if (autor != null) {
            System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty()) autor.setNombre(nombre);
            System.out.print("Nueva nacionalidad (dejar vacío para no cambiar): ");
            String nacionalidad = sc.nextLine();
            if (!nacionalidad.isEmpty()) autor.setNacionalidad(nacionalidad);
            System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD, dejar vacío para no cambiar): ");
            String fecha = sc.nextLine();
            if (!fecha.isEmpty()) autor.setFechaNacimiento(LocalDate.parse(fecha));
            dao.actualizar(autor);
            System.out.println("Autor actualizado correctamente.");
        } else {
            System.out.println("No existe autor con ese ID.");
        }
    }

    public static void eliminarAutorMenu(Scanner sc, AutorDao dao) {
        listarAutoresMenu(dao);
        System.out.print("Ingrese ID del autor a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        dao.eliminar(id);
        System.out.println("Autor eliminado.");
    }

    public static void listarAutoresMenu(AutorDao dao) {
        List<Autor> autores = dao.listar();
        System.out.println("\nListado de Autores:");
        for (Autor a : autores) System.out.println(a);
    }

    // CATEGORIA
    public static void agregarCategoriaMenu(Scanner sc, CategoriaDao dao) {
        Categoria categoria = new Categoria();
        System.out.print("Ingrese nombre de la categoría: ");
        categoria.setNombre(sc.nextLine());
        dao.guardar(categoria);
        System.out.println("Categoría guardada correctamente.");
    }

    public static void buscarCategoriaMenu(Scanner sc, CategoriaDao dao) {
        listarCategoriasMenu(dao);
        System.out.print("Ingrese ID de la categoría: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Categoria categoria = dao.buscarPorId(id);
        if (categoria != null) System.out.println(categoria);
        else System.out.println("Categoría no encontrada.");
    }

    public static void actualizarCategoriaMenu(Scanner sc, CategoriaDao dao) {
        listarCategoriasMenu(dao);
        System.out.print("Ingrese ID de la categoría a actualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Categoria categoria = dao.buscarPorId(id);
        if (categoria != null) {
            System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty()) categoria.setNombre(nombre);
            dao.actualizar(categoria);
            System.out.println("Categoría actualizada correctamente.");
        } else {
            System.out.println("No existe categoría con ese ID.");
        }
    }

    public static void eliminarCategoriaMenu(Scanner sc, CategoriaDao dao) {
        listarCategoriasMenu(dao);
        System.out.print("Ingrese ID de la categoría a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        dao.eliminar(id);
        System.out.println("Categoría eliminada.");
    }

    public static void listarCategoriasMenu(CategoriaDao dao) {
        List<Categoria> categorias = dao.listar();
        System.out.println("\nListado de Categorías:");
        for (Categoria c : categorias) System.out.println(c);
    }

    // LIBRO
    public static void agregarLibroMenu(Scanner sc, LibroDao libroDao, AutorDao autorDao, CategoriaDao categoriaDao) {
        Libro libro = new Libro();
        System.out.print("Ingrese título del libro: ");
        libro.setTitulo(sc.nextLine());
        System.out.print("Ingrese año de publicación (YYYY-MM-DD): ");
        libro.setAnioPublicacion(LocalDate.parse(sc.nextLine()));

        // Seleccionar autor
        listarAutoresMenu(autorDao);
        System.out.print("Ingrese ID del autor: ");
        Long autorId = sc.nextLong();
        sc.nextLine();
        Autor autor = autorDao.buscarPorId(autorId);
        libro.setAutor(autor);

        // Seleccionar categorías
        listarCategoriasMenu(categoriaDao);
        System.out.print("Ingrese IDs de categorías separadas por coma: ");
        String[] ids = sc.nextLine().split(",");
        List<Categoria> categorias = new ArrayList<>();
        for (String idStr : ids) {
            try {
                Long catId = Long.parseLong(idStr.trim());
                Categoria cat = categoriaDao.buscarPorId(catId);
                if (cat != null) categorias.add(cat);
            } catch (NumberFormatException ignored) {}
        }
        libro.setCategorias(categorias);

        libroDao.guardar(libro);
        System.out.println("Libro guardado correctamente.");
    }

    public static void buscarLibroMenu(Scanner sc, LibroDao dao) {
        listarLibrosMenu(dao);
        System.out.print("Ingrese ID del libro: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Libro libro = dao.buscarPorId(id);
        if (libro != null) System.out.println(libro);
        else System.out.println("Libro no encontrado.");
    }

    public static void actualizarLibroMenu(Scanner sc, LibroDao libroDao, AutorDao autorDao, CategoriaDao categoriaDao) {
        listarLibrosMenu(libroDao);
        System.out.print("Ingrese ID del libro a actualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Libro libro = libroDao.buscarPorId(id);
        if (libro != null) {
            System.out.print("Nuevo título (dejar vacío para no cambiar): ");
            String titulo = sc.nextLine();
            if (!titulo.isEmpty()) libro.setTitulo(titulo);

            System.out.print("Nuevo año de publicación (YYYY-MM-DD, dejar vacío para no cambiar): ");
            String anio = sc.nextLine();
            if (!anio.isEmpty()) libro.setAnioPublicacion(LocalDate.parse(anio));

            // Cambiar autor
            System.out.print("¿Cambiar autor? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                listarAutoresMenu(autorDao);
                System.out.print("Ingrese ID del nuevo autor: ");
                Long autorId = sc.nextLong();
                sc.nextLine();
                Autor autor = autorDao.buscarPorId(autorId);
                libro.setAutor(autor);
            }

            // Cambiar categorías
            System.out.print("¿Cambiar categorías? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                listarCategoriasMenu(categoriaDao);
                System.out.print("Ingrese IDs de categorías separadas por coma: ");
                String[] ids = sc.nextLine().split(",");
                List<Categoria> categorias = new ArrayList<>();
                for (String idStr : ids) {
                    try {
                        Long catId = Long.parseLong(idStr.trim());
                        Categoria cat = categoriaDao.buscarPorId(catId);
                        if (cat != null) categorias.add(cat);
                    } catch (NumberFormatException ignored) {}
                }
                libro.setCategorias(categorias);
            }

            libroDao.actualizar(libro);
            System.out.println("Libro actualizado correctamente.");
        } else {
            System.out.println("No existe libro con ese ID.");
        }
    }

    public static void eliminarLibroMenu(Scanner sc, LibroDao dao) {
        listarLibrosMenu(dao);
        System.out.print("Ingrese ID del libro a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        dao.eliminar(id);
        System.out.println("Libro eliminado.");
    }

    public static void listarLibrosMenu(LibroDao dao) {
        List<Libro> libros = dao.listar();
        System.out.println("\nListado de Libros:");
        for (Libro l : libros) System.out.println(l);
    }
}