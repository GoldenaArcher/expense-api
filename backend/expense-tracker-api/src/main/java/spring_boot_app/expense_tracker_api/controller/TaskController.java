package spring_boot_app.expense_tracker_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_boot_app.expense_tracker_api.entity.Task;
import spring_boot_app.expense_tracker_api.service.TaskService;

import java.sql.Date;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public Page<Task> getAllTasks(Pageable page) {
        return taskService.getAllTasks(page);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/tasks")
    public void deleteTaskById(@RequestParam("id") Long id) {
        taskService.deleteTaskById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/tasks")
    public Task saveTaskDetails(@RequestBody Task task) {
        return taskService.saveTaskDetails(task);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTaskDetails(@RequestBody Task task, @PathVariable("id") Long id) {
        return taskService.updateTaskDetails(id, task);
    }

    @GetMapping("/tasks/filterByStatus")
    public Page<Task> filterTasksByStatus(@RequestParam("status") String status, Pageable page) {
        return taskService.findByStatus(status, page);
    }

    @GetMapping("/tasks/filterByName")
    public Page<Task> filterTasksByName(@RequestParam("keyword") String keyword, Pageable page) {
        return taskService.findByName(keyword, page);
    }

    @GetMapping("/tasks/filterByDueDate")
    public Page<Task> filterTasksByDueDate(@RequestParam(required = false, name = "startDate") Date startDate,
                                        @RequestParam(required = false, name = "endDate") Date endDate,
                                        Pageable page) {
        return taskService.findByDueDate(startDate, endDate, page);
    }
}
