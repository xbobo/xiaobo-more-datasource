package org.xiaobo.activity.slave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xiaobo.activity.slave.entity.Message;

@Repository(value = "messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {

}
