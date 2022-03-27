package com.medlink.api.medlinkapi.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FindByIdResponse {
    private String drg_drug_name;
    private int  drug_id;
    private String unit_name;
    private BigDecimal price;
    private int drg_unit_id;
    private int drg_price_id ;
}
