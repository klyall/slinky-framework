package example.spatial;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@ComponentScan("example.spatial")
@EnableCouchbaseRepositories(basePackages = {"example.spatial"})
@EnableAutoConfiguration
public class ExampleSpatialRepositoryTestConfiguration {
}
