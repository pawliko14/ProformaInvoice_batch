package com.proformainvoices.batch.process.JDBCTemplateImplementation;

import com.proformainvoices.batch.process.Repository.ProformaRepositoryJDBCTemplate;
import com.proformainvoices.batch.process.Repository.ProformaRepositoryMapper;
import com.proformainvoices.batch.process.model.Proforma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplProformaRepositoryJDBCTemplate  implements ProformaRepositoryJDBCTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from proforma", Integer.class);
    }

    @Override
    public int save(Proforma proforma) {
        return 0;
    }

    @Override
    public int update(Proforma proforma) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Proforma> findAll() {

        String sql = "Select * from proforma";
        return jdbcTemplate.query(sql, new ProformaRepositoryMapper());
    }

    @Override
    public List<Proforma> findById(Long id) {
        return null;
    }

    @Override
    public Proforma findRecordByDocumentNumber(String docNumber) {

        String sql = "Select * from proforma where document_number = ?";
        return jdbcTemplate.queryForObject(sql, new ProformaRepositoryMapper(), new Object[]{docNumber});
    }

    @Override
    public Proforma updatePaid(Proforma proforma) {
        long id = proforma.getId();

        String sql = "update proforma set paid = 1 where id = ?";

        jdbcTemplate.update(sql,id);
        return proforma;
    }


}