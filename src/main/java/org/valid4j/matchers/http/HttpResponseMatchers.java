package org.valid4j.matchers.http;

import org.hamcrest.Matcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.equalTo;

public class HttpResponseMatchers {

    private HttpResponseMatchers() {
        throw new AssertionError("Prevent instantiation");
    }

    public static Matcher<Response> isResponseOk() {
        return describedAs("is response ok", hasStatusCode(200));
    }

    public static Matcher<Response> hasStatusCode(final int statusCode) {
        return hasStatusCode(equalTo(statusCode));
    }

    public static Matcher<Response> hasStatusCode(final Family family) {
        return hasStatusCode(ofFamily(family));
    }

    public static Matcher<Response> hasStatusCodeOf(final StatusType status) {
        return hasStatusCode(status.getStatusCode());
    }

    public static Matcher<Response> hasStatusCode(final Matcher<? super Integer> statusCodeMatcher) {
        return new HasStatusCodeMatcher(statusCodeMatcher);
    }

    public static Matcher<Response> hasStatus(final StatusType expected) {
        final StatusType status = new HttpDescriptiveStatus(expected);
        return new HasStatusMatcher(status);
    }

    public static Matcher<Integer> ofFamily(final Family family) {
        return new OfFamilyMatcher(family);
    }

    public static Matcher<Response> hasContentType(final String mediaType) {
        return hasContentType(MediaType.valueOf(mediaType));
    }

    public static Matcher<Response> hasContentType(final MediaType mediaType) {
        return hasContentType(equalTo(mediaType));
    }

    public static Matcher<Response> hasContentType(final Matcher<? extends MediaType> mediaTypeMatcher) {
        return new HasContentTypeMatcher(mediaTypeMatcher);
    }

    public static Matcher<MediaType> isCompatibleWith(final MediaType compatibleMediaType) {
        return new MediaTypeCompatibleWithMatcher(compatibleMediaType);
    }

    public static Matcher<Response> hasHeader(final String headerName) {
        return new HasHeaderMatcher(headerName);
    }

    public static Matcher<Response> hasHeader(
            final String headerName,
            final Matcher<? extends Object> valueMatcher) {
        return new HasHeaderWithValueMatcher(headerName, valueMatcher);
    }

    public static Matcher<Response> hasHeaderValues(
            final String headerName,
            final Matcher<? extends Iterable<?>> valuesMatcher) {
        return new HasHeaderWithValuesMatcher(headerName, valuesMatcher);
    }

    public static Matcher<Response> hasCookie(final String cookieName) {
        return new HasCookieMatcher(cookieName);
    }

    public static Matcher<Response> hasEntity() {
        return new HasEntityMatcher();
    }

    public static Matcher<Response> ofLanguage(final String languageTag) {
        return ofLanguage(equalTo(Locale.forLanguageTag(languageTag)));
    }

    public static Matcher<Response> ofLanguage(final Locale locale) {
        return ofLanguage(equalTo(locale));
    }

    public static Matcher<Response> ofLanguage(final Matcher<? extends Locale> localeMatcher) {
        return new OfLanguageMatcher(localeMatcher);
    }

    public static Matcher<Response> withLastModifiedDate(final Matcher<? extends Date> lastModDateMatcher) {
        return new WithLastModifiedDateMatcher(lastModDateMatcher);
    }

}
