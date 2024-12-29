package spring_boot_app.expense_tracker_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring_boot_app.expense_tracker_api.entity.Task;

import java.sql.Date;
import java.util.List;

public interface TaskService {
    Page<Task> getAllTasks(Pageable page);

    Task getTaskById(Long id);

    void deleteTaskById(Long id);

    Task saveTaskDetails(Task task);

    Task updateTaskDetails(Long id, Task task);

    Page<Task> findByStatus(String status, Pageable page);

    Page<Task> findByName(String keyword, Pageable page);

    Page<Task> findByDueDate(Date startDate, Date endDate, Pageable page);
}
