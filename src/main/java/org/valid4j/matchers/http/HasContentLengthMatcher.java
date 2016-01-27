package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;

class HasContentLengthMatcher extends FeatureMatcher<Response, Integer> {
    public HasContentLengthMatcher(Matcher<? super Integer> lengthMatcher) {
        super(lengthMatcher, "has content length", "content length");
    }

    @Override
    protected Integer featureValueOf(Response actual) {
        return actual.getLength();
    }
}
