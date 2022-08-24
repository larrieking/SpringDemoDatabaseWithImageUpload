package com.example.springdemodatabase.controller;

import com.example.springdemodatabase.model.Status;
import com.example.springdemodatabase.model.Tutorial;
import com.example.springdemodatabase.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>>getAllTutorials(@RequestParam(required = false)String title){
        try{
            List<Tutorial>tutorials = new ArrayList<>();
            if ((title == null))
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/tutorial", consumes = {"multipart/form-data"}, headers = ("content-type=multipart/*"))
    public ResponseEntity<Tutorial> createTutorial(@RequestParam("file") MultipartFile mp, @RequestParam("description")String description, @RequestParam("title")String title, @RequestParam("published")boolean published, @RequestParam("status") String status){
            Status postedStatus = Status.valueOf(status.toUpperCase());
        try{
            byte[]fileBte = mp.getBytes();




            Tutorial _tutorial = tutorialRepository.save(new Tutorial(title, description, published, fileBte, postedStatus
            ));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        }
        catch(MethodArgumentTypeMismatchException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        catch (MaxUploadSizeExceededException e){

            return new ResponseEntity<>(null, HttpStatus.valueOf("Max file size exceeded. allowed size is 20kb"+" What you"));

        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
