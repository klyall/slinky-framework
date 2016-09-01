package example.versioned.domain;

import com.couchbase.client.java.repository.annotation.Field;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slinkyframework.repository.couchbase.domain.VersionedDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class ExampleVersionedDocument extends VersionedDocument {

    @Id
    @Nullable private String id;

    @Field
    @Nullable private String name;

    public ExampleVersionedDocument(@Nullable String id, @Nullable String name) {
        this.id = id;
        this.name = name;
    }

    public @Nullable String getId() {
        return id;
    }

    public @Nullable String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
