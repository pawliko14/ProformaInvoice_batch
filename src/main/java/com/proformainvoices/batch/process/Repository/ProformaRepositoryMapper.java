package com.proformainvoices.batch.process.Repository;

import com.proformainvoices.batch.process.model.Proforma;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProformaRepositoryMapper implements RowMapper<Proforma> {
    @Override
    public Proforma mapRow(ResultSet resultSet, int i) throws SQLException {

        Proforma proforma = new Proforma();
        proforma.setId(resultSet.getLong("id"));
        proforma.setSupplier(resultSet.getString("supplier"));
        proforma.setAmount(resultSet.getBigDecimal("amount"));
        proforma.setCurrency(resultSet.getString("currency"));
        proforma.setDocumentNumber(resultSet.getString("document_number"));
        proforma.setDocumentDate(resultSet.getString("document_date"));
        proforma.setPurchaseOrderNumber(resultSet.getString("purchase_order_number"));
        proforma.setUrgency(resultSet.getString("urgency"));
        proforma.setProject(resultSet.getString("project"));
        proforma.setComments(resultSet.getString("comments"));
        proforma.setArea(resultSet.getString("area"));
        proforma.setForPayment(resultSet.getBoolean("for_payment"));
        proforma.setProposedPaymentDate(resultSet.getString("proposed_payment_date"));
        proforma.setPaid(resultSet.getBoolean("paid"));
        proforma.setDateOfPayment(resultSet.getString("date_of_payment"));

        return proforma;
    }
}
