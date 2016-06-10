#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootApplication
@Configuration
public class TestApplication {

    private WireMockServer wireMockServer;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }

    @Bean
    public WireMockServer wireMockServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8089));
        // Make sure the Wiremock server is started before returning bean
        wireMockServer.start();
        return wireMockServer;
    }
}