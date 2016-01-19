package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;
import java.net.URI;

class WithLocation extends TypeSafeMatcher<Response> {
    private final Matcher<? extends URI> uriMatcher;

    public WithLocation(Matcher<? extends URI> uriMatcher) {
        this.uriMatcher = uriMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with Location: ").appendDescriptionOf(uriMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return uriMatcher.matches(response.getLocation());
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        mismatchDescription.appendText("Location: ").appendValue(response.getLocation());
    }
}
