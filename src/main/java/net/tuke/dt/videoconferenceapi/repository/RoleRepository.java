package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
