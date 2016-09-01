package environment;

import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.DocumentDefinition;
import org.slinkyframework.environment.builder.definition.BuildDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseBuilderConfiguration {

    private static final String DESCRIPTION = "Example Couchbase Bucket";
    private static final String BUCKET_NAME = "exampleBucket";
    private static final String BUCKET_PASSWORD = "password";
    private static final String DOCUMENT_PACKAGE = "example";
    private static final String DOCUMENT_CLASS_NAME = "ExampleDocument";

    @Bean
    public BuildDefinition couchbaseBuildDefinition() {
        CouchbaseBuildDefinition buildDefinition = new CouchbaseBuildDefinition(DESCRIPTION, BUCKET_NAME);
        buildDefinition.setBucketPassword(BUCKET_PASSWORD);

        DocumentDefinition documentDefinition1 = new DocumentDefinition("example.audited.domain", "ExampleAuditedDocument");
        DocumentDefinition documentDefinition2 = new DocumentDefinition("example.versioned.domain", "ExampleVersionedDocument");
        DocumentDefinition documentDefinition3 = new DocumentDefinition("example.spatial.domain", "ExampleSpatialDocument");

        buildDefinition.addDocumentDefinition(documentDefinition1);
        buildDefinition.addDocumentDefinition(documentDefinition2);
        buildDefinition.addDocumentDefinition(documentDefinition3);

        buildDefinition.addSpatialView("byLocation", "views/spatial.js");

        return buildDefinition;
    }
}
