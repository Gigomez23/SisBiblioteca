package utils;

public class ItemMenu {
    public String descripcion;
    public Runnable accion;

    public ItemMenu(String descripcion, Runnable accion) {
        this.descripcion = descripcion;
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Runnable getAccion() {
        return accion;
    }

    public void setAccion(Runnable accion) {
        this.accion = accion;
    }
}
