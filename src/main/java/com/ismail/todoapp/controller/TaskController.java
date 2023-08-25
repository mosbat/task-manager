package com.ismail.todoapp.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private long taskIdCounter = 1;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", tasks);
        return "task-manager";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam String newTask) {
        Task task = new Task(taskIdCounter, newTask, false);
        tasks.add(task);
        taskIdCounter++;
        return "redirect:/";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping("/toggleDone/{id}")
    @ResponseBody
    public String toggleDone(@PathVariable long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDone(!task.isDone());
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/updateTaskName/{id}/{description}")
    @ResponseBody
    public String updateDescription(@PathVariable long id, @PathVariable String description) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/removeTask/{id}")
    @ResponseBody
    public void removeTask(@PathVariable long id) {
        tasks.removeIf(task -> task.getId() == id);
    }
}

@Getter
@Setter
class Task {
    private long id;
    private String description;
    private boolean done;

    public Task(long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

}
