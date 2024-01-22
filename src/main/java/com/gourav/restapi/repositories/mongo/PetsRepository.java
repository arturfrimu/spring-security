package com.gourav.restapi.repositories.mongo;

import com.gourav.restapi.models.mongo.Pets;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetsRepository extends MongoRepository<Pets, String> {

    Optional<Pets> findById(ObjectId id);
}
