package com.ganaseguros.recursos.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios_roles",schema = "usuario")
public class UsuarioRolesEntity {
    @Id
    @Column(name = "usuario_rol_id")
    private Long usuarioRolId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "estado_id")
    private Long estadoId;

}
