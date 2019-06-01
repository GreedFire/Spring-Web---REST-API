package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testTaskRepository() {
        //Given
        Task task1 = new Task("title1", "content1");
        Task task2 = new Task("title2", "content2");

        //When
        //SAVE TEST
        task1 = taskRepository.save(task1);
        task2 = taskRepository.save(task2);

        //GET TEST
        List<Task> taskList = taskRepository.findAll();
        Optional<Task> temporaryTask = taskRepository.findById(task2.getId());

        //Then
        Assert.assertEquals(2, taskList.size());
        Assert.assertEquals("title2", temporaryTask.get().getTitle());

        //Cleanup
        taskRepository.deleteById(task1.getId());
        taskRepository.deleteById(task2.getId());
    }

    @Test
    public void testUpdate(){
        //Given
        Task task1 = new Task("title1", "content1");
        //When
        task1 = taskRepository.save(task1);
        task1 = taskRepository.save(new Task(task1.getId(), "titleX", "contentX"));
        //Then
        Assert.assertEquals("contentX", task1.getContent());
        //CleanUp
        taskRepository.deleteById(task1.getId());
    }
}