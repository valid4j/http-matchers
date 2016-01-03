package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

class OfFamilyMatcher extends TypeSafeMatcher<Integer> {
    private final Response.Status.Family family;

    public OfFamilyMatcher(Response.Status.Family family) {
        this.family = family;
    }

    public void describeTo(Description description) {
        description.appendText("of family ").appendValue(family);
    }

    @Override
    protected boolean matchesSafely(Integer statusCode) {
        return family.equals(Response.Status.Family.familyOf(statusCode));
    }

    @Override
    protected void describeMismatchSafely(Integer statusCode, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(statusCode);
    }
}