package com.springGroup.rentingDB.Repo;

import com.springGroup.rentingDB.Models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
