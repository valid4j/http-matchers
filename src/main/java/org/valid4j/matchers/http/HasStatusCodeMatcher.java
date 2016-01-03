package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.Response;

class HasStatusCodeMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final Matcher<? super Integer> statusCodeMatcher;

    public HasStatusCodeMatcher(Matcher<? super Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        if (statusCodeMatcher.matches(response.getStatus())) {
            return true;
        } else {
            mismatchDescription
                    .appendText("was status code ")
                    .appendValue(response.getStatus());
            return false;
        }
    }

    public void describeTo(Description description) {
        description
                .appendText("has status code ")
                .appendDescriptionOf(statusCodeMatcher);
    }
}
