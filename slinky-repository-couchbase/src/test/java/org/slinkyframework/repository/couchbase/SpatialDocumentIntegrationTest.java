package org.slinkyframework.repository.couchbase;

import example.spatial.ExampleSpatialRepository;
import example.spatial.ExampleSpatialRepositoryTestConfiguration;
import example.spatial.domain.ExampleSpatialDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleSpatialRepositoryTestConfiguration.class)
public class SpatialDocumentIntegrationTest {

    @Autowired
    private ExampleSpatialRepository testee;

    @Before
    public void setUp() {
        testee.deleteAll();
    }

    @Test
    public void shouldSaveAndRetrieveADocument() {
        String id = "spatial-1";
        double longitude = 10.0;
        double latitude = 10.0;

        ExampleSpatialDocument document = createLocation(id, longitude, latitude);

        ExampleSpatialDocument retrievedDocument = testee.findOne(id);
        assertThat("Retrieved document", retrievedDocument, is(equalTo(document)));
    }

    @Test
    public void shouldFindByLocationNear() {
        String id = "spatial-1";
        double longitude = -0.1252986;
        double latitude = 51.5000981;

        ExampleSpatialDocument bigBen = createLocation("2", longitude, latitude);

        Distance distance = new Distance(1.0, Metrics.KILOMETERS);

        Point point = new Point(longitude, latitude);
        Set<ExampleSpatialDocument> results = testee.findByLocationNear(point, distance);

        assertThat("Results found", results.size(), is(equalTo(1)));
    }

    @Test
    public void shouldFindByLocationWithIn() {
        ExampleSpatialDocument inside = createLocation("spatial-2", 1, 1);
        ExampleSpatialDocument outside = createLocation("spatial-3", 3, 3);

        Distance distance = new Distance(1.0, Metrics.KILOMETERS);

        Point bottomLeft    = new Point(0, 0);
        Point topRight      = new Point(2, 2);

        Set<ExampleSpatialDocument> results = testee.findByLocationWithin(bottomLeft, topRight);

        assertThat("Results found", results.size(), is(equalTo(1)));
    }

    private ExampleSpatialDocument createLocation(String id, double longitude, double latitude) {
        ExampleSpatialDocument location = new ExampleSpatialDocument(id, "Test Spatial " + id, new Point(longitude, latitude));
        return testee.save(location);
    }
}
