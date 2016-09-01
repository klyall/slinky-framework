
package example.spatial;

import example.spatial.domain.ExampleSpatialDocument;
import org.slinkyframework.repository.SlinkyRepository;
import org.springframework.data.couchbase.core.query.Dimensional;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ExampleSpatialRepository extends CrudRepository<ExampleSpatialDocument, String>, SlinkyRepository {

    @Dimensional(dimensions=2, designDocument="spatial", spatialViewName="byLocation")
    Set<ExampleSpatialDocument> findByLocationNear(Point point, Distance distance);

    @Dimensional(dimensions=2, designDocument="spatial", spatialViewName="byLocation")
    Set<ExampleSpatialDocument> findByLocationWithin(Point bottomLeft, Point topRight);
}
