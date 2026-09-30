package com.example.hexagonal.application.service;

import com.example.hexagonal.infrastructure.persistence.InMemoryTaskRepository;
import com.example.hexagonal.application.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {
    private final InMemoryTaskRepository repository = new InMemoryTaskRepository();
    private final TaskService service = new TaskService(
            repository,
            Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC)
    );

    @Test
    void createsAndListsTask() {
        Task created = service.create("  Preparar entrega  ");

        assertEquals("Preparar entrega", created.title());
        assertEquals(Instant.parse("2026-01-01T00:00:00Z"), created.createdAt());
        assertEquals(java.util.List.of(created), service.findAll());
    }

    @Test
    void rejectsBlankTitleWithoutSavingTask() {
        assertThrows(IllegalArgumentException.class, () -> service.create("   "));

        assertEquals(java.util.List.of(), repository.findAll());
    }
}