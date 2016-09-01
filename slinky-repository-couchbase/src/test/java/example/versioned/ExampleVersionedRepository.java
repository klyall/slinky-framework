package example.versioned;

import example.versioned.domain.ExampleVersionedDocument;
import org.slinkyframework.repository.SlinkyRepository;
import org.springframework.data.repository.CrudRepository;

public interface ExampleVersionedRepository extends CrudRepository<ExampleVersionedDocument, String>, SlinkyRepository {
}
