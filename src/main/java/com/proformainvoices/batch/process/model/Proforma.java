package com.proformainvoices.batch.process.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "proforma")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Proforma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private Long id;

    private String documentDate;
    private String supplier;
    private BigDecimal amount;
    private String currency;
    private String documentNumber;
    private String purchaseOrderNumber;
    private String urgency;
    private String project;
    private String comments;
    private String area;
    private boolean forPayment;
    private String proposedPaymentDate;
    private boolean paid;
    private String dateOfPayment;

    @Override
    public String toString() {
        return "Proforma{" +
                "id=" + id +
                ", documentDate='" + documentDate + '\'' +
                ", supplier='" + supplier + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", purchaseOrderNumber='" + purchaseOrderNumber + '\'' +
                ", urgency='" + urgency + '\'' +
                ", project='" + project + '\'' +
                ", comments='" + comments + '\'' +
                ", area='" + area + '\'' +
                ", forPayment=" + forPayment +
                ", proposedPaymentDate='" + proposedPaymentDate + '\'' +
                ", paid=" + paid +
                ", dateOfPayment='" + dateOfPayment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proforma proforma = (Proforma) o;
        return forPayment == proforma.forPayment &&
                paid == proforma.paid &&
                Objects.equals(id, proforma.id) &&
                Objects.equals(documentDate, proforma.documentDate) &&
                Objects.equals(supplier, proforma.supplier) &&
                Objects.equals(amount, proforma.amount) &&
                Objects.equals(currency, proforma.currency) &&
                Objects.equals(documentNumber, proforma.documentNumber) &&
                Objects.equals(purchaseOrderNumber, proforma.purchaseOrderNumber) &&
                Objects.equals(urgency, proforma.urgency) &&
                Objects.equals(project, proforma.project) &&
                Objects.equals(comments, proforma.comments) &&
                Objects.equals(area, proforma.area) &&
                Objects.equals(proposedPaymentDate, proforma.proposedPaymentDate) &&
                Objects.equals(dateOfPayment, proforma.dateOfPayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentDate, supplier, amount, currency, documentNumber, purchaseOrderNumber, urgency, project, comments, area, forPayment, proposedPaymentDate, paid, dateOfPayment);
    }


}
