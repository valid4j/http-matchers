package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.Response;

class OfFamilyMatcher extends TypeSafeDiagnosingMatcher<Integer> {
    private final Response.Status.Family family;

    public OfFamilyMatcher(Response.Status.Family family) {
        this.family = family;
    }

    @Override
    protected boolean matchesSafely(Integer statusCode, Description mismatchDescription) {
        if (family.equals(Response.Status.Family.familyOf(statusCode))) {
            return true;
        } else {
            mismatchDescription.appendText("was ").appendValue(statusCode);
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("of family ").appendValue(family);
    }
}
