package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "asiento_det_tem", schema = "erp")
public class AsientoDetTemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asiento_det_tem_id")
    private Long asientoDetTemId;

    @Column(name = "asiento_cab_tem_id")
    private Long asientoCabTemId;

    @Column(name = "codigo_concepto")
    private String codigoConcepto;

    @Column(name = "columna_excel")
    private int columnaExcel;



    @Column(name = "amount")
    private Double amount;

    @Column(name = "estado_id")
    private Long estadoId;

}
