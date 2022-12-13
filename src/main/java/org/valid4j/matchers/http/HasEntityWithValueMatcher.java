package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import jakarta.ws.rs.core.Response;

class HasEntityWithValueMatcher<T> extends TypeSafeDiagnosingMatcher<Response> {
    private final Class<T> entityClass;
    private final Matcher<? super T> entityMatcher;

    public HasEntityWithValueMatcher(
        Class<T> entityClass,
        Matcher<? super T> entityMatcher) {
        this.entityClass = entityClass;
        this.entityMatcher = entityMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has Entity ").appendDescriptionOf(entityMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response, Description mismatchDescription) {
        response.bufferEntity();
        T entity = response.readEntity(entityClass);
        if (!entityMatcher.matches(entity)) {
            mismatchDescription.appendText("Entity ");
            entityMatcher.describeMismatch(entity, mismatchDescription);
            return false;
        }
        return true;
    }
}
