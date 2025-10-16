package utils;

import entities.Empleado;
import entities.Cargo;
import repository.dao.EmpleadoDao;
import repository.dao.CargoDao;

import java.util.List;
import java.util.Scanner;

public final class menuUtils {

    private menuUtils() {

    }

    public static void agregarCargoMenu(Scanner sc, CargoDao dao) {
        Cargo cargo = new Cargo();
        System.out.print("Ingrese nombre del cargo: ");
        cargo.setNombre(sc.nextLine());
        dao.guardar(cargo);
        System.out.println("Cargo guardado correctamente.");
    }

    public static void buscarCargoMenu(Scanner sc, CargoDao dao) {
        listarCargosMenu(sc, dao);
        System.out.print("Ingrese ID: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Cargo c = dao.buscarPorId(id);
        if (c != null) System.out.println(c);
        else System.out.println("Cargo no encontrado.");
    }

    public static void actualizarCargoMenu(Scanner sc, CargoDao dao) {
        listarCargosMenu(sc, dao);
        System.out.print("Ingrese ID del cargo a actualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Cargo c = dao.buscarPorId(id);
        if (c != null) {
            System.out.print("Nuevo nombre: ");
            c.setNombre(sc.nextLine());
            dao.actualizar(c);
            System.out.println("Cargo actualizado correctamente.");
        } else System.out.println("No existe cargo con ese ID.");
    }

    public static void eliminarCargoMenu(Scanner sc, CargoDao dao) {
        listarCargosMenu(sc, dao);
        System.out.print("Ingrese ID del cargo a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        dao.eliminar(id);
        System.out.println("Cargo eliminado.");
    }

    public static void listarCargosMenu(Scanner sc, CargoDao dao){
        List<Cargo> carreras = dao.listar();
        System.out.println("\nListado de Cargos:");
        for (Cargo c : carreras) System.out.println(c);
    }

    public static void agregarEmpleadoMenu(Scanner sc, EmpleadoDao dao, CargoDao cargoDao) {
        Empleado empleado = new Empleado();
        System.out.print("Ingrese nombre del empleado: ");
        empleado.setNombre(sc.nextLine());
        System.out.print("Ingrese apellido del empleado: ");
        empleado.setApellido(sc.nextLine());
        System.out.print("Ingrese ID del cargo: ");
        Long cargoId = sc.nextLong();
        sc.nextLine();
        Cargo cargo = cargoDao.buscarPorId(cargoId);
        if (cargo != null) {
            empleado.setCargo(cargo);
        } else {
            System.out.println("Cargo no encontrado. Se dejará vacío.");
        }
        dao.guardar(empleado);
        System.out.println("Empleado guardado correctamente.");
    }

    public static void buscarEmpleadoMenu(Scanner sc, EmpleadoDao dao) {
        listarEmpleados(sc, dao);
        System.out.print("Ingrese ID: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Empleado e = dao.buscarPorId(id);
        if (e != null) System.out.println(e);
        else System.out.println("Empleado no encontrado.");
    }

    public static void actualizarEmpleadoMenu(Scanner sc, EmpleadoDao dao, CargoDao cargoDao) {
        listarEmpleados(sc, dao);
        System.out.print("Ingrese ID del empleado a actualizar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Empleado empleado = dao.buscarPorId(id);
        if (empleado != null) {
            System.out.print("Nuevo nombre (dejar vacío para no cambiar): ");
            String nuevoNombre = sc.nextLine();
            if (!nuevoNombre.isEmpty()) {
                empleado.setNombre(nuevoNombre);
            }
            System.out.print("Nuevo apellido (dejar vacío para no cambiar): ");
            String nuevoApellido = sc.nextLine();
            if (!nuevoApellido.isEmpty()) {
                empleado.setApellido(nuevoApellido);
            }
            System.out.print("Ingrese ID del nuevo cargo: ");
            Long cargoId = sc.nextLong();
            sc.nextLine();
            Cargo nuevoCargo = cargoDao.buscarPorId(cargoId);
            if (nuevoCargo != null) {
                empleado.setCargo(nuevoCargo);
            } else {
                System.out.println("Cargo no encontrado. Se mantiene el actual.");
            }
            dao.actualizar(empleado);
            System.out.println("Empleado actualizado correctamente.");
        } else {
            System.out.println("No existe empleado con ese ID.");
        }
    }

    public static void eliminarEmpleadoMenu(Scanner sc, EmpleadoDao dao) {
        listarEmpleados(sc, dao);
        System.out.print("Ingrese ID del empleado a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        dao.eliminar(id);
        System.out.println("Empleado eliminado.");
    }

    public static void listarEmpleados(Scanner sc, EmpleadoDao dao) {
        List<Empleado> empleados = dao.listar();
        System.out.println("\nListado de Empleados:");
        for (Empleado e : empleados) System.out.println(e);
    }
}
