package org.valid4j.matchers.http;


import jakarta.ws.rs.core.NewCookie;
import java.util.Date;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import static org.valid4j.matchers.http.NewCookieMatchers.*;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

class NewCookieMatchersTest {

    private static final int VERSION = 3;
    private static final int AGE = 256;
    private static final Date EXPIRY_DATE = new Date();
    private static final boolean IS_SECURE = true;
    private static final boolean HTTP_ONLY = false;

    private final NewCookie cookie = new NewCookie.Builder("cookie-name")
        .value("cookie-value")
        .path("/path")
        .domain("example.org")
        .version(VERSION)
        .comment("This is a comment")
        .maxAge(AGE)
        .expiry(EXPIRY_DATE)
        .secure(IS_SECURE)
        .httpOnly(HTTP_ONLY).build();

    @Test
    void shouldMatchOnName() {
        assertThat(cookie, withCookieName("cookie-name"));
        assertThat(cookie, not(withCookieName("another-name")));
        assertThat(withCookieName("cookie-name"),
                isDescribedBy("with cookie name \"cookie-name\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnValue() {
        assertThat(cookie, withCookieValue("cookie-value"));
        assertThat(cookie, not(withCookieValue("another-value")));
        assertThat(withCookieValue("cookie-value"),
                isDescribedBy("with cookie value \"cookie-value\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnPath() {
        assertThat(cookie, withCookiePath(startsWith("/")));
        assertThat(cookie, not(withCookiePath(endsWith("/"))));
        assertThat(withCookiePath(startsWith("/")),
                isDescribedBy("with cookie path a string starting with \"/\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnDomain() {
        assertThat(cookie, withCookieDomain("example.org"));
        assertThat(cookie, withCookieDomain(endsWith(".org")));
        assertThat(cookie, not(withCookieDomain(endsWith(".com"))));
        assertThat(withCookieDomain(endsWith(".org")),
                isDescribedBy("with cookie domain a string ending with \".org\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnVersion() {
        assertThat(cookie, withCookieVersion(VERSION));
        assertThat(cookie, withCookieVersion(greaterThan(2)));
        assertThat(cookie, not(withCookieVersion(lessThan(2))));
        assertThat(withCookieVersion(VERSION),
                isDescribedBy("with cookie version <3>"));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnComment() {
        assertThat(cookie, withCookieComment(containsString("is a comment")));
        assertThat(cookie, not(withCookieComment(equalTo("hi there"))));
        assertThat(withCookieComment(startsWith("This")),
                isDescribedBy("with cookie comment a string starting with \"This\""));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnMaxAge() {
        assertThat(cookie, withCookieMaxAge(lessThan(300)));
        assertThat(cookie, not(withCookieMaxAge(lessThan(200))));
        assertThat(withCookieMaxAge(lessThan(200)),
                isDescribedBy("with cookie max age a value less than <200>"));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnExpiryDate() {
        assertThat(cookie, withCookieExpiryDate(equalTo(EXPIRY_DATE)));

        Date expiryDate = new Date(1461852318000L);
        assertThat(cookie, not(withCookieExpiryDate(equalTo(expiryDate))));
        assertThat(withCookieExpiryDate(equalTo(expiryDate)),
                isDescribedBy("with cookie expiry date <Thu Apr 28 16:05:18 CEST 2016>"));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnSecureFlag() {
        assertThat(cookie, isCookieSecure());

        NewCookie nonSecureCookie = new NewCookie.Builder("name")
            .value("value")
            .path("path")
            .domain("domain")
            .comment( "comment")
            .maxAge(AGE)
            .secure(false).build();
        assertThat(nonSecureCookie, not(isCookieSecure()));

        assertThat(isCookieSecure(),
                isDescribedBy("is cookie secure"));
        // TODO: Verify mismatch description
    }

    @Test
    void shouldMatchOnHttpOnlyFlag() {
        assertThat(cookie, not(isCookieHttpOnly()));

        NewCookie httpOnlyCookie = new NewCookie.Builder("name")
            .value("value")
            .path("path")
            .domain("domain")
            .comment( "comment")
            .maxAge(AGE)
            .secure(false)
            .httpOnly(true).build();
        assertThat(httpOnlyCookie, isCookieHttpOnly());

        assertThat(isCookieHttpOnly(),
                isDescribedBy("is cookie http only"));
        // TODO: Verify mismatch description
    }

}
