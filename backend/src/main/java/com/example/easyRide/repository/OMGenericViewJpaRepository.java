package com.example.easyRide.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


/**
 * A really basic extension of a readonly Repository
 *
 * @param <E>  the entity of the repository
 * @param <ID> the type of the ID of the entity
 * @author Emmettippì
 */

@NoRepositoryBean

public interface OMGenericViewJpaRepository<E , ID> extends Repository<E, ID> {

    List<E> findAll();

    void save(E entity);

    void deleteById(Long id);


    boolean existsById(Long id);


    Page<E> findAll(Pageable pageable);


    Optional<E> findById(ID id);


}