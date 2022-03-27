package com.medlink.api.medlinkapi.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class DrgDrugPrice {
    private int drg_price_id;
    private int drug_unit_id;
    private String unit_name;
    private BigDecimal price;
}
