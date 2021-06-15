package com.proformainvoices.batch.process.Repository;

import com.proformainvoices.batch.process.model.Proforma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfromaRepository extends JpaRepository<Proforma,Long> {
}
