#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefs;

import com.github.tomakehurst.wiremock.WireMockServer;
import ${package}.ServiceDriver;
import ${package}.TestApplication;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplication.class, loader = SpringApplicationContextLoader.class)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class
}) // Overriding the defaults to remove the dependency on ServletTestExecutionListener - it logs unnecessary warnings when running.
abstract class AbstractFeature {

    @Autowired
    ServiceDriver serviceDriver;

    @Autowired
    WireMockServer wireMockServer;

    @Before
    public void beforeScenario() {
        wireMockServer.start();
    }

    @After
    public void afterScenario() {
        wireMockServer.stop();
    }
}