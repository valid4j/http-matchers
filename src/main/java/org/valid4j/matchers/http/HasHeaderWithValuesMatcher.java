package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.util.List;

class HasHeaderWithValuesMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final String headerName;
    private final Matcher<? extends Iterable<?>> valuesMatcher;

    public HasHeaderWithValuesMatcher(String headerName, Matcher<? extends Iterable<?>> valuesMatcher) {
        this.headerName = headerName;
        this.valuesMatcher = valuesMatcher;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        if (headers.containsKey(headerName)) {
            List<Object> values = headers.get(headerName);
            if (!valuesMatcher.matches(values)) {
                mismatchDescription
                        .appendText("header ")
                        .appendValue(headerName)
                        .appendText(" was ")
                        .appendValueList("", ",", "", values);
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
                .appendText(" with ")
                .appendDescriptionOf(valuesMatcher);
    }
}
