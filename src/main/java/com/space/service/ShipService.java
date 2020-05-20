package com.space.service;

import com.space.exception.ShipNotValidException;
import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    List<Ship> findAll(Specification<Ship> spec, Pageable pageable);
    Ship save(Ship ship) throws ShipNotValidException;
    void deleteById(Long id);
    boolean existsById(Long id);
    Ship findById(Long id);
    //long count();
}
