package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;

class HasStatusCodeMatcher extends FeatureMatcher<Response, Integer> {
    public HasStatusCodeMatcher(Matcher<Integer> statusCodeMatcher) {
        super(statusCodeMatcher, "has status code", "status code");
    }

    @Override
    protected Integer featureValueOf(Response actual) {
        return actual.getStatus();
    }
}
