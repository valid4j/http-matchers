package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class HasContentTypeMatcher extends FeatureMatcher<Response, MediaType> {

    public HasContentTypeMatcher(Matcher<? super MediaType> mediaTypeMatcher) {
        super(mediaTypeMatcher, "has Content-Type", "Content-Type");
    }

    @Override
    protected MediaType featureValueOf(Response actual) {
        return actual.getMediaType();
    }
}