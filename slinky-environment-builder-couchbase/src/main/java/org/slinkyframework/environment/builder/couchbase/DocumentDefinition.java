package org.slinkyframework.environment.builder.couchbase;

import com.couchbase.client.java.view.DefaultView;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.View;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slinkyframework.environment.builder.couchbase.utils.ClasspathUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

public class DocumentDefinition {

    public static final String VIEW_ALL = "all";

    private final String packageName;
    private final String className;
    private Map<String, View> views = new HashMap<>();

    public DocumentDefinition(String packageName, String className) {
        Assert.notNull(packageName);
        Assert.notNull(className);

        this.packageName = packageName;
        this.className = className;

        views.put(VIEW_ALL, DefaultView.create(VIEW_ALL, defineAllView()));
    }

    public DesignDocument createDesignDocument() {
            return DesignDocument.create(
                    getDesignDocumentName(),
                    getViews());
    }

    public String getDesignDocumentName() {
        return uncapitalize(className);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public void addView(String name, String filename) {
        views.put(name, DefaultView.create(name, ClasspathUtils.readFile(filename)));
    }

    public void addView(View view) {
        views.put(view.name(), view);
    }

    public List<View> getViews() {
        return new ArrayList(views.values());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private String defineAllView() {
        String documentCanonicalName = packageName + "." + className;
        return format("function (doc, meta) { if (doc._class == '%s') { emit(meta.id, null); } }", documentCanonicalName);
    }
}
