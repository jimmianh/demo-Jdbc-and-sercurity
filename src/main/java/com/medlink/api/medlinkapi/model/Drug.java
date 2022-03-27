package com.medlink.api.medlinkapi.model;

import java.math.BigDecimal;
import java.util.List;

public class Drug {
    private int drug_id;
    private int drg_store_id;
    private String drg_drug_name;
    private List<DrgDrugPrice> units;

    public Drug() {
    }

    public Drug(int drug_id, int drg_store_id, String drg_drug_name, List<DrgDrugPrice> units) {
        this.drug_id = drug_id;
        this.drg_store_id = drg_store_id;
        this.drg_drug_name = drg_drug_name;
        this.units = units;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public int getDrg_store_id() {
        return drg_store_id;
    }

    public void setDrg_store_id(int drg_store_id) {
        this.drg_store_id = drg_store_id;
    }

    public String getDrg_drug_name() {
        return drg_drug_name;
    }

    public void setDrg_drug_name(String drg_drug_name) {
        this.drg_drug_name = drg_drug_name;
    }

    public List<DrgDrugPrice> getUnits() {
        return units;
    }

    public void setUnits(List<DrgDrugPrice> units) {
        this.units = units;
    }
}

