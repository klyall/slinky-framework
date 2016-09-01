package example.spatial.domain;

import com.couchbase.client.java.repository.annotation.Field;
import org.slinkyframework.repository.couchbase.domain.BaseDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.geo.Point;

@Document
public class ExampleSpatialDocument extends BaseDocument {

    @Id private String id;
    @Field private String name;
    @Field private Point location;

    public ExampleSpatialDocument(String id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getLocation() {
        return location;
    }
}
