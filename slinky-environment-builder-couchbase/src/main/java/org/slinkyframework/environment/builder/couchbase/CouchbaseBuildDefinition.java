package org.slinkyframework.environment.builder.couchbase;

import com.couchbase.client.java.view.DefaultView;
import com.couchbase.client.java.view.View;
import org.slinkyframework.environment.builder.definition.AbstractBuildDefinition;
import org.slinkyframework.environment.builder.definition.BuildPriority;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

public class CouchbaseBuildDefinition extends AbstractBuildDefinition {

    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "password";
    public static final String DEFAULT_BUCKET_PASSWORD = "password";
    public static final int DEFAULT_BUCKET_SIZE = 100;
    public static final String VIEW_ALL = "all";
    private final String documentPackage;
    private final String documentClassName;

    private List<String> hosts = new ArrayList<>();
    private String adminUsername    = DEFAULT_ADMIN_USERNAME;
    private String adminPasssword   = DEFAULT_ADMIN_PASSWORD;
    private String bucketName;
    private String bucketPassword   = DEFAULT_BUCKET_PASSWORD;
    private int bucketSizeInMB      = DEFAULT_BUCKET_SIZE;
    private List<View> views = new ArrayList<>();

    public CouchbaseBuildDefinition(String name, String host, String bucketName, String documentPackage, String documentClassName) {
        this(BuildPriority.NORMAL, name, host, bucketName, documentPackage, documentClassName);
    }

    public CouchbaseBuildDefinition(BuildPriority priority, String name, String host, String bucketName, String documentPackage, String documentClassName) {
        super(priority, name);
        this.bucketName = bucketName;
        this.documentPackage = documentPackage;
        this.documentClassName = documentClassName;
        this.hosts.add(host);

        views.add(DefaultView.create(VIEW_ALL, defineAllView()));
    }


    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
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

    public List<View> getViews() {
        return views;
    }

    public void addView(View view) {
        views.add(view);
    }

    public int getBucketSizeInMB() {
        return bucketSizeInMB;
    }

    public void setBucketSizeInMB(int bucketSizeInMB) {
        this.bucketSizeInMB = bucketSizeInMB;
    }

    private String defineAllView() {
        String documentCanonicalName = documentPackage + "." + documentClassName;
        return format("function (doc, meta) { if (doc._class == '%s') { emit(meta.id, null); } }", documentCanonicalName);
    }

    public String getDesignDocumentName() {
        return uncapitalize(documentClassName);
    }
}
