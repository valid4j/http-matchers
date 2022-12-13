package org.valid4j.matchers.http;

import io.dropwizard.testing.junit5.DropwizardClientExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasContentLength;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasEntity;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

@ExtendWith(DropwizardExtensionsSupport.class)
class HttpResponseEntityMatchersTest {

    @Path("text")
    @Produces(MediaType.TEXT_PLAIN)
    public static class TextPayloadResource {
        @GET
        public String payload() {
            return "some-content-as-payload";
        }
    }

    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public static class JsonPayloadResource {
        @GET
        public Response payload() {
            Map<String, Object> payload = new HashMap<>();
            payload.put("key1", "value1");
            payload.put("key2", "value2");
            return Response.ok().entity(payload).build();
        }
    }

    public final static DropwizardClientExtension server = new DropwizardClientExtension(
            new TextPayloadResource(),
            new JsonPayloadResource());

    public final Client client = ClientBuilder.newClient();

    @Test
    void shouldMatchByEntityWithStringValue() {
        Response response = client.target(server.baseUri()).path("text").request().get();
        assertThat(response, hasEntity(String.class, equalTo("some-content-as-payload")));
        assertThat(response, hasEntity(equalTo("some-content-as-payload")));
        assertThat(hasEntity(String.class, equalTo("payload")),
                isDescribedBy("has Entity \"payload\""));
        assertThat(mismatchOf(response, hasEntity(equalTo("some-other"))),
                equalTo("Entity was \"some-content-as-payload\""));
    }

    @Test
    void shouldMatchByGenericEntityWithStringValues() {
        Response response = client.target(server.baseUri()).path("json").request().get();
        assertThat(response, hasEntity(
                new GenericType<Map<String, String>>() {
                },
                allOf(hasEntry("key1", "value1"), hasEntry("key2", "value2"))));
    }

    @Test
    void shouldMatchByContentLength() {
        Response response = client.target(server.baseUri()).path("text").request().get();
        assertThat(response, hasContentLength(equalTo(23)));
        assertThat(hasContentLength(equalTo(7)),
                isDescribedBy("has Content-Length <7>"));
        assertThat(mismatchOf(response, hasContentLength(equalTo(0))),
                equalTo("Content-Length was <23>"));
    }
}
