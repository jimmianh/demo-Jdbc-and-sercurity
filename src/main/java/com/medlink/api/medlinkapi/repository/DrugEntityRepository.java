package com.medlink.api.medlinkapi.repository;

import com.google.gson.Gson;
import com.medlink.api.medlinkapi.controller.InsertRequest;
import com.medlink.api.medlinkapi.controller.UpdateDrugRequest;
import com.medlink.api.medlinkapi.exception.ExceptionHandle;
import com.medlink.api.medlinkapi.model.DrgDrug;
import com.medlink.api.medlinkapi.model.DrgDrugPrice;
import com.medlink.api.medlinkapi.model.DrgDrugUnit;
import com.medlink.api.medlinkapi.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DrugEntityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ExceptionHandle exceptionHandle;


    public Connection establishConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medlinkdemo", "root", "Jimmi_1807");
            System.out.println("Connection made!");
            System.out.println("Schema Name:" + connection.getSchema() + "\n");
        } catch (SQLException sqle) {
            System.err.println("SQL Exception thrown while making connection");
        }
        return connection;
    }

    static class DrugRowMapper implements RowMapper<Drug> {

        @Override
        public Drug mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Drug drug = new Drug();
            drug.setDrug_id(resultSet.getInt("drug_id"));
            drug.setDrg_store_id(resultSet.getInt("drg_store_id"));
            drug.setDrg_drug_name(resultSet.getString("drg_drug_name"));
            return drug;
        }
    }

    public List<Drug> showAll() {
        return jdbcTemplate.query("SELECT * FROM drg_drug", new DrugRowMapper());
    }

    //find
    public String findById(int drug_id) throws SQLException {
        if (drug_id != 0) {
            Connection connection = establishConnection();
            PreparedStatement ps = connection.prepareStatement("select * from drg_drug where drug_id = ?");
            ps.setInt(1, drug_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DrgDrug drgDrug = (DrgDrug) jdbcTemplate.queryForObject(
                        "select * from drg_drug where drug_id = ?",
                        new Object[]{drug_id},
                        new BeanPropertyRowMapper(DrgDrug.class));

                List<DrgDrugPrice> drgDrugPrice = showAllPrice(drug_id);
                Drug drug = new Drug(drgDrug.getDrug_id(), drgDrug.getDrg_store_id(), drgDrug.getDrg_drug_name(), drgDrugPrice);
                Gson g = new Gson();
                String response = g.toJson(drug);
                return response;
            } else {
                return exceptionHandle.getIdNotExist(exceptionHandle);
            }

        } else {
            return exceptionHandle.getIdIsNull(exceptionHandle);
        }
    }

    public List<DrgDrugUnit> showAllUnit(int drug_id) {
        return jdbcTemplate.query("SELECT * FROM drg_drug_unit where drug_id = ?", new DrgUnitRowMapper(), drug_id);
    }


    static class DrgUnitRowMapper implements RowMapper<DrgDrugUnit> {

        @Override
        public DrgDrugUnit mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            DrgDrugUnit drgDrugUnit = new DrgDrugUnit();
            drgDrugUnit.setDrug_id(resultSet.getInt("drug_id"));
            drgDrugUnit.setDrug_unit_id(resultSet.getInt("drug_unit_id"));
            drgDrugUnit.setUnit_name(resultSet.getString("unit_name"));
            return drgDrugUnit;
        }
    }

    static class DrgPriceRowMapper implements RowMapper<DrgDrugPrice> {

        @Override
        public DrgDrugPrice mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            DrgDrugPrice drgDrugPrice = new DrgDrugPrice();
            drgDrugPrice.setDrg_price_id(resultSet.getInt("drg_price_id"));
            drgDrugPrice.setPrice(resultSet.getBigDecimal("price"));
            drgDrugPrice.setDrug_unit_id(resultSet.getInt("drug_unit_id"));
            drgDrugPrice.setUnit_name(resultSet.getString("unit_name"));
            return drgDrugPrice;
        }
    }

    public List<DrgDrugPrice> showAllPrice(int drug_id) {
        return jdbcTemplate.query("SELECT * FROM drg_drug_price where drug_id = ?", new DrgPriceRowMapper(), drug_id);
    }


    static boolean idExistsSQL(Connection connection, String tableName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }


    //Insert
    public void insert(InsertRequest insertRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolderUnit = new GeneratedKeyHolder();
        KeyHolder keyHolderPrice = new GeneratedKeyHolder();

        try {
            //insert vào drug
            String INSERT_DRUG_MESSAGE_SQL
                    = "INSERT INTO drg_drug ( drg_drug_name, drg_store_id,drg_drug_cd,vat_percent) " + "VALUE(?, ?,?,?)";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_DRUG_MESSAGE_SQL, new String[]{"drug_id"});
                ps.setString(1, insertRequest.getDrg_drug_name());
                ps.setInt(2, insertRequest.getDrg_store_id());
                ps.setString(3, insertRequest.getDrg_drug_cd());
                ps.setInt(4, insertRequest.getVat_percent());
                return ps;
            }, keyHolder);

            Number keyDrug = keyHolder.getKey();
            System.out.println("Newly persisted customer generated id: " + keyDrug.longValue());
            System.out.println("-- loading customer by id --");


            List<DrgDrugUnit> drgDrugUnitList = showAllUnit(keyDrug.intValue());

