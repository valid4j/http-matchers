package org.valid4j.matchers.http.helpers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class MatcherHelpers {

    public static <T> String mismatchOf(T mismatch, Matcher<? super T> matcher) {
        Description mismatchDescription = new StringDescription();
        matcher.describeMismatch(mismatch, mismatchDescription);
        return mismatchDescription.toString();
    }

}
