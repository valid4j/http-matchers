package org.valid4j.matchers.http;


import jakarta.ws.rs.core.MediaType;

import static jakarta.ws.rs.core.MediaType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.Test;
import static org.valid4j.matchers.http.HttpResponseMatchers.compatibleWith;
import static org.valid4j.matchers.http.helpers.MatcherHelpers.mismatchOf;
import static org.valid4j.matchers.http.helpers.MatcherMatchers.isDescribedBy;

class MediaTypeMatchersTest {
    private static final MediaType IMAGE_WILDCARD = new MediaType("image", "*");
    private static final MediaType IMAGE_JPEG = new MediaType("image", "jpeg");
    private static final MediaType IMAGE_PNG = new MediaType("image", "png");

    @Test
    void shouldMatchCompatibleMediaType() {
        assertThat(WILDCARD_TYPE, compatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_WILDCARD, compatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_WILDCARD, compatibleWith(IMAGE_PNG));

        assertThat(IMAGE_JPEG, compatibleWith(WILDCARD_TYPE));
        assertThat(IMAGE_JPEG, compatibleWith(IMAGE_WILDCARD));
        assertThat(IMAGE_PNG, compatibleWith(IMAGE_WILDCARD));

        assertThat(WILDCARD_TYPE, compatibleWith(WILDCARD_TYPE));
        assertThat(IMAGE_JPEG, compatibleWith(IMAGE_JPEG));
        assertThat(IMAGE_PNG, compatibleWith(IMAGE_PNG));

        assertThat(compatibleWith(IMAGE_JPEG),
                isDescribedBy("compatible with <image/jpeg>"));
    }

    @Test
    void shouldNotMatchIncompatibleMediaType() {
        assertThat(IMAGE_PNG, not(compatibleWith(IMAGE_JPEG)));
        assertThat(IMAGE_JPEG, not(compatibleWith(IMAGE_PNG)));
        assertThat(APPLICATION_JSON_TYPE, not(compatibleWith(APPLICATION_XML_TYPE)));

        assertThat(mismatchOf(APPLICATION_JSON_TYPE, compatibleWith(IMAGE_JPEG)),
                equalTo("was <application/json>"));
    }
}
