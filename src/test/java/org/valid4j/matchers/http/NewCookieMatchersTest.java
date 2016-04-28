package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import java.util.Date;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.NewCookieMatchers.*;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class NewCookieMatchersTest {

    private static final int VERSION = 3;
    private static final int AGE = 256;
    private static final Date EXPIRY_DATE = new Date();
    private static final boolean IS_SECURE = true;
    private static final boolean HTTP_ONLY = false;

    private final NewCookie cookie = new NewCookie(
            "cookie-name",
            "cookie-value",
            "/path",
            "example.org",
            VERSION,
            "This is a comment",
            AGE,
            EXPIRY_DATE,
            IS_SECURE,
            HTTP_ONLY);

    @Test
    public void shouldMatchOnName() {
        assertThat(cookie, withCookieName("cookie-name"));
        assertThat(cookie, not(withCookieName("another-name")));
        assertThat(withCookieName("cookie-name"),
                isDescribedBy("with cookie name \"cookie-name\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnValue() {
        assertThat(cookie, withCookieValue("cookie-value"));
        assertThat(cookie, not(withCookieValue("another-value")));
        assertThat(withCookieValue("cookie-value"),
                isDescribedBy("with cookie value \"cookie-value\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnPath() {
        assertThat(cookie, withCookiePath(startsWith("/")));
        assertThat(cookie, not(withCookiePath(endsWith("/"))));
        assertThat(withCookiePath(startsWith("/")),
                isDescribedBy("with cookie path a string starting with \"/\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnDomain() {
        assertThat(cookie, withCookieDomain("example.org"));
        assertThat(cookie, withCookieDomain(endsWith(".org")));
        assertThat(cookie, not(withCookieDomain(endsWith(".com"))));
        assertThat(withCookieDomain(endsWith(".org")),
                isDescribedBy("with cookie domain a string ending with \".org\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnVersion() {
        assertThat(cookie, withCookieVersion(VERSION));
        assertThat(cookie, withCookieVersion(greaterThan(2)));
        assertThat(cookie, not(withCookieVersion(lessThan(2))));
        assertThat(withCookieVersion(VERSION),
                isDescribedBy("with cookie version <3>"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnComment() {
        assertThat(cookie, withCookieComment(containsString("is a comment")));
        assertThat(cookie, not(withCookieComment(equalTo("hi there"))));
        assertThat(withCookieComment(startsWith("This")),
                isDescribedBy("with cookie comment a string starting with \"This\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnMaxAge() {
        assertThat(cookie, withCookieMaxAge(lessThan(300)));
        assertThat(cookie, not(withCookieMaxAge(lessThan(200))));
        assertThat(withCookieMaxAge(lessThan(200)),
                isDescribedBy("with cookie max age a value less than <200>"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnExpiryDate() {
        assertThat(cookie, withCookieExpiryDate(equalTo(EXPIRY_DATE)));

        Date expiryDate = new Date(1461852318000L);
        assertThat(cookie, not(withCookieExpiryDate(equalTo(expiryDate))));
        assertThat(withCookieExpiryDate(equalTo(expiryDate)),
                isDescribedBy("with cookie expiry date <Thu Apr 28 16:05:18 CEST 2016>"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnSecureFlag() {
        assertThat(cookie, isCookieSecure());

        NewCookie nonSecureCookie = new NewCookie("name", "value", "path", "domain", "comment", AGE, false);
        assertThat(nonSecureCookie, not(isCookieSecure()));

        assertThat(isCookieSecure(),
                isDescribedBy("is cookie secure"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnHttpOnlyFlag() {
        assertThat(cookie, not(isCookieHttpOnly()));

        NewCookie httpOnlyCookie = new NewCookie("name", "value", "path", "domain", "comment", AGE, false, true);
        assertThat(httpOnlyCookie, isCookieHttpOnly());

        assertThat(isCookieHttpOnly(),
                isDescribedBy("is cookie http only"));
        // TODO: Verify mismatch description
    }

}
