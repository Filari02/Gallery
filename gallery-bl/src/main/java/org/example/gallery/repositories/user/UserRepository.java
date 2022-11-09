package org.example.gallery.repositories.user;

import org.example.gallery.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
    Optional<User> findByEmail(String email);
}