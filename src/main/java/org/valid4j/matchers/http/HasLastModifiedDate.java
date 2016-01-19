package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;
import java.util.Date;

class HasLastModifiedDate extends TypeSafeMatcher<Response> {
    private final Matcher<? extends Date> lastModDateMatcher;

    public HasLastModifiedDate(Matcher<? extends Date> lastModDateMatcher) {
        this.lastModDateMatcher = lastModDateMatcher;
    }

    public void describeTo(Description description) {
        description.appendText("has last modified date ").appendDescriptionOf(lastModDateMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return lastModDateMatcher.matches(response.getLastModified());
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        Date date = response.getLastModified();
        mismatchDescription.appendText("last modified date was ").appendValue(date);
    }
}
