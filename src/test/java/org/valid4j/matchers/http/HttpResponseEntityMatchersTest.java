package org.valid4j.matchers.http;

import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasContentLength;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasEntity;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class HttpResponseEntityMatchersTest {

    @Path("/")
    public static class PayloadResource {
        @GET
        public String payload() {
            return "some-content-as-payload";
        }
    }

    @ClassRule
    public final static DropwizardClientRule server = new DropwizardClientRule(new PayloadResource());

    public final Client client = ClientBuilder.newClient();

    @Test
    public void shouldMatchByEntityWithStringValue() {
        Response response = client.target(server.baseUri()).request().get();
        assertThat(response, hasEntity(String.class, equalTo("some-content-as-payload")));
        assertThat(response, hasEntity(equalTo("some-content-as-payload")));
        assertThat(hasEntity(String.class, equalTo("payload")),
            isDescribedBy("has Entity \"payload\""));
      assertThat(mismatchOf(response, hasEntity(equalTo("some-other"))),
              equalTo("Entity was \"some-content-as-payload\""));
    }

    @Test
    public void shouldMatchByContentLength() {
        Response response = client.target(server.baseUri()).request().get();
        assertThat(response, hasContentLength(equalTo(23)));
        assertThat(hasContentLength(equalTo(7)),
                isDescribedBy("has Content-Length <7>"));
        assertThat(mismatchOf(response, hasContentLength(equalTo(0))),
                equalTo("Content-Length was <23>"));
    }
}
