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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_PHOTO")
@Access(AccessType.FIELD)
public class ProductPhotoEntity extends AbstractAuditingEntity<Long> {

    @Lob
    @Column(name = "CONTENT", length = 2147483647)
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private ProductEntity product;

    public ProductPhotoEntity() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "PRODUCT_PHOTO_GEN", sequenceName = "PRODUCT_PHOTO_SEQ", allocationSize = ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_PHOTO_GEN")
    @Access(AccessType.PROPERTY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] photo) {
        this.content = photo;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public Filter<ProductPhotoEntity>[] toFilter() {
        Filter[] filters = new Filter[2];
        filters[0] = (root, query, cb) -> cb.equal(root.get("content"), getContent());
        return filters;
    }
}
