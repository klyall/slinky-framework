package example.audited;

import org.springframework.data.domain.AuditorAware;

// Note: This should really get values from Spring Security
public class NaiveAuditorAware implements AuditorAware<String> {

    public static final String DEFAULT_TEST_USER = "test-user";

    private String auditor = DEFAULT_TEST_USER;

    @Override
    public String getCurrentAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}