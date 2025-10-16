package utils;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public String titulo;
    public ArrayList<ItemMenu> items;
    Scanner sc = new Scanner(System.in);

    public Menu() {
        this.items = new ArrayList<>();
    }

    public Menu(String titulo) {
        this.titulo = titulo;
        this.items = new ArrayList<>();
    }

    public void agregarItem(String descripcion, Runnable accion) {
        this.items.add(new ItemMenu(descripcion, accion));
    }

    public void mostrar() {
        System.out.println("=== " + this.titulo + " ===");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getDescripcion());
        }
        System.out.println((items.size() + 1) + ". Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = sc.nextInt();
        if (opcion > 0 && opcion <= items.size()) {
            items.get(opcion - 1).getAccion().run();
        } else if (opcion == items.size() + 1) {
            System.out.println("Saliendo...");
        } else {
            System.out.println("Opción inválida.");
        }
    }
}
