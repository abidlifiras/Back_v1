package com.cra.portfolio.controller;

import com.cra.portfolio.dto.ApplicationRequest;
import com.cra.portfolio.dto.ApplicationResponse;
import com.cra.portfolio.dto.DatabaseResponse;
import com.cra.portfolio.model.Contact;
import com.cra.portfolio.model.Server;
import com.cra.portfolio.service.ApplicationService;
import com.cra.portfolio.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApplicationController {
    //needs refactoring , optimizations , response entity<> , exception handling , logic for finds , pagination

    private final ApplicationService applicationService;
    private final ContactService contactService;

   /* @PostMapping("/{applicationId}/interfaces")
    public void addApplicationInterface(
            @PathVariable("applicationId") Integer applicationId,
            @RequestBody InterfaceRequest interfaceRequest
    ) {
        Integer appSrcId = interfaceRequest.getApplicationSrcId();
        Integer appDestId = interfaceRequest.getApplicationDestId();
        String protocol = interfaceRequest.getProtocol();
        if (!(appSrcId == null && appDestId == null))  {
            applicationService.addApplicationInterface(applicationId, appSrcId, appDestId, protocol);
        }




    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse createApplication(@RequestBody ApplicationRequest applicationRequest) {
        return (applicationService.createApplication(applicationRequest));
    }
    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationResponse> getAllApplications(){
        return applicationService.getAllApplications();
    }*/

    //this needs to be fixed !!! and adapted to get Archived apps  ...
    @GetMapping()
    public ResponseEntity<List<ApplicationResponse>> getAllApplications(
            @RequestParam(defaultValue = "10", required = false)
            Integer pageSize,
            @RequestParam(defaultValue = "0", required = false)
            Integer page

    ) {

        Pageable paging = PageRequest.of(page, pageSize);

        List<ApplicationResponse> applicationResponses =
                applicationService.getAllApplications(paging);

        return new ResponseEntity<>(
                applicationResponses, HttpStatus.CREATED);
    }


    @PutMapping("/{applicationId}/server/link/{serverId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApplicationResponse> addServerToApp(@PathVariable Integer applicationId, @PathVariable Integer serverId, @RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse updatedApplication = applicationService.addServerToApp(applicationId, serverId, applicationRequest);
        return ResponseEntity.ok(updatedApplication);
    }

    @PutMapping("/{applicationId}/server/unlink/{serverId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApplicationResponse> removeServerFromApp(@PathVariable Integer applicationId, @PathVariable Integer serverId) {
        ApplicationResponse updatedApplication = applicationService.removeServerFromApp(applicationId, serverId);
        return ResponseEntity.ok(updatedApplication);
    }


    @PutMapping("/{applicationId}/contact/link/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApplicationResponse> addContactToApp(@PathVariable Integer applicationId, @PathVariable Integer contactId, @RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse updatedApplication = applicationService.addContactToApp(applicationId, contactId, applicationRequest);
        return ResponseEntity.ok(updatedApplication);
    }

    @PutMapping("/{applicationId}/contact/unlink/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApplicationResponse> removeContactFromApp(@PathVariable Integer applicationId, @PathVariable Integer contactId) {
        ApplicationResponse updatedApplication = applicationService.removeContactFromApp(applicationId, contactId);
        return ResponseEntity.ok(updatedApplication);
    }


    @GetMapping("/archived")
    public ResponseEntity<List<ApplicationResponse>> getAllArchivedApplications(
            @RequestParam(defaultValue = "5", required = false)
            Integer pageSize,
            @RequestParam(defaultValue = "0", required = false)
            Integer page

    ) {

        Pageable paging = PageRequest.of(page, pageSize);

        List<ApplicationResponse> applicationResponses =
                applicationService.getAllArchivedApplications(paging);

        return new ResponseEntity<>(
                applicationResponses, HttpStatus.CREATED);
    }

    /*@GetMapping("/non-archived")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationResponse> getAllNonArchivedApplications(){
        return applicationService.getAllNonArchivedApplications();
    }*/
    @GetMapping("/{appId}/servers")
    public List<Server> getNonArchivedApplicationServers(@PathVariable Integer appId) {
        return applicationService.getNonArchivedApplicationServers(appId);
    }

    @GetMapping("/{appId}/contacts")
    public List<Contact> getNonArchivedApplicationContacts(@PathVariable Integer appId) {
        return applicationService.getNonArchivedApplicationContacts(appId);
    }

    @GetMapping("/non-archived")
    public ResponseEntity<List<ApplicationResponse>> getAllNonArchivedApplications(
            @RequestParam(defaultValue = "5", required = false)
            Integer pageSize,
            @RequestParam(defaultValue = "0", required = false)
            Integer page

    ) {

        Pageable paging = PageRequest.of(page, pageSize);

        List<ApplicationResponse> applicationResponses =
                applicationService.getAllNonArchivedApplications(paging);

        return new ResponseEntity<>(
                applicationResponses, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ApplicationResponse> deleteApplicationById(@PathVariable Integer id) {
        applicationService.deleteApplicationById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(@PathVariable Integer id, @RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse updatedApplication = applicationService.updateApplication(id, applicationRequest);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationSoft(@PathVariable Integer id) {
        applicationService.deleteApplicationSoft(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse findApplicationById(@PathVariable Integer id) {
        return applicationService.findById(id);
    }

    @GetMapping("/name/{appName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationResponse> findApplicationByName(@PathVariable String appName) {
        return applicationService.findByAppName(appName);

    }
    @GetMapping("/all")
    public List<ApplicationResponse> getNonArchivedApplication() {
        return applicationService.getApplicationAll();
    }


}
