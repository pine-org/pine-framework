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

@SuppressWarnings({"unchecked", "rawtypes"})
@Entity
@Table(name = "GOODS_PHOTO")
public class GoodsPhotoEntity extends AbstractAuditingEntity<Long> {

    @Lob
    @Column(name = "CONTENT", columnDefinition = "MEDIUMBLOB", length = 2147483647)
    private byte[] content;

    private GoodsEntity goods;

    public GoodsPhotoEntity() {
    }

    public GoodsPhotoEntity(byte[] content, GoodsEntity goods) {
        this.content = content;
        this.goods = goods;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "GOODS_PHOTO_GEN", sequenceName = "GOODS_PHOTO_SEQ", allocationSize = ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_PHOTO_GEN")
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

    @ManyToOne
    @JoinColumn(name = "GOODS_ID", referencedColumnName = "ID")
    @Access(AccessType.PROPERTY)
    public GoodsEntity getGoods() {
        return goods;
    }

    public void setGoods(GoodsEntity goods) {
        this.goods = goods;
    }

    @Override
    public Filter<GoodsPhotoEntity>[] toFilter() {
        Filter[] filters = new Filter[2];
        filters[0] = (root, query, cb) -> cb.equal(root.get("content"), getContent());
        return filters;
    }
}
