package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;
import java.util.Objects;

class HasStatusMatcher extends TypeSafeMatcher<Response> {
    private final Response.StatusType status;

    public HasStatusMatcher(Response.StatusType status) {
        this.status = status;
    }

    public void describeTo(Description description) {
        description.appendText("has status ").appendValue(status);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        Response.StatusType actual = response.getStatusInfo();
        if (status.getStatusCode() == actual.getStatusCode() &&
                Objects.equals(status.getReasonPhrase(), actual.getReasonPhrase())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        Response.StatusType actual = new HttpDescriptiveStatus(response.getStatusInfo());
        mismatchDescription.appendText("was status ").appendValue(actual);
    }
}