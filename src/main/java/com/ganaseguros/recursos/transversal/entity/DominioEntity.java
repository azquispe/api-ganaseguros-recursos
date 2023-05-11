package com.ganaseguros.recursos.transversal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "dominios", schema = "transversal")
public class DominioEntity {
    @Id
    @Column(name = "dominio_id")
    private Long dominioId;

    @Column(name = "dominio")
    private String dominio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "abreviatura")
    private String abreviatura;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "estado_id")
    private Long estadoId;

}
