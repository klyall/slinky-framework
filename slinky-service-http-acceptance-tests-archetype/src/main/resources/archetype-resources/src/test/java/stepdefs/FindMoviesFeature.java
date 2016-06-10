#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

public class FindMoviesFeature extends AbstractFeature {

    @Given("^Dan wants to find a list of movies up to a (.*) rating")
    public void bobWantsToFindAListOfMoviesForTheFollowingBrands(String rating) throws Throwable {
        serviceDriver.setRating(rating);
    }

    @When("^he requests a list of movies${symbol_dollar}")
    public void heRequestsAListOfMovies() throws Throwable {
        serviceDriver.findMovies();
    }

    @Then("^Dan gets a list of movies${symbol_dollar}")
    public void bobGetsAListOfMovies() throws Throwable {
        serviceDriver.verifyMoviesFound();
    }

    @Then("^it only includes (.*) rated movies${symbol_dollar}")
    public void itOnlyIncludesSpecifiedMovies(List<String> expectedRatings) throws Throwable {
        serviceDriver.verifyOnlyIncludesSpecificMovies(expectedRatings);
    }

    @Then("^Dan gets an empty list of movies${symbol_dollar}")
    public void bobGetsAnEmptyListOfMovies() throws Throwable {
        serviceDriver.verifyNoMoviesFound();
    }
}
