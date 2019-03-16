package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask/{id}")
    public TaskDto getTask(@RequestParam("id") Long taskId){
        return new TaskDto(1L, "test title", "test_content");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask/{id}")
    public void deleteTask(@RequestParam("id") Long taskId){

    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask/taskDto")
    public TaskDto updateTask(@RequestParam TaskDto taskDto){
        return new TaskDto(1L,  "Edited test title", "test content");
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask/taskDto")
    public void createTask(@RequestParam TaskDto taskDto){

    }
}
