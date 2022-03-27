package com.medlink.api.medlinkapi.model;

import lombok.Data;

@Data
public class DrgDrugUnit {
    private int drug_unit_id;
    private int drug_id;
    private String unit_name;
}
