package com.example.jobportal.repository;

import com.example.jobportal.model.Notification;
import com.example.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByTimestampDesc(User user);
    List<Notification> findByUserAndIsReadOrderByTimestampDesc(User user, boolean isRead);
}
