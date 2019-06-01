package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.common.base.Verify;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.ContentType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        when(taskMapper.maptoTaskDtoList(tasks)).thenReturn(taskDtos);
        when(taskService.getAllTasks()).thenReturn(tasks);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void testGetTask() throws Exception{
        //Given
        Task task = new Task(1, "title", "content");
        TaskDto taskDto = new TaskDto(1, "title", "content");
        Optional<Task> opt = java.util.Optional.of(task);

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(taskService.getTask(1L)).thenReturn(opt);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

    }


    @Test
    public void testDeleteTask() throws Exception{
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask").param("taskId", "1"))
                .andExpect(status().isOk());

        Mockito.verify(taskService, times(1)).deleteTask(Mockito.anyLong());
    }

    @Test
    public void testUpdateTask() throws Exception{
        //Given
        Task task = new Task(1, "title", "content");
        TaskDto taskDto = new TaskDto(1, "title", "content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(taskService.saveTask(Mockito.any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")));
    }

    @Test // to też
    public void testCreateTask() throws Exception{
        //Given
        Task task = new Task(1, "title", "content");
        TaskDto taskDto = new TaskDto(1, "title", "content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskService.saveTask(Mockito.any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        Mockito.verify(taskService, times(1)).saveTask(Mockito.any());
    }
}