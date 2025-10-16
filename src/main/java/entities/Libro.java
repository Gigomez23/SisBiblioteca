package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "anio_publicacion", nullable = false)
    private LocalDate anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "libro_categoria",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @Override
    public String toString() {
        return "Libro{" +
                "id: " + id +
                " | titulo: " + titulo +
                " | añoPublicacion: " + anioPublicacion +
                " | autor: " + (autor != null ? autor.getNombre() : "Sin autor") +
                " | categorias: " + (categorias != null ? categorias.size() : 0) + '\'' +
                "============================";
    }
}
