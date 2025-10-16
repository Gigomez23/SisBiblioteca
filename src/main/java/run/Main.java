package run;
import config.JPAUtil;
import jakarta.persistence.EntityManager;
import utils.Menu;
import utils.menuUtils;
import repository.dao.AutorDao;
import repository.dao.LibroDao;
import repository.dao.CategoriaDao;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);

        AutorDao autorDao = new AutorDao(em);
        LibroDao libroDao = new LibroDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);


        App(sc, autorDao, libroDao, categoriaDao);
    }

    public static void App(Scanner sc, AutorDao autorDao, LibroDao libroDao, CategoriaDao categoriaDao) {
        Menu menuPrincipal = new Menu("Menú Principal");
        Menu menuAutores = new Menu("Menú de Autores");
        Menu menuLibros = new Menu("Menú de Libros");
        Menu menuCategorias = new Menu("Menú de Categorías");

        menuAutores.agregarItem("Agregar Autor", () -> menuUtils.agregarAutorMenu(sc, autorDao));
        menuAutores.agregarItem("Buscar Autor", () -> menuUtils.buscarAutorMenu(sc, autorDao));
        menuAutores.agregarItem("Actualizar Autor", () -> menuUtils.actualizarAutorMenu(sc, autorDao));
        menuAutores.agregarItem("Eliminar Autor", () -> menuUtils.eliminarAutorMenu(sc, autorDao));
        menuAutores.agregarItem("Listar Auotres", () -> menuUtils.listarAutoresMenu(autorDao));

        menuLibros.agregarItem("Agregar Libro", () -> menuUtils.agregarLibroMenu(sc, libroDao, autorDao, categoriaDao));
        menuLibros.agregarItem("Buscar Libro", () -> menuUtils.buscarLibroMenu(sc, libroDao));
        menuLibros.agregarItem("Actualizar Libro", () -> menuUtils.actualizarLibroMenu(sc, libroDao, autorDao, categoriaDao));
        menuLibros.agregarItem("Eliminar Libro", () -> menuUtils.eliminarLibroMenu(sc, libroDao));
        menuLibros.agregarItem("Listar  Libros", () -> menuUtils.listarLibrosMenu(libroDao));

        menuCategorias.agregarItem("Agregar Categoria", () -> menuUtils.agregarCategoriaMenu(sc, categoriaDao));
        menuCategorias.agregarItem("Buscar Categoria", () -> menuUtils.buscarCategoriaMenu(sc, categoriaDao));
        menuCategorias.agregarItem("Actualizar Categoria", () -> menuUtils.actualizarCategoriaMenu(sc, categoriaDao));
        menuCategorias.agregarItem("Eliminar Categoria", () -> menuUtils.eliminarCategoriaMenu(sc, categoriaDao));
        menuCategorias.agregarItem("Listar Categorias", () -> menuUtils.listarCategoriasMenu(categoriaDao));

        menuPrincipal.agregarItem("Menú de Autores", menuAutores::mostrar);
        menuPrincipal.agregarItem("Menú de Libros", menuLibros::mostrar);
        menuPrincipal.agregarItem("Menú de Categorias", menuCategorias::mostrar);


        boolean continuar = true;
        while (continuar) {
            menuPrincipal.mostrar();
            System.out.print("¿Desea continuar? (s/n): ");
            String resp = sc.nextLine();
            if (resp.equalsIgnoreCase("n")) {
                continuar = false;
                System.out.println("Saliendo del sistema...");
            }
        }
    }
}
