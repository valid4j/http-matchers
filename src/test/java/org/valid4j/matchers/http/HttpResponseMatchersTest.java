package org.valid4j.matchers.http;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.valid4j.matchers.http.helpers.HttpStatus;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.StatusType;
import java.net.URI;
import java.util.Date;
import java.util.Locale;

import static jakarta.ws.rs.core.MediaType.*;
import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static org.hamcrest.Matchers.*;
import static org.valid4j.matchers.http.HttpResponseMatchers.*;
import static org.valid4j.matchers.http.NewCookieMatchers.withCookieValue;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

class HttpResponseMatchersTest {
    private static final MediaType TEXT_WILDCARD = new MediaType("text", MEDIA_TYPE_WILDCARD);

    @Test
    void shouldMatchOkResponse() {
        Response ok = Response.ok().build();
        assertThat(ok, isResponseOk());
        assertThat(isResponseOk(), isDescribedBy("is response ok"));
    }

    @Test
    void shouldMatchByStatusCode() {
        assertThat(response(BAD_REQUEST), hasStatusCode(400));
        assertThat(hasStatusCode(400),
                isDescribedBy("has status code <400>"));
        assertThat(mismatchOf(response(UNAUTHORIZED), hasStatusCode(400)),
                equalTo("status code was <401>"));
    }

    @Test
    void shouldMatchByStatusCodeMatcher() {
        Response response = response(BAD_GATEWAY);
        assertThat(response, hasStatusCode(oneOf(502, 503)));
        assertThat(hasStatusCode(oneOf(502, 503)),
                isDescribedBy("has status code one of {<502>, <503>}"));
        assertThat(mismatchOf(
                        response(INTERNAL_SERVER_ERROR),
                        hasStatusCode(oneOf(502, 503))),
                equalTo("status code was <500>"));
    }

    @Test
    void shouldMatchByStatusCodeOfFamily() {
        Response response = response(UNAUTHORIZED);
        assertThat(response, hasStatusCode(ofFamily(CLIENT_ERROR)));
        assertThat(response, hasStatusCode(CLIENT_ERROR));
        assertThat(hasStatusCode(ofFamily(CLIENT_ERROR)),
                isDescribedBy("has status code of family <CLIENT_ERROR>"));
        assertThat(hasStatusCode(CLIENT_ERROR),
                isDescribedBy("has status code of family <CLIENT_ERROR>"));
        assertThat(mismatchOf(response(BAD_GATEWAY), hasStatusCode(CLIENT_ERROR)),
                equalTo("status code was <502>"));
    }

    @Test
    void shouldMatchByStatusCodeOfStatus() {
        Response response = response(Status.OK);
        HttpStatus okWithIgnoredReason = new HttpStatus(200, "Ignored Reason");
        assertThat(response, hasStatusCodeOf(okWithIgnoredReason));
        assertThat(hasStatusCodeOf(okWithIgnoredReason),
                isDescribedBy("has status code <200>"));
        assertThat(mismatchOf(response(BAD_REQUEST), hasStatusCodeOf(okWithIgnoredReason)),
                equalTo("status code was <400>"));
    }

    @Test
    void shouldMatchByStatusCodeAndReason() {
        Response response = response(ACCEPTED);
        assertThat(response, hasStatus(ACCEPTED));
        assertThat(response, hasStatus(202, "Accepted"));
        assertThat(hasStatus(ACCEPTED),
                isDescribedBy("has status <202 - Accepted>"));
        assertThat(mismatchOf(response(BAD_GATEWAY), hasStatus(ACCEPTED)),
                equalTo("status was <502 - Bad Gateway>"));
    }

    @Test
    void shouldNotMatchByStatusCodeAndReason() {
        Response response = response(OK);
        assertThat(response, not(hasStatus(200, "Mismatched Reason")));
        assertThat(response, not(hasStatus(new HttpStatus(200, "Mismatched Reason"))));
        assertThat(response, not(hasStatus(201, "OK")));
        assertThat(response, not(hasStatus(new HttpStatus(201, "OK"))));
        assertThat(response, hasStatus(200, "OK"));
        assertThat(response, hasStatus(new HttpStatus(200, "OK")));
    }

