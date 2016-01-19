package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

class HasContentLengthMatcher extends TypeSafeMatcher<Response> {
    private final Matcher<? extends Integer> lengthMatcher;

    public HasContentLengthMatcher(Matcher<? extends Integer> lengthMatcher) {
        this.lengthMatcher = lengthMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has Content-Length ").appendDescriptionOf(lengthMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return lengthMatcher.matches(response.getLength());
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        int length = response.getLength();
        mismatchDescription.appendText("Content-Length ").appendValue(length);
    }
}
