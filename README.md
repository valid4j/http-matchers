http-matchers
=============

A set of [hamcrest-matchers](http://hamcrest.org/JavaHamcrest/) to test 
your web services via the standard [Java API for RESTful Services (JAX-RS)](https://jax-rs-spec.java.net/)

The [JAX-RS client API (javax.ws.rs.client)](https://docs.oracle.com/javaee/7/api/index.html?javax/ws/rs/client/package-summary.html)
can be downloaded from here, but is usually bundled with the client implementation you are using, e.g Jersey.

    <dependency>
	    <groupId>javax.ws.rs</groupId>
	    <artifactId>javax.ws.rs-api</artifactId>
	    <version>2.0.1</version>
    </dependency>

### Usage guide

Example usage:

    // Statically import the library entry point:
    import static org.valid4j.matchers.http.HttpResponseMatchers.*;
    
    // Invoke your web service using plain 
    Client client = ClientBuilder.newClient();
    Response response = client.target("http://example.org/hello").request("text/plain").get();
    
    // Verify the status code of response
    assertThat(response, hasStatusCode(Family.SUCCESSFUL));
    assertThat(response, hasStatusCode(200));
    assertThat(response, hasStatusCodeOf(Status.OK));

    // Verify status code and reason of response
    assertThat(response, hasStatus(Status.OK));
    
    // Verify content type of response
    assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
    assertThat(response, hasContentType(isCompatibleWith(MediaType.APPLICATION_JSON_TYPE)));
    
    // Verify headers of response
    assertThat(response, hasHeader("some-header"));
    assertThat(response, hasHeader("some-string-header", equalTo("some-value")));
    assertThat(response, hasHeader("some-int-header", equalTo(42)));
    assertThat(response, hasHeaderValues("some-headers", hasItem(equalTo("some-value"))));
    
    ...
    
    