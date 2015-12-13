package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class HttpStatusTest {

    @Test
    public void shouldRepresentSuccessfulStatus() {
        Response.StatusType status = new HttpStatus(200, "Created");
        assertThat(status.getStatusCode(), equalTo(200));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.SUCCESSFUL));
        assertThat(status.getReasonPhrase(), equalTo("Created"));
        assertThat(status.toString(), equalTo("200 - Created"));
    }

    @Test
    public void shouldRepresentInformationalStatus() {
        Response.StatusType status = new HttpStatus(100, "Continue");
        assertThat(status.getStatusCode(), equalTo(100));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.INFORMATIONAL));
        assertThat(status.getReasonPhrase(), equalTo("Continue"));
        assertThat(status.toString(), equalTo("100 - Continue"));
    }

    @Test
    public void shouldRepresentRedirectionStatus() {
        Response.StatusType status = new HttpStatus(301, "Moved Permanently");
        assertThat(status.getStatusCode(), equalTo(301));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.REDIRECTION));
        assertThat(status.getReasonPhrase(), equalTo("Moved Permanently"));
        assertThat(status.toString(), equalTo("301 - Moved Permanently"));
    }

    @Test
    public void shouldRepresentClientErrorStatus() {
        Response.StatusType status = new HttpStatus(422, "Unprocessable Entity");
        assertThat(status.getStatusCode(), equalTo(422));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.CLIENT_ERROR));
        assertThat(status.getReasonPhrase(), equalTo("Unprocessable Entity"));
        assertThat(status.toString(), equalTo("422 - Unprocessable Entity"));
    }

    @Test
    public void shouldRepresentServerErrorStatus() {
        Response.StatusType status = new HttpStatus(503, "Service Unavailable");
        assertThat(status.getStatusCode(), equalTo(503));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.SERVER_ERROR));
        assertThat(status.getReasonPhrase(), equalTo("Service Unavailable"));
        assertThat(status.toString(), equalTo("503 - Service Unavailable"));
    }

    @Test
    public void shouldRepresentOtherStatus() {
        Response.StatusType status = new HttpStatus(999, "Not Defined");
        assertThat(status.getStatusCode(), equalTo(999));
        assertThat(status.getFamily(), equalTo(Response.Status.Family.OTHER));
        assertThat(status.getReasonPhrase(), equalTo("Not Defined"));
        assertThat(status.toString(), equalTo("999 - Not Defined"));
    }
}
