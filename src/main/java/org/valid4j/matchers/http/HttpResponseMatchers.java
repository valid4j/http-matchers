package org.valid4j.matchers.http;

import org.hamcrest.Matcher;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status.Family;
import jakarta.ws.rs.core.Response.StatusType;
import java.net.URI;
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

    public static Matcher<Response> hasStatusCode(final Matcher<Integer> statusCodeMatcher) {
        return new HasStatusCodeMatcher(statusCodeMatcher);
    }

    public static Matcher<Response> hasStatus(final StatusType status) {
        return new HasStatusMatcher(status);
    }

    public static Matcher<Response> hasStatus(final int statusCode, final String reason) {
        return new HasStatusMatcher(statusCode, reason);
    }

    public static Matcher<Integer> ofFamily(final Family family) {
        return new OfFamilyMatcher(family);
    }

    public static Matcher<Response> hasHeader(final String headerName) {
        return new HasHeaderMatcher(headerName);
    }

    public static Matcher<Response> hasHeader(
            final String headerName,
            final Matcher<?> valueMatcher) {
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

    public static Matcher<Response> hasCookie(final String cookieName, final Matcher<NewCookie> cookieMatcher) {
        return new HasCookieWithValueMatcher(cookieName, cookieMatcher);
    }

    public static Matcher<Response> hasEntity() {
        return new HasEntityMatcher();
    }

    public static <T> Matcher<Response> hasEntity(Matcher<String> entityMatcher) {
        return new HasEntityWithValueMatcher(String.class, entityMatcher);
    }

    public static <T> Matcher<Response> hasEntity(Class<T> entityClass, Matcher<? super T> entityMatcher) {
        return new HasEntityWithValueMatcher(entityClass, entityMatcher);
    }

    public static <T> Matcher<Response> hasEntity(GenericType<T> entityType, Matcher<? super T> entityMatcher) {
        return new HasGenericEntityWithValueMatcher(entityType, entityMatcher);
    }

    public static Matcher<Response> hasContentType(final String mediaType) {
        return hasContentType(MediaType.valueOf(mediaType));
    }

    public static Matcher<Response> hasContentType(final MediaType mediaType) {
        return hasContentType(equalTo(mediaType));
    }

    public static Matcher<Response> hasContentType(final Matcher<? super MediaType> mediaTypeMatcher) {
        return new HasContentTypeMatcher(mediaTypeMatcher);
    }

    public static Matcher<MediaType> compatibleWith(final MediaType compatibleMediaType) {
        return new MediaTypeCompatibleWithMatcher(compatibleMediaType);
    }

    public static Matcher<Response> hasContentEncoding(final String encoding) {
        return new HasHeaderWithValueMatcher(HttpHeaders.CONTENT_ENCODING, equalTo(encoding));
    }

    public static Matcher<Response> hasContentLocation(final URI uri) {
        return hasContentLocation(equalTo(uri));
    }

    public static Matcher<Response> hasContentLocation(final Matcher<URI> uriMatcher) {
        return new HasHeaderWithValueMatcher(HttpHeaders.CONTENT_LOCATION, uriMatcher);
    }

    public static Matcher<Response> ofLanguage(final String languageTag) {
        return ofLanguage(equalTo(Locale.forLanguageTag(languageTag)));
    }

    public static Matcher<Response> ofLanguage(final Locale locale) {
        return ofLanguage(equalTo(locale));
    }

    public static Matcher<Response> ofLanguage(final Matcher<Locale> localeMatcher) {
        return new OfLanguageMatcher(localeMatcher);
    }

    public static Matcher<Response> hasLastModifiedDate(final Matcher<Date> lastModDateMatcher) {
        return new HasLastModifiedDate(lastModDateMatcher);
    }

    public static Matcher<Response> hasContentLength(final Matcher<Integer> lengthMatcher) {
        return new HasContentLengthMatcher(lengthMatcher);
    }

    public static Matcher<Response> hasLocation(final URI uri) {
        return hasLocation(equalTo(uri));
    }

    public static Matcher<Response> hasLocation(final Matcher<URI> uriMatcher) {
        return new HasLocation(uriMatcher);
    }
}
