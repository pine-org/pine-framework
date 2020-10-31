package com.pineframework.core.hibernate.audit;

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
        ((AuditRevisionEntity) revisionEntity)
                .setUsername("currentuser");
    }

}
