package com.gourav.restapi.repositories;

import com.gourav.restapi.models.Pets;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends MongoRepository<Pets, String> {

    Pets findById(ObjectId id);
}
