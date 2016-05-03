package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

class HasGenericEntityWithValueMatcher<T> extends TypeSafeDiagnosingMatcher<Response> {
    private final GenericType<T> entityType;
    private final Matcher<? super T> entityMatcher;

    public HasGenericEntityWithValueMatcher(
            GenericType<T> entityType,
            Matcher entityMatcher) {
        this.entityType = entityType;
        this.entityMatcher = entityMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has Entity ").appendDescriptionOf(entityMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        response.bufferEntity();
        T entity = response.readEntity(entityType);
        if (!entityMatcher.matches(entity)) {
            mismatchDescription.appendText("Entity ");
            entityMatcher.describeMismatch(entity, mismatchDescription);
            return false;
        }
        return true;
    }
}
