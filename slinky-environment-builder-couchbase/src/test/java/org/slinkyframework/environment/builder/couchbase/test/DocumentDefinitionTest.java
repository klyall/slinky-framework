package org.slinkyframework.environment.builder.couchbase.test;

import com.couchbase.client.java.view.DefaultView;
import org.junit.Test;
import org.slinkyframework.environment.builder.couchbase.DocumentDefinition;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DocumentDefinitionTest {

    @Test
    public void twoIdenticalDocumentDefintionsShouldBeEqual() {
        String testPackageName = "package1";
        String testClassName = "Class1";

        DocumentDefinition definition1 = new DocumentDefinition(testPackageName, testClassName);
        DocumentDefinition definition2 = new DocumentDefinition(testPackageName, testClassName);

        assertThat(definition1, is(equalTo(definition2)));
    }

    @Test
    public void shouldCreateDesignDocument() {
        String testPackageName = "package1";
        String testClassName = "Class1";
        String expectedName  = "class1";

        DocumentDefinition testee = new DocumentDefinition(testPackageName, testClassName);

        assertThat("Design document name", testee.createDesignDocument().name(), is(equalTo(expectedName)));
    }

    @Test
    public void shouldCreateAllViewByDefault() {
        String testPackageName = "package1";
        String testClassName = "Class1";
        String expectedName  = "all";

        DocumentDefinition testee = new DocumentDefinition(testPackageName, testClassName);

        assertThat("Views size", testee.getViews().size(), is(equalTo(1)));
        assertThat("View name", testee.getViews().get(0).name(), is(equalTo(expectedName)));
    }

    @Test
    public void shouldBeAbleToOverwriteDefaultAllView() {
        String testPackageName = "package1";
        String testClassName = "Class1";
        String expectedDefinition = "new definition";

        DocumentDefinition testee = new DocumentDefinition(testPackageName, testClassName);
        testee.addView(DefaultView.create(DocumentDefinition.VIEW_ALL, expectedDefinition));

        assertThat("Views size", testee.getViews().size(), is(equalTo(1)));
        assertThat("View name", testee.getViews().get(0).map(), is(equalTo(expectedDefinition)));
    }
    @Test
    public void shouldLoadMapReduceViewFromClasspath() {
        String testPackageName = "package1";
        String testClassName = "Class1";
        String name         = "dummyView";
        String filename     = "test-views/dummy-view.js";
        String expectedMap  = "line 1\nline 2";

        DocumentDefinition testee = new DocumentDefinition(testPackageName, testClassName);
        testee.addView(name, filename);

        assertThat("Map reduce views", testee.getViews().size(), is(equalTo(2)));
        assertThat("Map reduce view definition", testee.getViews().get(1).map(), is(equalTo(expectedMap)));
    }


}
