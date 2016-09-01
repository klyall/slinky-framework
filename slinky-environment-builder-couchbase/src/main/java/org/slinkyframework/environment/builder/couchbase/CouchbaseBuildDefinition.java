package org.slinkyframework.environment.builder.couchbase;

import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.SpatialView;
import com.couchbase.client.java.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.couchbase.utils.ClasspathUtils;
import org.slinkyframework.environment.builder.definition.AbstractBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CouchbaseBuildDefinition extends AbstractBuildDefinition {

    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "password";
    public static final String DEFAULT_BUCKET_PASSWORD = "password";
    public static final int DEFAULT_BUCKET_SIZE = 100;

    private static final Logger LOG = LoggerFactory.getLogger(CouchbaseBuildDefinition.class);

    private static final String FULL_TEXT_INDEX_NAME = "fullDocumentIndex";
    private String adminUsername = DEFAULT_ADMIN_USERNAME;
    private String adminPasssword = DEFAULT_ADMIN_PASSWORD;
    private String bucketName;
    private String bucketPassword = DEFAULT_BUCKET_PASSWORD;
    private int bucketSizeInMB = DEFAULT_BUCKET_SIZE;
    private Set<DocumentDefinition> documentDefinitions = new HashSet<>();
    private List<View> spatialViews = new ArrayList<>();

    public CouchbaseBuildDefinition(String name, String bucketName) {
        this(BuildPriority.NORMAL, name, bucketName);
    }

    public CouchbaseBuildDefinition(BuildPriority priority, String name, String bucketName) {
        super(priority, name);
        this.bucketName = bucketName;
    }

    public CouchbaseBuildDefinition(String name, String bucketName, String documentPackage, String documentClassName) {
        this(BuildPriority.NORMAL, name, bucketName, documentPackage, documentClassName);
    }

    public CouchbaseBuildDefinition(BuildPriority priority, String name, String bucketName, String documentPackage, String documentClassName) {
        super(priority, name);
        this.bucketName = bucketName;
        this.addDocumentDefinition(new DocumentDefinition(documentPackage, documentClassName));
    }


    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPasssword() {
        return adminPasssword;
    }

    public void setAdminPasssword(String adminPasssword) {
        this.adminPasssword = adminPasssword;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getBucketPassword() {
        return bucketPassword;
    }

    public void setBucketPassword(String bucketPassword) {
        this.bucketPassword = bucketPassword;
    }
    public int getBucketSizeInMB() {
        return bucketSizeInMB;
    }

    public void setBucketSizeInMB(int bucketSizeInMB) {
        this.bucketSizeInMB = bucketSizeInMB;
    }

    public List<DesignDocument> getDesignDocuments() {
        return documentDefinitions
                .stream()
                .map(d -> d.createDesignDocument())
                .collect(Collectors.toList());
    }

    public Set<DocumentDefinition> getDocumentDefinitions() {
        return documentDefinitions;
    }

    public String getFullTextIndexName() {
        return bucketName + "_" + FULL_TEXT_INDEX_NAME;
    }

    public List<View> getSpatialViews() {
        return spatialViews;
    }

    public void addSpatialView(View view) {
        spatialViews.add(view);
    }

    public void addSpatialView(String name, String filename) {
        spatialViews.add(SpatialView.create(name, ClasspathUtils.readFile(filename)));
    }

    public String getSpatialDesignDocumentName() {
        return "spatial";
    }

    public void addDocumentDefinition(DocumentDefinition documentDefinition) {
        documentDefinitions.add(documentDefinition);
    }
}