    @Test
    void shouldMatchByHeader() {
        Response response = Response.ok().header("some-key", "some-value").build();
        assertThat(response, hasHeader("some-key"));
        assertThat(response, not(hasHeader("some-other-key")));
        assertThat(hasHeader("some-key"),
                isDescribedBy("has header \"some-key\""));
        assertThat(mismatchOf(response, hasHeader("some-other-key")),
                equalTo("header \"some-other-key\" was missing"));
    }

    @Test
    void shouldMatchByHeaderWithValue() {
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
                equalTo("header \"some-key\" was \"some-value\""));
    }

    @Test
    void shouldMatchByHeaderWithValues() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        headers.add("some-key", "some-value");
        headers.add("some-key", "some-value2");
        headers.add("some-key", "some-value3");
        headers.add("Cache-Control", "no-cache");
        headers.add("Cache-Control", "no-store");
        headers.add("Age", 42);
        Response response = Response.ok().replaceAll(headers).build();
        assertThat(response, hasHeaderValues("some-key",
                hasItem(equalTo("some-value"))));
        assertThat(response, hasHeaderValues("some-key",
                hasItem(equalTo("some-value2"))));
        assertThat(response, hasHeaderValues("some-key",
                hasItem(equalTo("some-value3"))));
        assertThat(response, hasHeaderValues("Cache-Control",
                contains(equalTo("no-cache"), equalTo("no-store"))));
        assertThat(response, hasHeaderValues("Age",
                hasItem(equalTo(42))));
        assertThat(response, not(hasHeaderValues("some-key",
                hasItem(equalTo("some-other-value")))));
        assertThat(response, not(hasHeaderValues("Age",
                hasItem(equalTo(53)))));
        assertThat(response, not(hasHeaderValues("some-key3",
                hasItem(equalTo(0)))));
        assertThat(hasHeaderValues("some-key", hasItem(equalTo("some-value"))),
                isDescribedBy("has header \"some-key\" with a collection containing \"some-value\""));
        assertThat(mismatchOf(response, hasHeaderValues("some-other-key", hasItem(equalTo("some-value")))),
                equalTo("header \"some-other-key\" was missing"));
        assertThat(mismatchOf(response, hasHeaderValues("some-key", hasItem(equalTo("some-other")))),
                equalTo("header \"some-key\" was \"some-value\",\"some-value2\",\"some-value3\""));
    }

    @Test
    void shouldMatchByCookie() {
        Response response = Response.ok().cookie(
                new NewCookie.Builder("cookie1").value("my-value").build(),
            new NewCookie.Builder("cookie1").value("my-other-value").build(),
            new NewCookie.Builder("cookie1").value("my-yet-another-value").build(),
            new NewCookie.Builder("cookie2").value("my-value-2").build()).build();
        assertThat(response, hasCookie("cookie1"));
        assertThat(response, hasCookie("cookie2"));
        assertThat(response, not(hasCookie("cookie3")));
        assertThat(hasCookie("cookie1"),
                isDescribedBy("has cookie \"cookie1\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchByCookieWithValue() {
        Response response = Response.ok().cookie(
                new NewCookie.Builder("cookie1").value("my-value").build(),
                new NewCookie.Builder("cookie2").value("my-other-value").build()).build();
        assertThat(response, hasCookie("cookie1", withCookieValue("my-value")));
        assertThat(response, not(hasCookie("cookie1", withCookieValue("OHASONEFN"))));
        assertThat(response, not(hasCookie("cookie3", withCookieValue("my-value"))));
        assertThat(hasCookie("cookie1", withCookieValue("my-value")),
                isDescribedBy("has cookie \"cookie1\" with cookie value \"my-value\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchByContentType() {
        Response response = Response.ok("content", TEXT_PLAIN_TYPE).build();
        assertThat(response, hasContentType(TEXT_PLAIN_TYPE));
        assertThat(response, hasContentType(TEXT_PLAIN));
        assertThat(response, hasContentType(compatibleWith(TEXT_WILDCARD)));
        assertThat(response, not(hasContentType(APPLICATION_JSON_TYPE)));
        assertThat(response, not(hasContentType(APPLICATION_JSON)));
        assertThat(hasContentType(TEXT_PLAIN_TYPE),
                isDescribedBy("has Content-Type <text/plain>"));
        Response jsonResponse = Response.ok("content", APPLICATION_JSON_TYPE).build();
        assertThat(mismatchOf(jsonResponse, hasContentType(TEXT_PLAIN_TYPE)),
                equalTo("Content-Type was <application/json>"));
    }

    @Test
    void shouldMatchByContentEncoding() {
        Response response = Response.ok("content").encoding("gzip").build();
        assertThat(response, hasContentEncoding("gzip"));
        assertThat(response, not(hasContentEncoding("deflate")));
        assertThat(hasContentEncoding("gzip"),
                isDescribedBy("has header \"Content-Encoding\" with value \"gzip\""));
        assertThat(mismatchOf(response, hasContentEncoding("deflate")),
                equalTo("header \"Content-Encoding\" was \"gzip\""));
    }

    @Test
    void shouldMatchByContentLocation() {
        URI location = URI.create("http://example.com/loco");
        URI other = URI.create("http://example.com/poco");
        Response response = Response.ok().contentLocation(location).build();
        assertThat(response, hasContentLocation(location));
        assertThat(response, not(hasContentLocation(other)));
        assertThat(hasContentLocation(location),
                isDescribedBy("has header \"Content-Location\" with value <http://example.com/loco>"));
        assertThat(mismatchOf(response, hasContentLocation(other)),
                equalTo("header \"Content-Location\" was <http://example.com/loco>"));
    }

    @Test
    void shouldMatchByLanguage() {
        Response response = Response.ok("message").language(Locale.UK).build();
        assertThat(response, ofLanguage("en-GB"));
        assertThat(response, ofLanguage(Locale.UK));
        assertThat(response, ofLanguage(equalTo(Locale.UK)));
        assertThat(response, not(ofLanguage("en-US")));
        assertThat(response, not(ofLanguage(Locale.US)));
        assertThat(response, not(ofLanguage(equalTo(Locale.US))));
        assertThat(ofLanguage("en-CA"),
                isDescribedBy("of Content-Language <en_CA>"));
        assertThat(mismatchOf(response, ofLanguage("en-CA")),
                equalTo("Content-Language was <en_GB>"));
    }

    @Test
    void shouldMatchByLastModified() {
        final long lastModMillis = 1452960194707L;
        Date lastModDate = new Date(lastModMillis);
        Response response = Response.ok().lastModified(lastModDate).build();
        assertThat(response, hasLastModifiedDate(equalTo(lastModDate)));
        assertThat(hasLastModifiedDate(equalTo(lastModDate)),
                isDescribedBy("has Last-Modified <Sat Jan 16 17:03:14 CET 2016>"));
        assertThat(mismatchOf(response, hasLastModifiedDate(equalTo(new Date(0L)))),
                equalTo("Last-Modified was <Sat Jan 16 17:03:14 CET 2016>"));
    }

    void shouldMatchByHasLink() {
        // TODO:
    }

    void shouldMatchByLinkByRelation() {
        // TODO:
    }

    @Test
    void shouldMatchByLocation() {
        URI location = URI.create("http://example.com/loco");
        Response response = Response.created(location).build();
        assertThat(response, hasLocation(location));
        assertThat(response, hasLocation(equalTo(location)));
        assertThat(hasLocation(equalTo(location)),
                isDescribedBy("has Location <http://example.com/loco>"));
        URI mismatched = URI.create("http://mismatched.com");
        assertThat(mismatchOf(response, hasLocation(equalTo(mismatched))),
                equalTo("Location was <http://example.com/loco>"));
    }

    @Test
    void shouldMatchByHasEntity() {
        Response response = Response.ok("entity").build();
        assertThat(response, hasEntity());
        assertThat(hasEntity(), isDescribedBy("has entity"));
        assertThat(mismatchOf(response, hasEntity()),
                equalTo("has entity"));
    }

    @Test
    void shouldMatchByHasNoEntity() {
        Response response = Response.noContent().build();
        assertThat(response, not(hasEntity()));
        assertThat(mismatchOf(response, hasEntity()),
                equalTo("has no entity"));
    }

    private static Response response(StatusType status) {
        return Response.status(status).build();
    }
}
