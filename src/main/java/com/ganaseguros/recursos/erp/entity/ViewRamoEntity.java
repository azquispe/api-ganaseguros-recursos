package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "view_ramos_ti", schema = "erp")
public class ViewRamoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rramo_id")
    private Long rramoId;

    @Column(name = "nombre_ramo")
    private String nombreRamo;

}
