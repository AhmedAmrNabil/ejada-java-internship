package io.github.ahmedamr.authentication.repository;

import io.github.ahmedamr.authentication.entity.AppUser;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);

	boolean existsByUsername(String username);

	@EntityGraph(attributePaths = "roles")
	Optional<AppUser> findWithRolesById(Long id);

	@EntityGraph(attributePaths = "roles")
	Optional<AppUser> findWithRolesByUsername(String username);

}
