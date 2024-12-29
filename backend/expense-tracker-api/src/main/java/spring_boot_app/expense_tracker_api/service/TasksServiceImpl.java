package spring_boot_app.expense_tracker_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring_boot_app.expense_tracker_api.entity.Task;
import spring_boot_app.expense_tracker_api.exceptions.ResourceNotFoundException;
import spring_boot_app.expense_tracker_api.repository.TaskRepository;

import java.sql.Date;
import java.util.Optional;

@Service
public class TasksServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Page<Task> getAllTasks(Pageable page) {
        return taskRepository.findAll(page);
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            return task.get();
        }

        throw new ResourceNotFoundException("Task is not found by id " + id);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task saveTaskDetails(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskDetails(Long id, Task task) {
        Task existingTask = getTaskById(id);

        Optional.ofNullable(task.getName()).ifPresent(existingTask::setName);
        Optional.ofNullable(task.getStatus()).ifPresent(existingTask::setStatus);
        Optional.ofNullable(task.getDueDate()).ifPresent(existingTask::setDueDate);

        return taskRepository.save(existingTask);
    }

    @Override
    public Page<Task> findByStatus(String status, Pageable page) {
        return taskRepository.findByStatus(status, page);
    }

    @Override
    public Page<Task> findByName(String keyword, Pageable page) {
        return taskRepository.findByNameContaining(keyword, page);
    }

    @Override
    public Page<Task> findByDueDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }
        return taskRepository.findByDueDateBetween(startDate, endDate, page);
    }
}
