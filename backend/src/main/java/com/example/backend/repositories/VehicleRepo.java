package com.example.backend.repositories;

import com.example.backend.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepo extends CrudRepository<Vehicle, Long>
{
}
