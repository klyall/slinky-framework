package example.audited;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@ComponentScan("example.audited")
@EnableCouchbaseRepositories(basePackages = {"example.audited"})
@EnableCouchbaseAuditing
@EnableAutoConfiguration
public class ExampleAuditedRepositoryTestConfiguration {

    @Bean
    public NaiveAuditorAware testAuditorAware() {
        return new NaiveAuditorAware();
    }
}
