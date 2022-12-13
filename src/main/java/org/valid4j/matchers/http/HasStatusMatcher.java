package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import jakarta.ws.rs.core.Response;
import java.util.Objects;

class HasStatusMatcher extends TypeSafeMatcher<Response> {
    private final int statusCode;
    private final String reason;

    public HasStatusMatcher(Response.StatusType status) {
        this(status.getStatusCode(), status.getReasonPhrase());
    }

    public HasStatusMatcher(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public void describeTo(Description description) {
        description
                .appendText("has status ")
                .appendValue(describe(this.statusCode, this.reason));
    }

    @Override
    protected boolean matchesSafely(Response response) {
        Response.StatusType actual = response.getStatusInfo();
        return (this.statusCode == actual.getStatusCode() &&
                Objects.equals(this.reason, actual.getReasonPhrase()));
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        mismatchDescription
                .appendText("status was ")
                .appendValue(describe(response.getStatusInfo()));
    }

    private static StringBuilder describe(Response.StatusType status) {
        return describe(status.getStatusCode(), status.getReasonPhrase());
    }

    private static StringBuilder describe(int statusCode, String reason) {
        return new StringBuilder()
                .append(statusCode)
                .append(" - ")
                .append(reason);
    }

}