package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import jakarta.ws.rs.core.Response;

class HasEntityMatcher extends TypeSafeMatcher<Response> {
    public void describeTo(Description description) {
        description.appendText("has entity");
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return response.hasEntity();
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        if (response.hasEntity()) {
            mismatchDescription.appendText("has entity");
        } else {
            mismatchDescription.appendText("has no entity");
        }
    }
}
