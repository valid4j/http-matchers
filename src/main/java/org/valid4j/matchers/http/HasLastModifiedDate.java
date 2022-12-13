package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import jakarta.ws.rs.core.Response;
import java.util.Date;

class HasLastModifiedDate extends FeatureMatcher<Response, Date> {
    public HasLastModifiedDate(Matcher<? super Date> lastModDateMatcher) {
        super(lastModDateMatcher, "has Last-Modified", "Last-Modified");
    }

    @Override
    protected Date featureValueOf(Response actual) {
        return actual.getLastModified();
    }
}
