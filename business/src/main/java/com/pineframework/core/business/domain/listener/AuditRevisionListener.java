package com.pineframework.core.business.domain.listener;

import com.pineframework.core.business.domain.audit.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public class AuditRevisionListener implements RevisionListener {

    /**
     * set username before insert history log to audit tables
     *
     * @param revisionEntity
     */
    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity.class
                .cast(revisionEntity)
                .setUsername("currentuser");
    }

}
