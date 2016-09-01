package org.slinkyframework.repository.couchbase;

import example.audited.ExampleAuditedRepository;
import example.audited.ExampleAuditedRepositoryTestConfiguration;
import example.audited.NaiveAuditorAware;
import example.audited.domain.ExampleAuditedDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleAuditedRepositoryTestConfiguration.class)
public class AuditedDocumentIntegrationTest {

    @Autowired
    private ExampleAuditedRepository testee;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldSaveAndRetrieveADocument() {
        String id = "audit-1";

        ExampleAuditedDocument document = new ExampleAuditedDocument(id, "Test Name 1");

        ExampleAuditedDocument savedDocument = testee.save(document);
        assertThat("Document version", savedDocument.getVersion(), is(not(equalTo(0L))));
        assertThat("Document creation date", savedDocument.getCreationDate(), is(not(nullValue())));
        assertThat("Document creator", savedDocument.getCreator(), is(equalTo(NaiveAuditorAware.DEFAULT_TEST_USER)));
        assertThat("Document last modification date", savedDocument.getLastModification(), is(not(nullValue())));
        assertThat("Document last modifier", savedDocument.getLastModifiedBy(), is(equalTo(NaiveAuditorAware.DEFAULT_TEST_USER)));

        ExampleAuditedDocument retrievedDocument = testee.findOne(id);
        assertThat("Retrieved document", retrievedDocument, is(equalTo(savedDocument)));
    }

    @Test
    public void shouldSaveDocumentTwiceAndSeeModificationDateChangeAndCreationDateStaySame() {
        String id = "audit-2";

        ExampleAuditedDocument document = new ExampleAuditedDocument(id, "Test Name 2");

        ExampleAuditedDocument savedDocument = testee.save(document);
        Date creationDate1 = savedDocument.getCreationDate();
        Date lastModificationDate1 = savedDocument.getLastModification();

        savedDocument.setName("Test Name 22");
        ExampleAuditedDocument savedDocument2 = testee.save(savedDocument);

        Date creationDate2 = savedDocument.getCreationDate();
        Date lastModificationDate2 = savedDocument.getLastModification();

        assertThat("Document creation date", creationDate2, is(equalTo(creationDate1)));
        assertThat("Document last modification date", lastModificationDate2, is(not(equalTo(lastModificationDate1))));
        assertThat("Document creator", savedDocument.getCreator(), is(equalTo(NaiveAuditorAware.DEFAULT_TEST_USER)));
        assertThat("Document last modifier", savedDocument.getLastModifiedBy(), is(equalTo(NaiveAuditorAware.DEFAULT_TEST_USER)));
    }
}
