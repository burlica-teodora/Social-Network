package com.example.social_network1.domain.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
