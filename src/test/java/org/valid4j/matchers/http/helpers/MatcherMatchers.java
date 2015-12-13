package org.valid4j.matchers.http.helpers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.equalTo;

public class MatcherMatchers {

    public static Matcher<Matcher<?>> isDescribedBy(final String description) {
        return isDescribedBy(equalTo(description));
    }

    public static Matcher<Matcher<?>> isDescribedBy(final Matcher<String> descriptionMatcher) {
        return new TypeSafeDiagnosingMatcher<Matcher<?>>() {
            @Override
            protected boolean matchesSafely(Matcher<?> matcher, Description mismatchDescription) {
                Description description = new StringDescription();
                matcher.describeTo(description);
                if (descriptionMatcher.matches(description.toString())) {
                    return true;
                } else {
                    mismatchDescription
                            .appendText("was ")
                            .appendValue(description.toString());
                    return false;
                }
            }

            public void describeTo(Description description) {
                description
                        .appendText("is described by ")
                        .appendDescriptionOf(descriptionMatcher);
            }
        };
    }
}
