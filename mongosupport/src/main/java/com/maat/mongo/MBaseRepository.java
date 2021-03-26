package com.maat.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MBaseRepository<T,ID> extends PagingAndSortingRepository<T, ID> {
}
