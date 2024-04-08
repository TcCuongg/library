package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.more.AuthorNew;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RedisTemplate redisStringTemplate;

    public List<String> findAllAuthorName(){
        List<String> stringList = new ArrayList<>();

        String redisKey = "findAllAuthorName";

        boolean hasKey = redisStringTemplate.hasKey(redisKey);

        if(hasKey){
            stringList = redisStringTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            stringList = authorRepository.findAllAuthorName();
            if(!stringList.isEmpty()){
                redisStringTemplate.opsForList().rightPushAll(redisKey, stringList);
            }
        }
        return stringList;
    }

    public List<Author> getAllAuthor(int count, int size){
        Pageable pageable = PageRequest.of(count, size, Sort.by(Sort.Direction.ASC, "name"));
        return authorRepository.getAllAuthor(pageable);
    }

    public List<Author> getAuthorByNameOrPhone(String request, int count, int size){
        List<Author> authorList = new ArrayList<>();
        Pageable pageable = PageRequest.of(count, size, Sort.by(Sort.Direction.ASC, "name"));
        if(request != ""){
            if(!authorRepository.findAllByName(request, pageable).isEmpty()){
                authorList = authorRepository.findAllByName(request, pageable);
            }
            else if(!authorRepository.findAllByPhone(Long.parseLong(request), pageable).isEmpty()){
                authorList = authorRepository.findAllByPhone(Long.parseLong(request), pageable);
            }
        }return authorList;
    }

    public List<Author> addNewAuthor(String name, String phone, String date, String address){
        List<Author> authorList = new ArrayList<>();
        if(name != "" && phone != "" && date != "" && address != ""){
            if(!date.contains("T") && !date.contains(" ")){
                date += "T00:00:00";
            } else{
                date = date.replace(" ", "T");
            }
            Author author = new Author();
            author.setName(name);
            author.setPhone(Long.parseLong(phone));
            author.setDate(LocalDateTime.parse(date));
            author.setAddress(address);
            authorRepository.save(author);
            authorList.add(authorRepository.findFirstByOrderByIdDesc());
        }
        return authorList;
    }

    public List<Author> updateAuthor(AuthorNew authorNew){
        List<Author> authorList = new ArrayList<>();
        if(authorNew.getName() != "" && authorNew.getPhone() != "" && authorNew.getDate() != "" && authorNew.getAddress() != ""){
            String date = "";
            if(!authorNew.getDate().contains("T") && !authorNew.getDate().contains(" ")){
                date = authorNew.getDate() + "T00:00:00";
            } else{
                date = authorNew.getDate().replace(" ", "T");
            }
            Author author = authorRepository.findFirstById(authorNew.getId());
            author.setName(authorNew.getName());
            author.setPhone(Long.parseLong(authorNew.getPhone()));
            author.setDate(LocalDateTime.parse(date));
            author.setAddress(authorNew.getAddress());
            authorRepository.save(author);
            authorList.add(author);
        }
        return authorList;
    }

    public List<Author> selectionAuthorWithBirthDay(String timeStart, String timeEnd, int count, int size){
        Pageable pageable = PageRequest.of(count, size, Sort.by(Sort.Direction.ASC, "name"));
        if(Objects.equals(timeStart, "")) timeStart = LocalDateTime.MIN.toString();
        else if(!timeStart.contains("T") && !timeStart.contains(" ")){
            timeStart += "T00:00:00";
        } else{
            timeStart = timeStart.replace(" ", "T");
        }
        if(Objects.equals(timeEnd, "")) timeEnd = "9999-12-31T23:59:59";
        else if(!timeEnd.contains("T") && !timeEnd.contains(" ")){
            timeEnd += "T00:00:00";
        } else{
            timeEnd = timeEnd.replace(" ", "T");
        }
        return authorRepository.selectionAuthorWithBirthDay(LocalDateTime.parse(timeStart), LocalDateTime.parse(timeEnd), pageable);
    }



    public int getCountAllAuthor(){
        return authorRepository.getCountAllAuthor();
    }
    public int getCountAuthorByNameOrPhone(String request){
        if(authorRepository.countAllByName(request) != 0) return authorRepository.countAllByName(request);
        else return authorRepository.countAllByPhone(Long.parseLong(request));
    }
    public int countSelectionAuthorWithBirthDay(String timeStart, String timeEnd){
        if(Objects.equals(timeStart, "")) timeStart = LocalDateTime.MIN.toString();
        else if(!timeStart.contains("T") && !timeStart.contains(" ")){
            timeStart += "T00:00:00";
        } else{
            timeStart = timeStart.replace(" ", "T");
        }
        if(Objects.equals(timeEnd, "")) timeEnd = "9999-12-31T23:59:59";
        else if(!timeEnd.contains("T") && !timeEnd.contains(" ")){
            timeEnd += "T00:00:00";
        } else{
            timeEnd = timeEnd.replace(" ", "T");
        }
        return authorRepository.countSelectionAuthorWithBirthDay(LocalDateTime.parse(timeStart), LocalDateTime.parse(timeEnd));
    }
}
