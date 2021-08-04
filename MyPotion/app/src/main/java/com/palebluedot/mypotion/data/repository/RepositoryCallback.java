package com.palebluedot.mypotion.data.repository;

public interface RepositoryCallback<T> {
    void onComplete(T result);
}
