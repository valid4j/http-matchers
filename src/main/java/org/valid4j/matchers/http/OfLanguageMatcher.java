package org.valid4j.matchers.http;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import javax.ws.rs.core.Response;
import java.util.Locale;

class OfLanguageMatcher extends FeatureMatcher<Response, Locale> {

    public OfLanguageMatcher(Matcher<? super Locale> localeMatcher) {
        super(localeMatcher, "of Content-Language", "Content-Language");
    }

    @Override
    protected Locale featureValueOf(Response actual) {
        return actual.getLanguage();
    }
}
