package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

class HasContentTypeMatcher extends TypeSafeMatcher<Response> {
    private final Matcher<? extends MediaType> mediaTypeMatcher;

    public HasContentTypeMatcher(Matcher<? extends MediaType> mediaTypeMatcher) {
        this.mediaTypeMatcher = mediaTypeMatcher;
    }

    public void describeTo(Description description) {
        description.appendText("has content type ").appendDescriptionOf(mediaTypeMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        MediaType actual = response.getMediaType();
        return mediaTypeMatcher.matches(actual);
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        mismatchDescription.appendText("was content type ").appendValue(response.getMediaType());
    }
}