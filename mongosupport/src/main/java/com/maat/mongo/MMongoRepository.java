package com.maat.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MMongoRepository<T> extends PagingAndSortingRepository<T, Id> {
}
