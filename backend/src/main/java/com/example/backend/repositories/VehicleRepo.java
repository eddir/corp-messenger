package com.example.backend.repositories;

import com.example.backend.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepo extends CrudRepository<Vehicle, Long>
{
}
