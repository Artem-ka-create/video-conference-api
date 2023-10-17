package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


}
