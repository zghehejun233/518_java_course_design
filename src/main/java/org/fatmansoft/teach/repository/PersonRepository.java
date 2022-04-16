package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.system.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
