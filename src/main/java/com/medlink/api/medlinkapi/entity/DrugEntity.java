package com.medlink.api.medlinkapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drg_drug")
@Data
public class DrugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int drug_id;

    @Column
    private int drg_store_id;

    @Column
    private String drg_drug_name;

    @Column
    private String unit_name;

    @Column
    private double price;

    @Column
    private int drug_unit_id;

    @Column
    private int drg_price_id;




}
