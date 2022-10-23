package com.example.backend.controller;

import com.example.backend.entities.Vehicle;
import com.example.backend.repositories.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(path = "/api/vehicle")
public class VehicleController
{
    VehicleRepo vehicleRepo;

    @Autowired
    public VehicleController(VehicleRepo vehicleRepo)
    {
        this.vehicleRepo = vehicleRepo;
    }

    @GetMapping
    public List<String> list()
    {
        Iterable<Vehicle> iterable = vehicleRepo.findAll();
        Iterator<Vehicle> iterator = iterable.iterator();
        List<String> list = new ArrayList<String>();
        while(iterator.hasNext())
            list.add(iterator.next().getModel());
        return list;
    }
}
