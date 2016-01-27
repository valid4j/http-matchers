package org.valid4j.matchers.http;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

public class HasEntityWithValueMatcher<T> extends TypeSafeMatcher<Response> {
    private final Class<T> entityClass;
    private final Matcher<? super T> entityMatcher;

    public HasEntityWithValueMatcher(Class<T> entityClass, Matcher<? super T> entityMatcher) {
        this.entityClass = entityClass;
        this.entityMatcher = entityMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has entity ").appendDescriptionOf(entityMatcher);
    }

    @Override
    protected boolean matchesSafely(Response response) {
        response.bufferEntity();
        T entity = response.readEntity(entityClass);
        return entityMatcher.matches(entity);
    }

    @Override
    protected void describeMismatchSafely(Response response, Description mismatchDescription) {
        response.bufferEntity();
        T entity = response.readEntity(entityClass);
        mismatchDescription.appendText("entity was ").appendValue(entity);
    }
}
