package com.springGroup.rentingDB.Repo;

import com.springGroup.rentingDB.Models.Apartment;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
}
