package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.NewCookie;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;

public class NewCookieMatchers {

    private NewCookieMatchers() {
        throw new AssertionError("Prevent instantiation");
    }

    public static Matcher<NewCookie> hasCookieName(final String name) {
        return new FeatureMatcher<NewCookie, String>(equalTo(name), "has cookie name", "cookie name") {
            @Override
            protected String featureValueOf(NewCookie actual) {
                return actual.getName();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieValue(final String value) {
        return new FeatureMatcher<NewCookie, String>(equalTo(value), "has cookie value", "cookie value") {
            @Override
            protected String featureValueOf(NewCookie actual) {
                return actual.getValue();
            }
        };
    }

    public static Matcher<NewCookie> hasCookiePath(final Matcher<String> pathMatcher) {
        return new FeatureMatcher<NewCookie, String>(pathMatcher, "has cookie path", "cookie path") {
            @Override
            protected String featureValueOf(NewCookie actual) {
                return actual.getPath();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieDomain(final String domain) {
        return hasCookieDomain(equalTo(domain));
    }

    public static Matcher<NewCookie> hasCookieDomain(final Matcher<String> domainMatcher) {
        return new FeatureMatcher<NewCookie, String>(domainMatcher, "has cookie domain", "cookie domain") {
            @Override
            protected String featureValueOf(NewCookie actual) {
                return actual.getDomain();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieVersion(final int version) {
        return hasCookieVersion(equalTo(version));
    }

    public static Matcher<NewCookie> hasCookieVersion(Matcher<Integer> versionMatcher) {
        return new FeatureMatcher<NewCookie, Integer>(versionMatcher, "has cookie version", "cookie version") {
            @Override
            protected Integer featureValueOf(NewCookie actual) {
                return actual.getVersion();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieComment(Matcher<String> commentMatcher) {
        return new FeatureMatcher<NewCookie, String>(commentMatcher, "has cookie comment", "cookie comment") {
            @Override
            protected String featureValueOf(NewCookie actual) {
                return actual.getComment();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieMaxAge(Matcher<Integer> ageMatcher) {
        return new FeatureMatcher<NewCookie, Integer>(ageMatcher, "has cookie max age", "cookie max age") {
            @Override
            protected Integer featureValueOf(NewCookie actual) {
                return actual.getMaxAge();
            }
        };
    }

    public static Matcher<NewCookie> hasCookieExpiryDate(Matcher<Date> expiryMatcher) {
        return new FeatureMatcher<NewCookie, Date>(expiryMatcher, "has cookie expiry date", "cookie expiry date") {
            @Override
            protected Date featureValueOf(NewCookie actual) {
                return actual.getExpiry();
            }
        };
    }

    public static Matcher<NewCookie> isCookieSecure() {
        return new TypeSafeMatcher<NewCookie>() {
            @Override
            protected boolean matchesSafely(NewCookie actual) {
                return actual.isSecure();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is cookie secure");
            }
        };
    }

    public static Matcher<NewCookie> isCookieHttpOnly() {
        return new TypeSafeMatcher<NewCookie>() {
            @Override
            protected boolean matchesSafely(NewCookie actual) {
                return actual.isHttpOnly();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is cookie http only");
            }
        };
    }

}
