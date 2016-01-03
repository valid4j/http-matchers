package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.Response;
import java.util.Objects;

class HasStatusMatcher extends TypeSafeDiagnosingMatcher<Response> {
    private final Response.StatusType status;

    public HasStatusMatcher(Response.StatusType status) {
        this.status = status;
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        Response.StatusType actual = new HttpDescriptiveStatus(response.getStatusInfo());
        if (status.getStatusCode() == actual.getStatusCode() &&
                Objects.equals(status.getReasonPhrase(), actual.getReasonPhrase())) {
            return true;
        } else {
            mismatchDescription.appendText("was status ").appendValue(actual);
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendText("has status ").appendValue(status);
    }

}
