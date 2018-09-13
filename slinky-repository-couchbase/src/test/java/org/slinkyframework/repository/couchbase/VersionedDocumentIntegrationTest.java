package org.slinkyframework.repository.couchbase;

import example.versioned.ExampleVersionedRepository;
import example.versioned.ExampleVersionedRepositoryTestConfiguration;
import example.versioned.domain.ExampleVersionedDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleVersionedRepositoryTestConfiguration.class)
public class VersionedDocumentIntegrationTest {

    @Autowired
    private ExampleVersionedRepository testee;

    @Before
    public void setUp() {
        testee.deleteAll();
    }

    @Test
    public void shouldSaveAndRetrieveADocument() {
        String id = "versioned-1";

        ExampleVersionedDocument document = new ExampleVersionedDocument(id, "Test Name 1");

        ExampleVersionedDocument savedDocument = testee.save(document);
        assertThat("Document version", savedDocument.getVersion(), is(not(equalTo(0L))));

        Optional<ExampleVersionedDocument> retrievedDocument = testee.findById(id);
        assertThat("Document found", retrievedDocument.isPresent(), is(true));
        assertThat("Retrieved document", retrievedDocument.get(), is(equalTo(savedDocument)));
    }

    @Test
    public void shouldSaveDocumentTwiceIfVersionIsIncremented() {
        String id = "versioned-2";

        ExampleVersionedDocument document = new ExampleVersionedDocument(id, "Test Name 2");

        ExampleVersionedDocument savedDocument1 = testee.save(document);
        long version1 = savedDocument1.getVersion();

        savedDocument1.setName("Test Name 22");
        ExampleVersionedDocument savedDocument2 = testee.save(savedDocument1);
        long version2 = savedDocument2.getVersion();

        assertThat("Retrieved document", savedDocument2.getName(), is(equalTo("Test Name 22")));
        assertThat("Document version", version1, is(not(equalTo(version2))));
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveSameVersionOfDocumentTwice() {
        String id = "3";

        ExampleVersionedDocument document1 = new ExampleVersionedDocument(id, "Test Name 3");
        ExampleVersionedDocument document2 = new ExampleVersionedDocument(id, "Test Name 32");

        testee.save(document1);

        // Saving twice should fail as this Document uses optimistic locking
        testee.save(document2);
    }
}
