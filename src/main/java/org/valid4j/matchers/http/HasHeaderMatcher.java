package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

class HasHeaderMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final String headerName;

    public HasHeaderMatcher(String headerName) {
        this.headerName = headerName;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        if (headers.containsKey(headerName)) {
            return true;
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
                .appendValue(headerName);
    }
}
