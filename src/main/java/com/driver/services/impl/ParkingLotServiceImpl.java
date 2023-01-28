package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot newParkingLot= new ParkingLot( name,address);
        parkingLotRepository1.save(newParkingLot);
        return newParkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        SpotType spotType;

        if(numberOfWheels<=2){
            spotType=SpotType.TWO_WHEELER;
        }else if(numberOfWheels<=4){
            spotType= SpotType.FOUR_WHEELER;
        }else{
            spotType=SpotType.OTHERS;
        }

        Spot spot=new Spot();

        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setSpotType(spotType);

        ParkingLot parkingLot= parkingLotRepository1.findById(parkingLotId).get();

        List<Spot> spotList= parkingLot.getSpotList();
        if(spotList==null){
            spotList=new ArrayList<>();
        }

        spotList.add(spot);

        parkingLot.setSpotList(spotList);

        spot.setParkingLot(parkingLot);
        parkingLotRepository1.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {

        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spots = parkingLot.getSpotList();
        Spot spot = null;
        for(Spot spot1: spots){
            if(spot1.getId()==spotId){
                spot = spot1;
                break;
            }
        }
        spot.setPricePerHour(pricePerHour);
        spot.setParkingLot(parkingLot);

        spotRepository1.save(spot);
        //parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
