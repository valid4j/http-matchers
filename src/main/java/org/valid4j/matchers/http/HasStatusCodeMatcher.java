package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

class HasStatusCodeMatcher extends TypeSafeMatcher<Response> {
    private final Matcher<? super Integer> statusCodeMatcher;

    public HasStatusCodeMatcher(Matcher<? super Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    public void describeTo(Description description) {
        description
                .appendText("has status code ")
                .appendDescriptionOf(statusCodeMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return statusCodeMatcher.matches(response.getStatus());
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        mismatchDescription
                .appendText("was status code ")
                .appendValue(response.getStatus());
    }
}
