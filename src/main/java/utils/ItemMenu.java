package utils;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemMenu {
    public String descripcion;
    public Runnable accion;

    public ItemMenu(String descripcion, Runnable accion) {
        this.descripcion = descripcion;
        this.accion = accion;
    }
}
