package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;

class HasCookieMatcher extends TypeSafeMatcher<Response> {
    private final String cookieName;

    public HasCookieMatcher(String cookieName) {
        this.cookieName = cookieName;
    }

    public void describeTo(Description description) {
        description.appendText("has cookie ").appendValue(cookieName);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        Map<String, NewCookie> cookies = response.getCookies();
        return cookies != null ? cookies.containsKey(cookieName) : false;
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        Map<String, NewCookie> cookies = response.getCookies();
        mismatchDescription.appendText("was ").appendValue(cookies);
    }
}
