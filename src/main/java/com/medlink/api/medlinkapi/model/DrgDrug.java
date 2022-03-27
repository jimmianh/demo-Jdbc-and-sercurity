package com.medlink.api.medlinkapi.model;

import java.math.BigDecimal;

public class DrgDrug {
    private int drug_id;
    private int drg_store_id;
    private String drg_drug_name;
    private String unit_name;
    private BigDecimal price;

    public DrgDrug(int drug_id, int drg_store_id, String drg_drug_name, String unit_name, BigDecimal price) {
        this.drug_id = drug_id;
        this.drg_store_id = drg_store_id;
        this.drg_drug_name = drg_drug_name;
        this.unit_name = unit_name;
        this.price = price;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public DrgDrug() {
    }

    public int getDrg_store_id() {
        return drg_store_id;
    }

    public void setDrg_store_id(int drg_store_id) {
        this.drg_store_id = drg_store_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public String getDrg_drug_name() {
        return drg_drug_name;
    }

    public void setDrg_drug_name(String drg_drug_name) {
        this.drg_drug_name = drg_drug_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
