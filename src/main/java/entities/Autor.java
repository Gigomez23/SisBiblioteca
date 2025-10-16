package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "autores")


public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "nacionalidad", length = 100, nullable = false)
    private String nacionalidad;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    @Override
    public String toString() {
        return "Autor{" +
                "id: " + id +
                " | nombre: " + nombre +
                " | nacionalidad: " + nacionalidad +
                " | fechaNacimiento: " + fechaNacimiento +
                " | libros: " + (libros != null ? libros.size() : 0) + '\'' +
                "============================";
    }

}
