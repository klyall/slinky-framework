package org.slinkyframework.repository.couchbase.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

//@Document
public class AuditedDocument extends VersionedDocument {

    @CreatedBy
    private String creator;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Date lastModification;

    @CreatedDate
    private Date creationDate;

    public String getCreator() {
        return creator;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
