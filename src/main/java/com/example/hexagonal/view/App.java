package com.example.hexagonal.view;

import com.example.hexagonal.application.service.TaskService;
import com.example.hexagonal.infrastructure.persistence.InMemoryTaskRepository;
import com.example.hexagonal.infrastructure.ui.console.TaskConsoleAdapter;

import java.time.Clock;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();
        TaskService taskService = new TaskService(repository, Clock.systemUTC());
        TaskConsoleAdapter console = new TaskConsoleAdapter(taskService, taskService);
        console.run(System.in, System.out);
    }
}