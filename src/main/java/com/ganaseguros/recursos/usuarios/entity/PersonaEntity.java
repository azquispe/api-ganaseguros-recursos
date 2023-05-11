package com.ganaseguros.recursos.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "personas", schema = "usuario")
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Long personaId;

    @Column(name = "genero_id")
    private Long generoId;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "apellido_esposo")
    private String apellidoEsposo;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "ciudad_expedido_id")
    private Long ciudadExpedidoId;

    @Column(name = "numero_celular")
    private String numeroCelular;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "estado_id")
    private Long estadoId;

}
