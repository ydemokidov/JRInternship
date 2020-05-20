package com.space.service;

import com.space.exception.ShipNotValidException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Repository
@Transactional
public class ShipServiceImpl implements ShipService{

    private ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> findAll(Specification<Ship> spec, Pageable pageable) {
        return StreamSupport.stream(shipRepository.findAll(spec,pageable).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Ship save(Ship ship) throws ShipNotValidException {
        if(ship.validate()) {
            ship.ratingRefresh();
            ship = shipRepository.save(ship);
            return ship;
        }else{
            throw new ShipNotValidException();
        }
    }

    @Override
    public void deleteById(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return shipRepository.existsById(id);
    }

    @Override
    public Ship findById(Long id) {
        return shipRepository.findById(id).get();
    }

    /*@Override
    public long count() {
        return shipRepository.count();
    }*/
}
