package com.ganaseguros.recursos.transversal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "opciones" , schema = "transversal")
public class OpcionesEntity {
    @Id
    @Column(name = "opcion_id")
    private Long opcionId;

    @Column(name = "opcion_origen_id")
    private Long opcionOrigenId;

    @Column(name = "tipo_opcion_id")
    private Long tipoOpcionId;

    @Column(name = "tipo_aplicacion_id")
    private Long tipoAplicacionId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "link")
    private String link;

    @Column(name = "orden_despliegue")
    private Long ordenDespliegue;

    @Column(name = "estado_id")
    private Long estadoId;
}
