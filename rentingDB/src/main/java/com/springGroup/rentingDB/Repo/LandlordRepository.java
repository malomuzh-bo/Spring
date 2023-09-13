package com.springGroup.rentingDB.Repo;

import com.springGroup.rentingDB.Models.Landlord;
import org.springframework.data.repository.CrudRepository;

public interface LandlordRepository extends CrudRepository<Landlord, Long> {
}
