package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.*;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class HttpResponseMatchersTest {

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

    // TODO:
    public void shouldMatchByHeader() {
    }

    public void shouldMatchByCookie() {
    }

    public void shouldMatchByHasEntity() {
    }

    public void shouldMatchByEntity() {
    }

    public void shouldMatchByLanguage() {
        // Locale
    }

    public void shouldMatchByLastModified() {
    }

    public void shouldMatchByContentLength() {
    }

    public void shouldMatchByHasLink() {
    }

    public void shouldMatchByLinkByRelation() {
    }

    public void shouldMatchByLocation() {
    }

    @Test
    public void shouldMatchByMediaType() {
        Response response = Response.ok("content", TEXT_PLAIN_TYPE).build();
        assertThat(response, hasContentType(TEXT_PLAIN_TYPE));
        assertThat(response, hasContentType(TEXT_PLAIN));
        assertThat(response, not(hasContentType(APPLICATION_JSON_TYPE)));
        assertThat(response, not(hasContentType(APPLICATION_JSON)));
        assertThat(hasContentType(TEXT_PLAIN_TYPE),
                isDescribedBy("has content type <text/plain>"));
        Response jsonResponse = Response.ok("content", APPLICATION_JSON_TYPE).build();
        assertThat(mismatchOf(jsonResponse, hasContentType(TEXT_PLAIN_TYPE)),
                equalTo("was content type <application/json>"));
    }

    private static Response response(StatusType status) {
        return Response.status(status).build();
    }
}
