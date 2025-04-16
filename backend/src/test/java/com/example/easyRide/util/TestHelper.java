package com.example.easyRide.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.Mockito.when;

public abstract class TestHelper<T, D, ID> {


    protected Page<T> createPageWithElements(List<T> listEntity) {
        return new PageImpl<>(listEntity);
    }

    protected Page<D> createPageWithDTOs( List<T> listEntity, Function<? super T, ? extends D> mapper) {
        return createPageWithElements(listEntity).map(mapper);
    }

    protected void setupRepositoryToReturnEntity(T entity,
                                                             ID id,
                                                             JpaRepository<T, ID> repositoryMethod) {
        when(repositoryMethod.findById(id))
                .thenReturn(Optional.ofNullable(entity));
    }

    protected void setupRepositoryToReturnPage(Page<T> page,
                                                       Pageable pageable,
                                                       Function<Pageable, Page<T>> repositoryMethod) {
        when(repositoryMethod.apply(pageable))
                .thenReturn(page);

    }

}
