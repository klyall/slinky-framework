package example.audited;

import example.audited.domain.ExampleAuditedDocument;
import org.slinkyframework.repository.SlinkyRepository;
import org.springframework.data.repository.CrudRepository;

public interface ExampleAuditedRepository extends CrudRepository<ExampleAuditedDocument, String>, SlinkyRepository {
}
