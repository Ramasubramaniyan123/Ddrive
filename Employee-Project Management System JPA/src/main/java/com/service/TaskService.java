package com.service;

import com.dao.TaskDAO;
import com.model.Task;

import java.util.List;

public class TaskService {

    private TaskDAO taskDAO = new TaskDAO();

    public void createTask(Task task) {
        taskDAO.save(task);
    }

    public Task getTask(Long id) {
        return taskDAO.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    public void deleteTask(Long id) {
        taskDAO.delete(id);
    }
}