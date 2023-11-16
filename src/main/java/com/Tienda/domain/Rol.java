package com.Tienda.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data // PARA LA CREACION AUTOMATICA DE LOS SET AND GET DE LOS ATRIBUTOS
@Entity //HACE QUE ESTA CLASE SEA UNA ENTIDAD
@Table(name = "rol")
public class Rol implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    
    @NotEmpty
    private String nombre;
    
    @Column(name = "id_usuario")
    private Long idUsuario;
    
}