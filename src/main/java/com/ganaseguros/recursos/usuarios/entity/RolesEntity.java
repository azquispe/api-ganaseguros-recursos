package com.ganaseguros.recursos.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "roles",schema = "usuario")
public class RolesEntity {
    @Id
    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "tipo_aplicacion_id")
    private Long tipoAplicacionId;

    @Column(name = "rol")
    private String rol;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado_id")
    private Long estadoId;


}
