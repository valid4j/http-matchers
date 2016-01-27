package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;
import java.util.Date;

class HasLastModifiedDate extends FeatureMatcher<Response, Date> {
    public HasLastModifiedDate(Matcher<? super Date> lastModDateMatcher) {
        super(lastModDateMatcher, "has last modified date", "last modified date");
    }

    @Override
    protected Date featureValueOf(Response actual) {
        return actual.getLastModified();
    }
}
