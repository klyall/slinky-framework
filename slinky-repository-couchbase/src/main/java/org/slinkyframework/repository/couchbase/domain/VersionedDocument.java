package org.slinkyframework.repository.couchbase.domain;

import org.springframework.data.annotation.Version;

public class VersionedDocument extends BaseDocument {

    @Version
    private long version;

    public long getVersion() {
        return version;
    }
}
