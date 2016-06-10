#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.github.tomakehurst.wiremock.WireMockServer;
import ${package}.domain.Movie;
import ${package}.domain.MovieListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@Component
public class ServiceDriver {

    // Not actually used here but need to make sure the Wiremock server is running before instantiating this class
    @Autowired
    private WireMockServer wireMockServer;

    @Value("${symbol_dollar}{movie.service.host}")
    private String host;

    @Value("${symbol_dollar}{movie.service.port}")
    private String port;

    private String rating;
    private List<Movie> moviesFound;

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void findMovies() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        ResponseEntity<MovieListResponse> response = restTemplate.getForEntity(createUrl(), MovieListResponse.class);

        moviesFound = response.getBody().getData();
    }

    private String createUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append("/movies");
        sb.append("?rating=");
        sb.append(rating);

        return sb.toString();
    }

    public void verifyOnlyIncludesSpecificMovies(List<String> expectedRatings) {
        for (Movie movie : moviesFound) {
            assertThat("Movie", movie.getRating(), isIn(expectedRatings));
        }
    }

    public void verifyNoMoviesFound() {
        assertThat("Movies", moviesFound, is(empty()));
    }

    public void verifyMoviesFound() {
        assertThat("Movies", moviesFound, is(not(empty())));
    }
}
