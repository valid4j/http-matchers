package org.valid4j.matchers.http.helpers;

import jakarta.ws.rs.core.Response;

/**
 * Implementation class of a {@link jakarta.ws.rs.core.Response.StatusType}
 */
public class HttpStatus implements Response.StatusType {

    private final int code;
    private final Response.Status.Family family;
    private final String reason;

    public HttpStatus(int code, String reason) {
        this.code = code;
        this.family = Response.Status.Family.familyOf(code);
        this.reason = reason;
    }

    public int getStatusCode() {
        return this.code;
    }

    public Response.Status.Family getFamily() {
        return this.family;
    }

    public String getReasonPhrase() {
        return this.reason;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getStatusCode())
                .append(" - ")
                .append(getReasonPhrase())
                .toString();
    }
}
