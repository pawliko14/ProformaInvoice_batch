package com.proformainvoices.batch.process.Repository;

import com.proformainvoices.batch.process.model.Proforma;

import java.util.List;

public interface ProformaRepositoryJDBCTemplate {

    int count();

    int save(Proforma proforma);

    int update(Proforma proforma);

    int deleteById(Long id);

    List<Proforma> findAll();

    List<Proforma> findById(Long id);

    Proforma findRecordByDocumentNumber(String docNumber);

    Proforma updatePaid(Proforma proforma);


}
