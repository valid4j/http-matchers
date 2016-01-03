package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.MediaType;

class MediaTypeCompatibleWithMatcher extends TypeSafeMatcher<MediaType> {
    private final MediaType compatibleMediaType;

    public MediaTypeCompatibleWithMatcher(MediaType compatibleMediaType) {
        this.compatibleMediaType = compatibleMediaType;
    }

    public void describeTo(Description description) {
        description.appendText("is compatible with ").appendValue(compatibleMediaType);
    }

    @Override
    protected boolean matchesSafely(MediaType mediaType) {
        return mediaType.isCompatible(compatibleMediaType);
    }

    @Override
    protected void describeMismatchSafely(MediaType mediaType, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(mediaType);
    }
}
