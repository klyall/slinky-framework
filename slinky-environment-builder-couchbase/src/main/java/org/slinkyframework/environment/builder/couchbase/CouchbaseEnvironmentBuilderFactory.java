package org.slinkyframework.environment.builder.couchbase;

import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.couchbase.docker.DockerCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.factory.EnvironmentBuilderFactory;
import org.springframework.stereotype.Component;

@Component
public class CouchbaseEnvironmentBuilderFactory implements EnvironmentBuilderFactory {

    @Override
    public boolean forClass(Class buildDefinitionClass) {
        return buildDefinitionClass.equals(CouchbaseBuildDefinition.class);
    }

    @Override
    public EnvironmentBuilder getInstance(EnvironmentBuilderContext environmentBuilderContext) {
        LocalCouchbaseEnvironmentBuilder localCouchbaseEnvironmentBuilder = new LocalCouchbaseEnvironmentBuilder(environmentBuilderContext.getTargetHost());

        if (environmentBuilderContext.isUseDocker()) {
            return new DockerCouchbaseEnvironmentBuilder(localCouchbaseEnvironmentBuilder);
        } else {
            return localCouchbaseEnvironmentBuilder;
        }
    }
}
