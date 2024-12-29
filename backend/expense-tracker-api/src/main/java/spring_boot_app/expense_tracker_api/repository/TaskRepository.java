package spring_boot_app.expense_tracker_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot_app.expense_tracker_api.entity.Task;

import java.sql.Date;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus(String status, Pageable page);

    Page<Task> findByNameContaining(String keyword, Pageable page);

    Page<Task> findByDueDateBetween(Date startDate, Date endDate, Pageable page);
}
