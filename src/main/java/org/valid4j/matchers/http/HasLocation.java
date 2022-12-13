package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import jakarta.ws.rs.core.Response;
import java.net.URI;

class HasLocation extends FeatureMatcher<Response, URI> {
    public HasLocation(Matcher<? super URI> uriMatcher) {
        super(uriMatcher, "has Location", "Location");
    }

    @Override
    protected URI featureValueOf(Response actual) {
        return actual.getLocation();
    }
}
