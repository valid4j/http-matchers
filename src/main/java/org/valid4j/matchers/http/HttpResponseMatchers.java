package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;
import java.util.List;
import java.util.Objects;

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
        return new TypeSafeDiagnosingMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response, Description mismatchDescription) {
                if (statusCodeMatcher.matches(response.getStatus())) {
                    return true;
                } else {
                    mismatchDescription
                            .appendText("was status code ")
                            .appendValue(response.getStatus());
                    return false;
                }
            }

            public void describeTo(Description description) {
                description
                        .appendText("has status code ")
                        .appendDescriptionOf(statusCodeMatcher);
            }
        };
    }

    public static Matcher<? super Response> hasStatus(final StatusType expected) {
        final StatusType status = new HttpDescriptiveStatus(expected);
        return new TypeSafeDiagnosingMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response, Description mismatchDescription) {
                StatusType actual = new HttpDescriptiveStatus(response.getStatusInfo());
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

        };
    }

    public static Matcher<? super Integer> ofFamily(final Family family) {
        return new TypeSafeDiagnosingMatcher<Integer>() {
            @Override
            protected boolean matchesSafely(Integer statusCode, Description mismatchDescription) {
                if (family.equals(Family.familyOf(statusCode))) {
                    return true;
                } else {
                    mismatchDescription.appendText("was ").appendValue(statusCode);
                    return false;
                }
            }

            public void describeTo(Description description) {
                description.appendText("of family ").appendValue(family);
            }
        };
    }

    public static Matcher<? super Response> hasContentType(final String mediaType) {
        return hasContentType(MediaType.valueOf(mediaType));
    }

    public static Matcher<? super Response> hasContentType(final MediaType mediaType) {
        return new TypeSafeDiagnosingMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response, Description mismatchDescription) {
                MediaType actual = response.getMediaType();
                if (Objects.equals(actual, mediaType)) {
                    return true;
                } else {
                    mismatchDescription.appendText("was content type ").appendValue(actual);
                    return false;
                }
            }

            public void describeTo(Description description) {
                description.appendText("has content type ").appendValue(mediaType);
            }

        };
    }

    public static Matcher<? super Response> hasHeader(final String headerName) {
        return new TypeSafeDiagnosingMatcher<Response>() {
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
        };
    }

    public static Matcher<? super Response> hasHeader(
            final String headerName,
            final Matcher<? extends Object> valueMatcher) {
        return new TypeSafeDiagnosingMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response, Description mismatchDescription) {
                MultivaluedMap<String, Object> headers = response.getHeaders();
                if (headers.containsKey(headerName)) {
                    Object value = headers.getFirst(headerName);
                    if (!valueMatcher.matches(value)) {
                        mismatchDescription
                                .appendText("header ")
                                .appendValue(headerName)
                                .appendText(" contained ")
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
        };
    }

    public static Matcher<? super Response> hasHeaderValues(
            final String headerName,
            final Matcher<? super Iterable<? super Object>> valuesMatcher) {
        return new TypeSafeDiagnosingMatcher<Response>() {
            @Override
            protected boolean matchesSafely(Response response, Description mismatchDescription) {
                MultivaluedMap<String, Object> headers = response.getHeaders();
                if (headers.containsKey(headerName)) {
                    List<Object> values = headers.get(headerName);
                    if (!valuesMatcher.matches(values)) {
                        mismatchDescription
                                .appendText("header ")
                                .appendValue(headerName)
                                .appendText(" contained ")
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
        };
    }

}
