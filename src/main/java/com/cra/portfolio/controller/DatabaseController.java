package com.cra.portfolio.controller;

import com.cra.portfolio.dto.DatabaseRequest;
import com.cra.portfolio.dto.DatabaseResponse;
import com.cra.portfolio.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/databases")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DatabaseController {
    private final DatabaseService databaseService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDatabase(@RequestBody DatabaseRequest databaseRequest){
        databaseService.createDatabase(databaseRequest);
    }

    @GetMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE},
            produces= {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<List<DatabaseResponse>> getDatabases(
            @RequestParam(defaultValue = "5", required = false)
            Integer pageSize,
            @RequestParam(defaultValue = "0", required = false)
            Integer page

    ) {

        Pageable paging  = PageRequest.of(page, pageSize);

        List<DatabaseResponse> databaseResponses =
                databaseService.getAllDatabases(paging);

        return new ResponseEntity<>(
                databaseResponses, HttpStatus.CREATED);
    }

    @PutMapping("/{databaseId}/server/link/{serverId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DatabaseResponse> addServerToDb(@PathVariable Integer databaseId , @PathVariable Integer serverId , @RequestBody DatabaseRequest databaseRequest) {
        DatabaseResponse updatedDatabase = databaseService.addServerToDb(databaseId ,serverId, databaseRequest);
        return ResponseEntity.ok(updatedDatabase);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<DatabaseResponse> deleteDatabaseById(@PathVariable Integer id) {
        databaseService.deleteDatabaseById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatabaseResponse> updateDatabase(@PathVariable Integer id, @RequestBody DatabaseRequest databaseRequest) {
        DatabaseResponse updatedDatabase = databaseService.updateDatabase(id, databaseRequest);
        return ResponseEntity.ok(updatedDatabase);
    }





}
