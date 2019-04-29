package org.xiaobo.activity.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xiaobo.activity.master.entity.User;

@Repository(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

}
