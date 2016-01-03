package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

class HasContentTypeMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final MediaType mediaType;

    public HasContentTypeMatcher(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        MediaType actual = response.getMediaType();
        if (Objects.equals(actual, mediaType)) {
            return true;
        } else {
            mismatchDescription.appendText("was content type ").appendValue(actual);
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("has content type ").appendValue(mediaType);
    }

}
