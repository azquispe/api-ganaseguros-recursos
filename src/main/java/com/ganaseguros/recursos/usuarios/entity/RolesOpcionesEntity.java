package com.ganaseguros.recursos.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles_opciones" , schema = "usuario")
public class RolesOpcionesEntity {
    @Id
    @Column(name = "rol_opcion_id")
    private Long rolOpcionId;

    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "opcion_id")
    private Long opcionId;

    @Column(name = "estado_id")
    private Long estadoId;
}
