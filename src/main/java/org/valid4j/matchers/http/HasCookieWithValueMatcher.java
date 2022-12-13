package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.util.Map;

class HasCookieWithValueMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final String cookieName;
    private final Matcher<NewCookie> cookieMatcher;

    public HasCookieWithValueMatcher(String cookieName, Matcher<NewCookie> cookieMatcher) {
        this.cookieName = cookieName;
        this.cookieMatcher = cookieMatcher;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        Map<String, NewCookie> cookies = response.getCookies();
        NewCookie cookie = cookies.get(cookieName);
        if (cookie != null) {
            if (cookieMatcher.matches(cookie)) {
                return true;
            } else {
                mismatchDescription
                        .appendText("cookie ")
                        .appendValue(cookieName)
                        .appendText(" ")
                        .appendValue(cookie);
                return false;
            }
        } else {
            mismatchDescription
                    .appendText("cookie ")
                    .appendValue(cookieName)
                    .appendText(" not found");
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("has cookie ")
                .appendValue(cookieName)
                .appendText(" ")
                .appendDescriptionOf(cookieMatcher);
    }
}
