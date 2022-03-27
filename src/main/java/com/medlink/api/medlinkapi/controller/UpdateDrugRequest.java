package com.medlink.api.medlinkapi.controller;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateDrugRequest {
    int drug_unit_id;
    int drug_id;
    BigDecimal price;
    String drg_drug_name;
}
