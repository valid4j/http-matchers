package org.valid4j.matchers.http;

import javax.ws.rs.core.Response;

/**
 * A class to decorate a {@link javax.ws.rs.core.Response.StatusType}
 * with a more descriptive {@link Object#toString()}
 */
class HttpDescriptiveStatus implements Response.StatusType {
    private final Response.StatusType status;

    public HttpDescriptiveStatus(Response.StatusType status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status.getStatusCode();
    }

    public Response.Status.Family getFamily() {
        return status.getFamily();
    }

    public String getReasonPhrase() {
        return status.getReasonPhrase();
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
