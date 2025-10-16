package entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "categorias")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "categorias")
    private List<Libro> libros;

    @Override
    public String toString() {
        return "Categoria{" +
                "id: " + id +
                " | nombre: " + nombre +
                " | libros: " + (libros != null ? libros.size() : 0) +
                " }" +
                "\n============================";
    }
}
