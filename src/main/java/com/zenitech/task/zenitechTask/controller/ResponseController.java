package com.zenitech.task.zenitechTask.controller;

import com.zenitech.task.zenitechTask.exeption.ResourceNotFoundException;
import com.zenitech.task.zenitechTask.model.Response;
import com.zenitech.task.zenitechTask.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResponseController {

    @Autowired
    ResponseRepository responseRepository;

    /*
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
    */

    /*
    @GetMapping("/sorted_responses")
    public ResponseEntity<List<Response>> getAllResponses(@RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Response> responses = responseRepository.findAll(Sort.by(orders));

            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

    @GetMapping("/responses")
    public ResponseEntity<List<Response>> getAllResponses(@RequestParam(required = false) String title) {
            List<Response> responses = new ArrayList<Response>();

            if (title == null)
                responseRepository.findAll().forEach(responses::add);
            else
                responseRepository.findByTitleContaining(title).forEach(responses::add);

            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/responses/{id}")
    public ResponseEntity<Response> getResponseById(@PathVariable("id") long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Response with id = " + id));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/responses")
    public ResponseEntity<Response> createResponse(@RequestBody Response response) {
        Response _response = responseRepository.save(new Response(response.getTitle(), response.getDescription(), false));
        return new ResponseEntity<>(_response, HttpStatus.CREATED);
    }

    @PutMapping("/responses/{id}")
    public ResponseEntity<Response> updateResponse(@PathVariable("id") long id, @RequestBody Response response) {

        Response _response = responseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Response with id = " + id));

        _response.setTitle(response.getTitle());
        _response.setDescription(response.getDescription());
        _response.setPosted(response.isPosted());

        return new ResponseEntity<>(responseRepository.save(_response), HttpStatus.OK);
    }

    @DeleteMapping("/responses/{id}")
    public ResponseEntity<HttpStatus> deleteResponse(@PathVariable("id") long id) {
        responseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/responses")
    public ResponseEntity<HttpStatus> deleteAllResponses() {
        responseRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/responses/posted")
    public ResponseEntity<List<Response>> findByPosted() {
        List<Response> responses = responseRepository.findByPosted(true);

        if (responses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
