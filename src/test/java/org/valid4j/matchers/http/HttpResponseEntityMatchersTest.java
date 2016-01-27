package org.valid4j.matchers.http;

import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasContentLength;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasEntity;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class HttpResponseEntityMatchersTest {

    @Rule
    public final ClientDriverRule service = new ClientDriverRule();
    public final Client client = ClientBuilder.newClient();

    @Test
    public void shouldMatchByEntityWithStringValue() {
        service.addExpectation(
                onRequestTo("/"),
                giveResponse("payload", MediaType.TEXT_PLAIN).withStatus(200));

        Response response = client.target(service.getBaseUrl()).request().get();
        assertThat(response, hasEntity(String.class, equalTo("payload")));
        assertThat(response, hasEntity(equalTo("payload")));
        assertThat(hasEntity(String.class, equalTo("payload")),
                isDescribedBy("has entity \"payload\""));
        assertThat(mismatchOf(response, hasEntity(String.class, equalTo("some-other"))),
                equalTo("entity was \"payload\""));
    }

    @Test
    public void shouldMatchByContentLength() {
        service.addExpectation(
                onRequestTo("/"),
                giveResponse("some-content-as-payload", MediaType.TEXT_PLAIN).withStatus(200));

        Response response = client.target(service.getBaseUrl()).request().get();
        assertThat(response, hasContentLength(equalTo(23)));
        assertThat(hasContentLength(equalTo(7)),
                isDescribedBy("has content length <7>"));
        assertThat(mismatchOf(response, hasContentLength(equalTo(0))),
                equalTo("content length was <23>"));
    }
}
