package com.pineframework.core.tutorial.eshop.business.domain;

import com.pineframework.core.business.domain.AbstractAuditingEntity;
import com.pineframework.core.datamodel.filter.Filter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Access(AccessType.FIELD)
public class ProductEntity extends AbstractAuditingEntity<Long> {

    @Column(name = "NAME", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "CODE", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "PRICE", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PHOTO", length = 2147483647)
    private byte[] photo;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            targetEntity = ProductPhotoEntity.class)
    private List<ProductPhotoEntity> photos = new ArrayList<>();

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "PRODUCT_GEN", sequenceName = "PRODUCT_SEQ", allocationSize = ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_GEN")
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

    public List<ProductPhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ProductPhotoEntity> photos) {
        this.photos = photos;
    }

    @Override
    public Filter<ProductEntity>[] toFilter() {
        Filter[] filters = new Filter[2];
        filters[0] = (root, query, cb) -> cb.equal(root.get("code"), getCode());
        filters[1] = (root, query, cb) -> cb.equal(root.get("name"), getName());
        return filters;
    }
}
