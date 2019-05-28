package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToTrelloCard(){
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("name", "desc", "1", "1");
        //When
        TrelloCard card = trelloMapper.mapToCard(cardDto);
        //Then
        Assert.assertEquals(cardDto.getName(), card.getName());
        Assert.assertEquals(cardDto.getDescription(), card.getDescription());
    }

    @Test
    public void testMapToTrelloCardDto(){
        //Given
        TrelloCard card = new TrelloCard("name", "desc", "1", "1");
        //When
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(card);
        //Then
        Assert.assertEquals(card.getName(), cardDto.getName());
        Assert.assertEquals(card.getDescription(), cardDto.getDescription());
    }

    @Test
    public void testMapToTrelloBoards(){
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "list1", true));
        trelloListDto.add(new TrelloListDto("2", "list2", false));
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1", "name1", trelloListDto));
        trelloBoardsDto.add(new TrelloBoardDto("2", "name2", trelloListDto));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        Assert.assertEquals(trelloBoardsDto.size(), trelloBoards.size());
        Assert.assertEquals(trelloBoardsDto.get(1).getId(), trelloBoards.get(1).getId());
        Assert.assertEquals(trelloBoardsDto.get(0).getLists().get(0).getId(), trelloBoards.get(0).getLists().get(0).getId());
    }

    @Test
    public void testMapToTrelloBoardsDto(){
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "list1", true));
        trelloList.add(new TrelloList("2", "list2", false));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "name1", trelloList));
        trelloBoards.add(new TrelloBoard("2", "name2", trelloList));
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        Assert.assertEquals(trelloBoards.size(), trelloBoardsDto.size());
        Assert.assertEquals(trelloBoards.get(1).getId(), trelloBoardsDto.get(1).getId());
        Assert.assertEquals(trelloBoards.get(0).getLists().get(0).getId(), trelloBoardsDto.get(0).getLists().get(0).getId());
    }
}
