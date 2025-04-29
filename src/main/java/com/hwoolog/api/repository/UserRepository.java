package com.hwoolog.api.repository;

import com.hwoolog.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
