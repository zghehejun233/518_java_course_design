package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.system.EUserType;
import org.fatmansoft.teach.models.system.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(EUserType name);
}