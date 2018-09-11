package com.test.githubsearch.data.localstorage;

import io.reactivex.Observable;

public interface LocalStorage {
    void write(String key, String message);
    Observable<String> read(String key);
}
