package main;

import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    Object locker = new Object();

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> get(@PathVariable int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(taskOptional.get(), HttpStatus.OK);

    }

    @GetMapping("/tasks/")
    public ResponseEntity<List<Task>> list() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task :
                taskIterable) {
            tasks.add(task);
        }
        return new ResponseEntity(tasks, HttpStatus.OK);

    }

    @PostMapping("/tasks/")
    public ResponseEntity<Task> add(Task task) {
        synchronized (locker) {
            Task newTask = taskRepository.save(task);
            return new ResponseEntity(newTask, HttpStatus.OK);
        }
    }

    @PostMapping("/task/{id}")
    public ResponseEntity<HttpStatus> putSubTask(@PathVariable int id) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);

    }

    @PutMapping("/tasks/")
    public ResponseEntity<HttpStatus> addSeveralTasks(List<Task> taskList) {
        synchronized (locker) {
            boolean flag = false;
            for (Task task : taskList) {
                Optional<Task> taskOptional = taskRepository.findById(task.getId());

                if (!taskOptional.isPresent()) {
                    flag = true;
                } else {
                    taskRepository.deleteById(task.getId());
                    taskRepository.save(task);
                }
            }

            if (flag) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//should be returned list of not updated tasks
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> changeTask(@PathVariable int id, Task changedTask) {
        synchronized (locker) {
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (!taskOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            taskRepository.deleteById(id);
            taskRepository.save(changedTask);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/tasks/")
    public ResponseEntity<HttpStatus> deleteAll() {
        synchronized (locker) {
            taskRepository.deleteAll();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id) {
        synchronized (locker) {
            Optional<Task> taskOptional = taskRepository.findById(id);
            if (!taskOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            taskRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
