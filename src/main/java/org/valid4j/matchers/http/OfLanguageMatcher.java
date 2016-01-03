package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;
import java.util.Locale;

class OfLanguageMatcher extends TypeSafeMatcher<Response> {
    private final Matcher<? extends Locale> localeMatcher;

    public OfLanguageMatcher(Matcher<? extends Locale> localeMatcher) {
        this.localeMatcher = localeMatcher;
    }

    public void describeTo(Description description) {
        description.appendText("of language ").appendDescriptionOf(localeMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        return localeMatcher.matches(response.getLanguage());
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        String language = response.getLanguage().toLanguageTag();
        mismatchDescription.appendText("was language ").appendValue(language);
    }
}
