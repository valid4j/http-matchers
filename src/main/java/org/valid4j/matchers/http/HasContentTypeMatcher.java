package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

class HasContentTypeMatcher extends TypeSafeMatcher<Response> {
    private final MediaType mediaType;

    public HasContentTypeMatcher(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public void describeTo(Description description) {
        description.appendText("has content type ").appendValue(mediaType);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        MediaType actual = response.getMediaType();
        return Objects.equals(actual, mediaType);
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        mismatchDescription.appendText("was content type ").appendValue(response.getMediaType());
    }
}