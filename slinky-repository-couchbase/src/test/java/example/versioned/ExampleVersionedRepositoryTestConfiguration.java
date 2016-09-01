package example.versioned;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@ComponentScan("example.versioned")
@EnableCouchbaseRepositories(basePackages = {"example.versioned"})
@EnableAutoConfiguration
public class ExampleVersionedRepositoryTestConfiguration {

}