//            for (int i = 0; i < drgDrugUnitList.size(); i++) {


            String INSERT_UNIT_MESSAGE_SQL
                    = "INSERT INTO drg_drug_unit ( unit_name,unit_id, drg_store_id, drug_id)" + "VALUE(?, ?, ?, ?)";
            System.out.println(keyDrug);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_UNIT_MESSAGE_SQL, new String[]{"drug_unit_id"});
                ps.setString(1, insertRequest.getUnit_name());
                ps.setInt(2, insertRequest.getUnit_id());
                ps.setInt(3, insertRequest.getDrg_store_id());
                ps.setLong(4, keyDrug.longValue());
                return ps;
            }, keyHolderUnit);
            Number keyDrgUnitId = keyHolderUnit.getKey();
            System.out.println("Newly persisted customer generated unit_id: " + keyDrgUnitId.longValue());
            System.out.println("-- loading customer by id --");
            //insert vào price


            String INSERT_PRICE_MESSAGE_SQL
                    = "INSERT INTO drg_drug_price (price ,drug_unit_id, unit_id, drg_store_id, unit_name , drug_id )" + "VALUE(?, ?, ?, ?, ?, ?)";
            System.out.println(keyDrug);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_PRICE_MESSAGE_SQL, new String[]{"drg_price_id"});
                ps.setBigDecimal(1, insertRequest.getPrice());
                ps.setLong(2, keyDrgUnitId.longValue());
                ps.setInt(3, insertRequest.getUnit_id());
                ps.setInt(4, insertRequest.getDrg_store_id());
                ps.setString(5, insertRequest.getUnit_name());
                ps.setLong(6, keyDrug.longValue());
                return ps;
            }, keyHolderPrice);

            Number keyDrgPriceId = keyHolderPrice.getKey();

            System.out.println("Newly persisted customer generated unit_id: " + keyDrgPriceId.longValue());
            System.out.println("-- loading customer by id --");
            System.out.println("Thêm mới " + insertRequest.getDrg_drug_name() + " thành công");
//            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public String updateByUnitId(UpdateDrugRequest updateDrugRequest) {
        if (updateDrugRequest.getDrug_id() == 0) {
            System.out.println("Id không tồn tại hoặc do bạn đang để trống id");

            return exceptionHandle.updateError(exceptionHandle);
        } else {
            System.out.println("hello");
            jdbcTemplate.update("update drg_drug_price " + " set price = ? " + " where drug_unit_id = ?",
                    updateDrugRequest.getPrice(), updateDrugRequest.getDrug_unit_id());
            jdbcTemplate.update("update drg_drug " + " set drg_drug_name = ? " + " where drug_id = ?",
                    updateDrugRequest.getDrg_drug_name(), updateDrugRequest.getDrug_id());
            System.out.println("Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrug_id());
            return "Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrug_id();
        }
    }


    public String deleteById(int drug_id) throws SQLException {
        if (drug_id != 0) {
            Connection connection = establishConnection();
            PreparedStatement ps = connection.prepareStatement("select * from drg_drug where drug_id = ?");
            ps.setInt(1, drug_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jdbcTemplate.update("DELETE FROM drg_drug_price WHERE drug_id=?", drug_id);
                jdbcTemplate.update("DELETE FROM drg_drug_unit WHERE drug_id=?", drug_id);
                jdbcTemplate.update("DELETE FROM drg_drug WHERE drug_id=?", drug_id);
                return "Xóa thành công sản phẩm có Id là:" + drug_id;
            } else {
                return exceptionHandle.deleteIdNotExist(exceptionHandle).toString();
            }
        } else {
            return exceptionHandle.deleteIdIsNull(exceptionHandle).toString();
        }
    }
}