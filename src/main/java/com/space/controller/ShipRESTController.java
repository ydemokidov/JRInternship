package com.space.controller;

import com.space.exception.ShipNotValidException;
import com.space.model.Ship;
import com.space.model.ShipFilterParams;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipRESTController {
    private ShipService shipService;

    @Autowired
    public ShipRESTController(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value="/rest/ships",method = RequestMethod.GET)
    public ResponseEntity<List<Ship>> filteredQuery(ShipFilterParams params) {
        int pageNum = params.getPageNumber()==null?0:params.getPageNumber();
        int pageSize = params.getPageSize()==null?3:params.getPageSize();
        Pageable page;
        if(params.getOrder()!=null) {
            page = PageRequest.of(pageNum, pageSize,Sort.by(params.getOrder().getFieldName()));
        }else{
            page = PageRequest.of(pageNum, pageSize);
        }

        final List<Ship> ships = shipService.findAll(params,page);

        return ships != null &&  !ships.isEmpty()
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "rest/ships",method = RequestMethod.POST)
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship){
        try{
            if(ship.getIsUsed()==null){
                ship.setIsUsed(false);
            }
            ship = shipService.save(ship);
            return new ResponseEntity<>(ship, HttpStatus.OK);
        }catch(ShipNotValidException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "rest/ships/count",method = RequestMethod.GET)
    public ResponseEntity<Integer> count(ShipFilterParams params){
        Pageable page = PageRequest.of(0,Integer.MAX_VALUE);
        return new ResponseEntity<>(shipService.findAll(params,page).size(),HttpStatus.OK);
    }

    @RequestMapping(value = "rest/ships/{id}",method = RequestMethod.GET)
    public ResponseEntity<Ship> getShip(@PathVariable(name="id") long id){
        if(id>0) {
            if(shipService.existsById(id)){
                Ship ship = shipService.findById(id);
                return new ResponseEntity<>(ship, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "rest/ships/{id}",method = RequestMethod.POST)
    public ResponseEntity<Ship> updateShip(@PathVariable(name="id") long id,@RequestBody Ship ship){
        if(id>0){
            if(! shipService.existsById(id)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                Ship toUpdateShip = shipService.findById(id);
                toUpdateShip.incrementShip(ship);
                try {
                    toUpdateShip = shipService.save(toUpdateShip);
                    return new ResponseEntity<Ship>(toUpdateShip,HttpStatus.OK);
                }catch(ShipNotValidException e){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "rest/ships/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteShip(@PathVariable(name="id") long id){
        if(id>0) {
            if(!shipService.existsById(id)){
                return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
            }else{
                shipService.deleteById(id);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
}
