package com.pineframework.core.tutorial.eshop.business.domain;

import com.pineframework.core.business.domain.AbstractAuditingTreeEntity;
import com.pineframework.core.datamodel.persistence.FlatPersistence;
import com.pineframework.core.datamodel.persistence.TreePersistence;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "GOODS")
@Audited
@AuditOverrides(value = {
        @AuditOverride(forClass = TreePersistence.class),
        @AuditOverride(forClass = FlatPersistence.class)})
public class Goods extends AbstractAuditingTreeEntity<Long, Goods> {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "GOODS", sequenceName = "GOODS_SEQ", allocationSize = ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_GEN")
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

}
