package com.medlink.api.medlinkapi.controller;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class InsertRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int drug_id;

    private int vat_percent;

    private String drg_drug_cd;
    private int drg_store_id;

    private String drg_drug_name;
    private String unit_name;
    private BigDecimal price;
    private int unit_id;
}
