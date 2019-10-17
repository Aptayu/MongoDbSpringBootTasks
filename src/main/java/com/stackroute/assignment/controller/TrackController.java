package com.stackroute.assignment.controller;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.exception.TrackNotFoundException;
import com.stackroute.assignment.trackservice.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api/v2")
public class TrackController {

        TrackService trackService;


        public TrackController(com.stackroute.assignment.trackservice.TrackService trackService) {
            this.trackService = trackService;
        }
        @PostMapping("/user")
        public ResponseEntity<?> saveUser(@RequestBody Track track) {
            ResponseEntity responseEntity;
            try {
                responseEntity = new ResponseEntity(trackService.saveTrack(track), HttpStatus.CREATED);
            }catch(Exception e){
                responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return responseEntity;

        }

        @GetMapping("/user")
        public ResponseEntity<?> getAllTracks(){
            ResponseEntity responseEntity;
            try{
                responseEntity= new ResponseEntity(trackService.getAllTracks(), HttpStatus.OK);
            }
            catch(Exception e){
                responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

            }
            return responseEntity;

        }
        @PutMapping("/user")
        public ResponseEntity updateComments(@RequestBody Track track){
            ResponseEntity responseEntity;
            try{
                trackService.saveTrack(track);
                responseEntity= new ResponseEntity("Successfully Updated", HttpStatus.OK);
            }
            catch(Exception e){
                responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

            }
            return responseEntity;

        }
        @DeleteMapping("/user")
        public ResponseEntity deleteComments(@RequestParam(value = "search") int search){
            ResponseEntity responseEntity;
            try{
                responseEntity= new ResponseEntity(trackService.deleteTrackById(search), HttpStatus.OK);
            }
            catch(Exception e){
                responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

            }
            return responseEntity;
        }
    @RequestMapping("/findByName")
    public ResponseEntity findByName(@RequestParam(value = "search") String search) {
        ResponseEntity responseEntity;

        try {

            responseEntity = new ResponseEntity<List<Track>>(trackService.getByTrackName(search), HttpStatus.OK);


        } catch (Exception e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

        }
        return responseEntity;


    }
}

