package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

class HasHeaderWithValueMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final String headerName;
    private final Matcher<?> valueMatcher;

    public HasHeaderWithValueMatcher(String headerName, Matcher<?> valueMatcher) {
        this.headerName = headerName;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        if (headers.containsKey(headerName)) {
            Object value = headers.getFirst(headerName);
            if (!valueMatcher.matches(value)) {
                mismatchDescription
                        .appendText("header ")
                        .appendValue(headerName)
                        .appendText(" was ")
                        .appendValue(value);
                return false;
            } else {
                return true;
            }
        } else {
            mismatchDescription
                    .appendText("header ")
                    .appendValue(headerName)
                    .appendText(" was missing");
            return false;
        }
    }

    public void describeTo(Description description) {
        description
                .appendText("has header ")
                .appendValue(headerName)
                .appendText(" with value ")
                .appendDescriptionOf(valueMatcher);
    }
}
