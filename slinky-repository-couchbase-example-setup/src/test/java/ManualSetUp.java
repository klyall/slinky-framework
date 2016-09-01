import environment.CouchbaseBuilderConfiguration;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.couchbase.CouchbaseEnvironmentBuilderFactory;
import org.slinkyframework.environment.builder.definition.BuildDefinition;

import java.util.Set;
import java.util.TreeSet;

public class ManualSetUp {

    private static final String TEST_HOST = "dev";

    public static final void main(String[] args) {
        EnvironmentBuilderContext builderContext = new EnvironmentBuilderContext(TEST_HOST, true);

        CouchbaseEnvironmentBuilderFactory factory = new CouchbaseEnvironmentBuilderFactory();
        EnvironmentBuilder environmentBuilder = factory.getInstance(builderContext);

        Set<BuildDefinition> buildDefinitions = new TreeSet<>();

        CouchbaseBuilderConfiguration builderConfiguration = new CouchbaseBuilderConfiguration();
        buildDefinitions.add(builderConfiguration.couchbaseBuildDefinition());

        environmentBuilder.setUp(buildDefinitions);
    }
}
