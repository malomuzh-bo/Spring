package com.springGroup.rentingDB.Repo;

import com.springGroup.rentingDB.Models.Rent;
import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent, Long> {
}
