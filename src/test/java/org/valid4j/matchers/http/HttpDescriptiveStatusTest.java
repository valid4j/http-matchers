package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

public class HttpDescriptiveStatusTest {

    @Test
    public void shouldBeDescriptive() {
        Response.StatusType status = new HttpDescriptiveStatus(
                Response.Status.CREATED);
        assertThat(status, hasToString("201 - Created"));
    }

    @Test
    public void shouldBeDelegateDescriptive() {
        Response.StatusType status = new HttpDescriptiveStatus(
                new HttpDescriptiveStatus(
                        Response.Status.ACCEPTED));
        assertThat(status, hasToString("202 - Accepted"));
    }

}
