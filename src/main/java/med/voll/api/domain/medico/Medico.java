package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosMedico datosRegistroMedico) {
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.telefono = datosRegistroMedico.telefono();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre()!=null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.direccion()!=null){
            this.documento = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion()!=null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico(){
        this.activo =false;
    }
}
