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
        assertThat(cookie, hasCookieName("cookie-name"));
        assertThat(cookie, not(hasCookieName("another-name")));
        assertThat(hasCookieName("cookie-name"),
                isDescribedBy("has cookie name \"cookie-name\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnValue() {
        assertThat(cookie, hasCookieValue("cookie-value"));
        assertThat(cookie, not(hasCookieValue("another-value")));
        assertThat(hasCookieValue("cookie-value"),
                isDescribedBy("has cookie value \"cookie-value\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnPath() {
        assertThat(cookie, hasCookiePath(startsWith("/")));
        assertThat(cookie, not(hasCookiePath(endsWith("/"))));
        assertThat(hasCookiePath(startsWith("/")),
                isDescribedBy("has cookie path a string starting with \"/\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnDomain() {
        assertThat(cookie, hasCookieDomain("example.org"));
        assertThat(cookie, hasCookieDomain(endsWith(".org")));
        assertThat(cookie, not(hasCookieDomain(endsWith(".com"))));
        assertThat(hasCookieDomain(endsWith(".org")),
                isDescribedBy("has cookie domain a string ending with \".org\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnVersion() {
        assertThat(cookie, hasCookieVersion(VERSION));
        assertThat(cookie, hasCookieVersion(greaterThan(2)));
        assertThat(cookie, not(hasCookieVersion(lessThan(2))));
        assertThat(hasCookieVersion(VERSION),
                isDescribedBy("has cookie version <3>"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnComment() {
        assertThat(cookie, hasCookieComment(containsString("is a comment")));
        assertThat(cookie, not(hasCookieComment(equalTo("hi there"))));
        assertThat(hasCookieComment(startsWith("This")),
                isDescribedBy("has cookie comment a string starting with \"This\""));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnMaxAge() {
        assertThat(cookie, hasCookieMaxAge(lessThan(300)));
        assertThat(cookie, not(hasCookieMaxAge(lessThan(200))));
        assertThat(hasCookieMaxAge(lessThan(200)),
                isDescribedBy("has cookie max age a value less than <200>"));
        // TODO: Verify mismatch description
    }

    @Test
    public void shouldMatchOnExpiryDate() {
        assertThat(cookie, hasCookieExpiryDate(equalTo(EXPIRY_DATE)));

        Date expiryDate = new Date(1461852318000L);
        assertThat(cookie, not(hasCookieExpiryDate(equalTo(expiryDate))));
        assertThat(hasCookieExpiryDate(equalTo(expiryDate)),
                isDescribedBy("has cookie expiry date <Thu Apr 28 16:05:18 CEST 2016>"));
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
