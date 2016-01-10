package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import java.util.Date;
import java.util.Locale;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.*;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class HttpResponseMatchersTest {
    private static final MediaType TEXT_WILDCARD = new MediaType("text", MEDIA_TYPE_WILDCARD);

    @Test
    public void shouldMatchOkResponse() {
        Response ok = Response.ok().build();
        assertThat(ok, isResponseOk());
        assertThat(isResponseOk(), isDescribedBy("is response ok"));
    }

    @Test
    public void shouldMatchByStatusCode() {
        assertThat(response(BAD_REQUEST), hasStatusCode(400));
        assertThat(hasStatusCode(400),
                isDescribedBy("has status code <400>"));
        assertThat(mismatchOf(response(UNAUTHORIZED), hasStatusCode(400)),
                equalTo("was status code <401>"));
    }

    @Test
    public void shouldMatchByStatusCodeMatcher() {
        Response response = response(BAD_GATEWAY);
        assertThat(response, hasStatusCode(isOneOf(502, 503)));
        assertThat(hasStatusCode(isOneOf(502, 503)),
                isDescribedBy("has status code one of {<502>, <503>}"));
        assertThat(mismatchOf(
                        response(INTERNAL_SERVER_ERROR),
                        hasStatusCode(isOneOf(502, 503))),
                equalTo("was status code <500>"));
    }

    @Test
    public void shouldMatchByStatusCodeOfFamily() {
        Response response = response(UNAUTHORIZED);
        assertThat(response, hasStatusCode(ofFamily(CLIENT_ERROR)));
        assertThat(response, hasStatusCode(CLIENT_ERROR));
        assertThat(hasStatusCode(ofFamily(CLIENT_ERROR)),
                isDescribedBy("has status code of family <CLIENT_ERROR>"));
        assertThat(hasStatusCode(CLIENT_ERROR),
                isDescribedBy("has status code of family <CLIENT_ERROR>"));
        assertThat(mismatchOf(response(BAD_GATEWAY), hasStatusCode(CLIENT_ERROR)),
                equalTo("was status code <502>"));
    }

    @Test
    public void shouldMatchByStatusCodeOfStatus() {
        Response response = response(Status.OK);
        HttpStatus okWithIgnoredReason = new HttpStatus(200, "Ignored Reason");
        assertThat(response, hasStatusCodeOf(okWithIgnoredReason));
        assertThat(hasStatusCodeOf(okWithIgnoredReason),
                isDescribedBy("has status code <200>"));
        assertThat(mismatchOf(response(BAD_REQUEST), hasStatusCodeOf(okWithIgnoredReason)),
                equalTo("was status code <400>"));
    }

    @Test
    public void shouldMatchByStatusCodeAndReason() {
        Response response = response(ACCEPTED);
        assertThat(response, hasStatus(ACCEPTED));
        assertThat(hasStatus(ACCEPTED),
                isDescribedBy("has status <202 - Accepted>"));
        assertThat(mismatchOf(response(BAD_GATEWAY), hasStatus(ACCEPTED)),
                equalTo("was status <502 - Bad Gateway>"));
    }

    @Test
    public void shouldNotMatchByStatusCodeAndReason() {
        Response response = response(OK);
        assertThat(response, not(hasStatus(new HttpStatus(200, "Mismatched Reason"))));
        assertThat(response, not(hasStatus(new HttpStatus(201, "OK"))));
        assertThat(response, hasStatus(new HttpStatus(200, "OK")));
    }

    @Test
    public void shouldMatchByContentType() {
        Response response = Response.ok("content", TEXT_PLAIN_TYPE).build();
        assertThat(response, hasContentType(TEXT_PLAIN_TYPE));
        assertThat(response, hasContentType(TEXT_PLAIN));
        assertThat(response, hasContentType(isCompatibleWith(TEXT_WILDCARD)));
        assertThat(response, not(hasContentType(APPLICATION_JSON_TYPE)));
        assertThat(response, not(hasContentType(APPLICATION_JSON)));
        assertThat(hasContentType(TEXT_PLAIN_TYPE),
                isDescribedBy("has content type <text/plain>"));
        Response jsonResponse = Response.ok("content", APPLICATION_JSON_TYPE).build();
        assertThat(mismatchOf(jsonResponse, hasContentType(TEXT_PLAIN_TYPE)),
                equalTo("was content type <application/json>"));
    }

    @Test
    public void shouldMatchByHeader() {
        Response response = Response.ok().header("some-key", "some-value").build();
        assertThat(response, hasHeader("some-key"));
        assertThat(response, not(hasHeader("some-other-key")));
        assertThat(hasHeader("some-key"),
                isDescribedBy("has header \"some-key\""));
        assertThat(mismatchOf(response, hasHeader("some-other-key")),
                equalTo("header \"some-other-key\" was missing"));
    }

    @Test
    public void shouldMatchByHeaderWithValue() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        headers.add("some-key", "some-value");
        headers.add("some-key", "some-value2");
        headers.add("some-key", "some-value3");
        headers.add("some-key2", 42);
        Response response = Response.ok().replaceAll(headers).build();
        assertThat(response, hasHeader("some-key", equalTo("some-value")));
        assertThat(response, hasHeader("some-key2", equalTo(42)));
        assertThat(response, not(hasHeader("some-key", equalTo("some-value2"))));
        assertThat(response, not(hasHeader("some-key2", hasItem(equalTo(53)))));
        assertThat(response, not(hasHeader("some-key3", hasItem(equalTo(0)))));
        assertThat(hasHeader("some-key", equalTo("some-value")),
                isDescribedBy("has header \"some-key\" with value \"some-value\""));
        assertThat(mismatchOf(response, hasHeader("some-other-key", equalTo("some-value"))),
                equalTo("header \"some-other-key\" was missing"));
        assertThat(mismatchOf(response, hasHeader("some-key", equalTo("some-other-value"))),
                equalTo("header \"some-key\" contained \"some-value\""));
    }

    @Test
    public void shouldMatchByHeaderWithValues() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        headers.add("some-key", "some-value");
        headers.add("some-key", "some-value2");
        headers.add("some-key", "some-value3");
        headers.add("some-key2", 42);
        Response response = Response.ok().replaceAll(headers).build();
        assertThat(response, hasHeaderValues("some-key", hasItem(equalTo("some-value"))));
        assertThat(response, hasHeaderValues("some-key", hasItem(equalTo("some-value2"))));
        assertThat(response, hasHeaderValues("some-key", hasItem(equalTo("some-value3"))));
        assertThat(response, hasHeaderValues("some-key2", hasItem(equalTo(42))));
        assertThat(response, not(hasHeaderValues("some-key", hasItem(equalTo("some-other-value")))));
        assertThat(response, not(hasHeaderValues("some-key2", hasItem(equalTo(53)))));
        assertThat(response, not(hasHeaderValues("some-key3", hasItem(equalTo(0)))));
        assertThat(hasHeaderValues("some-key", hasItem(equalTo("some-value"))),
                isDescribedBy("has header \"some-key\" with a collection containing \"some-value\""));
        assertThat(mismatchOf(response, hasHeaderValues("some-other-key", hasItem(equalTo("some-value")))),
                equalTo("header \"some-other-key\" was missing"));
    }

    @Test
    public void shouldMatchByCookie() {
        Response response = Response.ok().cookie(
                new NewCookie("cookie1", "my-value"),
                new NewCookie("cookie1", "my-other-value"),
                new NewCookie("cookie1", "my-yet-another-value"),
                new NewCookie("cookie2", "my-value-2")).build();
        assertThat(response, hasCookie("cookie1"));
        assertThat(response, hasCookie("cookie2"));
        assertThat(response, not(hasCookie("cookie3")));
    }

    public void shouldMatchByCookieWithValue() {

    }

    @Test
    public void shouldMatchByHasEntity() {
        Response response = Response.ok("entity").build();
        assertThat(response, hasEntity());
        assertThat(hasEntity(), isDescribedBy("has entity"));
        assertThat(mismatchOf(response, hasEntity()),
                equalTo("has entity"));
    }

    @Test
    public void shouldMatchByHasNoEntity() {
        Response response = Response.noContent().build();
        assertThat(response, not(hasEntity()));
        assertThat(mismatchOf(response, hasEntity()),
                equalTo("has no entity"));
    }

    @Test
    public void shouldMatchByLanguage() {
        Response response = Response.ok("message").language(Locale.UK).build();
        assertThat(response, ofLanguage("en-GB"));
        assertThat(response, ofLanguage(Locale.UK));
        assertThat(response, ofLanguage(equalTo(Locale.UK)));
        assertThat(response, not(ofLanguage("en-US")));
        assertThat(response, not(ofLanguage(Locale.US)));
        assertThat(response, not(ofLanguage(equalTo(Locale.US))));
        assertThat(ofLanguage("en-CA"),
                isDescribedBy("of language <en_CA>"));
        assertThat(mismatchOf(response, ofLanguage("en-CA")),
                equalTo("was language \"en-GB\""));
    }

    @Test
    public void shouldMatchByLastModified() {
        Date lastModDate = new Date();
        Response response = Response.ok().lastModified(lastModDate).build();
        assertThat(response, withLastModifiedDate(equalTo(lastModDate)));

    }

    public void shouldMatchByContentLength() {
    }

    public void shouldMatchByHasLink() {
    }

    public void shouldMatchByLinkByRelation() {
    }

    public void shouldMatchByLocation() {
    }

    private static Response response(StatusType status) {
        return Response.status(status).build();
    }
}
