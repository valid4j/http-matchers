package org.valid4j.matchers.http;

import org.hamcrest.Matcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.equalTo;

public class HttpResponseMatchers {

    private HttpResponseMatchers() {
        throw new AssertionError("Prevent instantiation");
    }

    public static Matcher<? super Response> isResponseOk() {
        return describedAs("is response ok", hasStatusCode(200));
    }

    public static Matcher<? super Response> hasStatusCode(final int statusCode) {
        return hasStatusCode(equalTo(statusCode));
    }

    public static Matcher<? super Response> hasStatusCode(final Family family) {
        return hasStatusCode(ofFamily(family));
    }

    public static Matcher<? super Response> hasStatusCodeOf(final StatusType status) {
        return hasStatusCode(status.getStatusCode());
    }

    public static Matcher<? super Response> hasStatusCode(final Matcher<? super Integer> statusCodeMatcher) {
        return new HasStatusCodeMatcher(statusCodeMatcher);
    }

    public static Matcher<? super Response> hasStatus(final StatusType expected) {
        final StatusType status = new HttpDescriptiveStatus(expected);
        return new HasStatusMatcher(status);
    }

    public static Matcher<? super Integer> ofFamily(final Family family) {
        return new OfFamilyMatcher(family);
    }

    public static Matcher<? super Response> hasContentType(final String mediaType) {
        return hasContentType(MediaType.valueOf(mediaType));
    }

    public static Matcher<? super Response> hasContentType(final MediaType mediaType) {
        return new HasContentTypeMatcher(mediaType);
    }

    public static Matcher<? super Response> hasHeader(final String headerName) {
        return new HasHeaderMatcher(headerName);
    }

    public static Matcher<? super Response> hasHeader(
            final String headerName,
            final Matcher<? extends Object> valueMatcher) {
        return new HasHeaderWithValueMatcher(headerName, valueMatcher);
    }

    public static Matcher<? super Response> hasHeaderValues(
            final String headerName,
            final Matcher<? super Iterable<? super Object>> valuesMatcher) {
        return new HasHeaderWithValuesMatcher(headerName, valuesMatcher);
    }

}
