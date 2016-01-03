package org.valid4j.matchers.http;

import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.isCompatibleWith;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

public class MediaTypeMatchersTest {
    private static final MediaType IMAGE_WILDCARD = new MediaType("image", "*");
    private static final MediaType IMAGE_JPEG = new MediaType("image", "jpeg");
    private static final MediaType IMAGE_PNG = new MediaType("image", "png");

    @Test
    public void shouldMatchCompatibleMediaType() {
        assertThat(WILDCARD_TYPE, isCompatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_WILDCARD, isCompatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_WILDCARD, isCompatibleWith(IMAGE_PNG));

        assertThat(IMAGE_JPEG, isCompatibleWith(WILDCARD_TYPE));
        assertThat(IMAGE_JPEG, isCompatibleWith(IMAGE_WILDCARD));
        assertThat(IMAGE_PNG, isCompatibleWith(IMAGE_WILDCARD));

        assertThat(WILDCARD_TYPE, isCompatibleWith(WILDCARD_TYPE));
        assertThat(IMAGE_JPEG, isCompatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_PNG, isCompatibleWith(IMAGE_PNG));

        assertThat(isCompatibleWith(IMAGE_JPEG),
                isDescribedBy("is compatible with <image/jpeg>"));
    }

    @Test
    public void shouldNotMatchIncompatibleMediaType() {
        assertThat(IMAGE_PNG, not(isCompatibleWith(IMAGE_JPEG)));
        assertThat(IMAGE_JPEG, not(isCompatibleWith(IMAGE_PNG)));
        assertThat(APPLICATION_JSON_TYPE, not(isCompatibleWith(APPLICATION_XML_TYPE)));

        assertThat(mismatchOf(APPLICATION_JSON_TYPE, isCompatibleWith(IMAGE_JPEG)),
                equalTo("was <application/json>"));
    }
}
