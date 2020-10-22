package com.pineframework.core.tutorial.eshop.business.domain;

import com.pineframework.core.business.domain.AbstractAuditingEntity;
import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "GOODS")
public class GoodsEntity extends AbstractAuditingEntity<Long> {

    @Column(name = "NAME", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "CODE", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "PRICE", nullable = true, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "DESCRIPTINO", nullable = true, length = 150, unique = true)
    private String description;

    @Column(name = "PHOTO", nullable = true)
    private byte[] photo;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "GOODS_GEN", sequenceName = "GOODS_SEQ", allocationSize = ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_GEN")
    @Access(AccessType.PROPERTY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public Filter<GoodsEntity>[] toFilter() {
        Filter[] filters = new Filter[2];
        filters[0] = (root, query, cb) -> cb.equal(root.get("code"), getCode());
        filters[1] = (root, query, cb) -> cb.equal(root.get("name"), getName());
        return filters;
    }
}
