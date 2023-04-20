package com.cra.portfolio.service;

import com.cra.portfolio.dto.ApplicationRequest;
import com.cra.portfolio.dto.ApplicationResponse;
import com.cra.portfolio.exception.NotFoundCustomException;
import com.cra.portfolio.model.Application;
import com.cra.portfolio.model.Contact;
import com.cra.portfolio.model.Server;
import com.cra.portfolio.repository.ApplicationRepository;
import com.cra.portfolio.repository.ContactRepository;
import com.cra.portfolio.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j

public class ApplicationService {

    //implement sort

    private final ApplicationRepository applicationRepository;
    private final ServerRepository serverRepository;

    private final ContactRepository contactRepository;


    //add server object to application
    //get by name id ... only if not archived !!!
    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {
        LocalDateTime created = LocalDateTime.now() ;
        Application application = Application.builder()
                .appName(applicationRequest.getAppName())
                .appDescription(applicationRequest.getAppDescription())
                .createdAt(created)
                .build();
        applicationRepository.save(application);
        log.info("application {} created successfully", application.getId());
        return mapToApplicationResponse(application);
    }



    /*public List<ApplicationResponse> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(this::mapToApplicationResponse).toList();

    }*/

    /*public void createApplicationServer(ApplicationRequest applicationRequest) {
        Application application = new Application();
        application.setAppName(applicationRequest.getAppName());
        application.setAppDescription(applicationRequest.getAppDescription());


        Set<Server> servers = new HashSet<>();
        for (Integer serverId : applicationRequest.getServers()) {
            Optional<Server> optionalServer = serverRepository.findById(serverId);
            optionalServer.ifPresent(servers::add);
        }
        application.setServers(servers);

        applicationRepository.save(application);
    }*/

    /*public ApplicationResponse addServerToApp(Integer applicationId,Integer serverId, ApplicationRequest applicationRequest) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            List<Server> serverList ; //init servers list
            Server server = serverRepository.findById(serverId).get(); //got the server I need to add

            //get list of servers in my app
            serverList = optionalApplication.get().getServers();
            //add the server to the list
            serverList.add(server);

            synchronized (serverList) {
                List<Server> serverListCopy = new ArrayList<>(serverList);
                Set<Server> serverSet = new HashSet<>(serverListCopy);
                List<Server> uniqueServerList = new ArrayList<>(serverSet);
                for (Server s : serverList) {
                    s.getApplications().add(application);
                }
                serverList = serverRepository.saveAll(uniqueServerList);
            }*/

            /*synchronized (serverList) {
                List<Server> serverListCopy = new ArrayList<>(serverList);
                for (Server s : serverListCopy) {
                    s.getApplications().add(application);
                }
                serverList = serverRepository.saveAll(serverListCopy);
            }*/
           /* List<Server> serverListCopy = new ArrayList<>(serverList);
            for (Server s : serverListCopy) {
                s.getApplications().add(application);
            }*/
           /* for (Server s : serverList) {
               s.getApplications().add(application);
            }*/
            //serverList=serverRepository.saveAll(serverList);

/*
            application.setAppName(applicationRequest.getAppName());
            application.setAppDescription(applicationRequest.getAppDescription());
            application.setServers(serverList);
            Application updatedApplication = applicationRepository.save(application);
            return mapToApplicationResponse2(updatedApplication);
        } else {
            throw new NotFoundCustomException("Application not found with id: " + applicationId);
        }
    }*/
    public ApplicationResponse addServerToApp(Integer applicationId, Integer serverId, ApplicationRequest applicationRequest) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Server server = serverRepository.findById(serverId).orElseThrow(() -> new NotFoundCustomException("Server not found with id: " + serverId));
            if (server.getDeletedAt()!=null){
                throw new NotFoundCustomException("Server not found with id : " + serverId);
            }
            List<Server> servers = application.getServers();
            if (!servers.contains(server)) {
                servers.add(server);
                server.getApplications().add(application);
                 application.setServers(servers);
                application.setAppName(applicationRequest.getAppName());
                application.setAppDescription(applicationRequest.getAppDescription());
                application.setModifiedAt(LocalDateTime.now());
                 Application updatedApplication = applicationRepository.save(application);
            return mapToApplicationResponse(updatedApplication);
        } else {
            return mapToApplicationResponse(application);
        }
    } else {
        throw new NotFoundCustomException("Application not found with id: " + applicationId);
    }
}

    public ApplicationResponse addContactToApp(Integer applicationId, Integer contactId,ApplicationRequest applicationRequest) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new NotFoundCustomException("Contact not found with id: " + contactId));
            if (contact.getDeletedAt()!=null){
                throw new NotFoundCustomException("Server not found with id : " + contactId);
            }
            List<Contact> contacts = application.getContacts();
            if (!contacts.contains(contact)) {
                contacts.add(contact);
                contact.getApplications().add(application);
                application.setContacts(contacts);
                application.setAppName(applicationRequest.getAppName());
                application.setAppDescription(applicationRequest.getAppDescription());
                application.setModifiedAt(LocalDateTime.now());
                Application updatedApplication = applicationRepository.save(application);
                return mapToApplicationResponse(updatedApplication);
            } else {
                return mapToApplicationResponse(application);
            }
        } else {
            throw new NotFoundCustomException("Application not found with id: " + applicationId);
        }
    }

    public ApplicationResponse removeServerFromApp(Integer applicationId, Integer serverId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Server server = serverRepository.findById(serverId).orElseThrow(() -> new NotFoundCustomException("Server not found with id: " + serverId));

            List<Server> servers = application.getServers();
            if (servers.contains(server)) {
                servers.remove(server);
                server.getApplications().remove(application);

                application.setServers(servers);
                Application updatedApplication = applicationRepository.save(application);
                return mapToApplicationResponse(updatedApplication);
            } else {
                return mapToApplicationResponse(application);
            }
        } else {
            throw new NotFoundCustomException("Application not found with id: " + applicationId);
        }
    }

    public ApplicationResponse removeContactFromApp(Integer applicationId, Integer contactId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new NotFoundCustomException("Contact not found with id: " + contactId));

            List<Contact> contacts= application.getContacts();
            if (contacts.contains(contact)) {
                contacts.remove(contact);
                contact.removeApplication(application);

                application.setContacts(contacts);
                Application updatedApplication = applicationRepository.save(application);
                return mapToApplicationResponse(updatedApplication);
            } else {
                return mapToApplicationResponse(application);
            }
        } else {
            throw new NotFoundCustomException("Application not found with id: " + applicationId);
        }
    }

//add get application servers by id and get only the not archived servers
public List<Server> getNonArchivedApplicationServers(Integer appId) {
    Optional<Application> optionalApplication = applicationRepository.findById(appId);
    if (optionalApplication.isPresent()) {
        Application application = optionalApplication.get();
        List<Server> servers = new ArrayList<>();
        for (Server server : application.getServers()) {
            for (Application serverApp : server.getApplications()) {
                if (serverApp.getId().equals(application.getId()) && server.getDeletedAt()==null) {
                    servers.add(server);
                    break;
                }
            }
        }
        return servers;
    } else {
        throw new NotFoundCustomException("Application not found with id: " + appId);
    }
}
    public List<Contact> getNonArchivedApplicationContacts(Integer appId) {
        Optional<Application> optionalApplication = applicationRepository.findById(appId);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            List<Contact> contacts = new ArrayList<>();
            for (Contact contact : application.getContacts()) {
                for (Application contactApp : contact.getApplications()) {
                    if (contactApp.getId().equals(application.getId()) && contact.getDeletedAt()==null) {
                        contacts.add(contact);
                        break;
                    }
                }
            }
            return contacts;
        } else {
            throw new NotFoundCustomException("Application not found with id: " + appId);
        }
    }







    public List<ApplicationResponse> getAllApplications(Pageable paging) {

        Iterable<Application> apps = applicationRepository.findAll(paging);

        List<ApplicationResponse> applicationResponses = new ArrayList<>();

        apps.forEach( application ->
            applicationResponses.add(ApplicationResponse
                    .builder()
                    .id(application.getId())
                    .appName(application.getAppName())
                    .appDescription(application.getAppDescription())
                    .servers(application.getServers())
                            .contacts((application.getContacts()))
                            .modifiedAt(application.getModifiedAt())
                            .deletedAt(application.getDeletedAt())
                            .createdAt(application.getCreatedAt())
                    .build())
    );

        return applicationResponses;
    }

    public void deleteApplicationById(Integer id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            for (Server server : application.getServers()) {
                server.getApplications().remove(application);
            }
            for (Contact contact : application.getContacts()) {
                contact.getApplications().remove(application);
            }
            applicationRepository.delete(optionalApplication.get());
        } else {
            throw new NotFoundCustomException("Application not found with id: " + id);
        }
    }

    public ApplicationResponse updateApplication(Integer id, ApplicationRequest applicationRequest) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        LocalDateTime updated =LocalDateTime.now();

        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            application.setAppName(applicationRequest.getAppName());
            application.setAppDescription(applicationRequest.getAppDescription());
            application.setModifiedAt(updated);
            Application updatedApplication = applicationRepository.save(application);
            return mapToApplicationResponse(updatedApplication);
        } else {
            throw new NotFoundCustomException("Application not found with id: " + id);
        }
    }

    /*private ApplicationResponse mapToApplicationResponse(Application application) {
        return ApplicationResponse.builder().id(application.getId())
                .appName(application.getAppName())
                .appDescription(application.getAppDescription())
                .isArchived(application.getIsArchived())
                .build();
    }*/
    private ApplicationResponse mapToApplicationResponse(Application application) {
        return ApplicationResponse.builder().id(application.getId())
                .appName(application.getAppName())
                .appDescription(application.getAppDescription())
                .servers(application.getServers())
                .contacts(application.getContacts())
                .createdAt(application.getCreatedAt())
                .deletedAt(application.getDeletedAt())
                .modifiedAt(application.getModifiedAt())
                .build();
    }


    public void deleteApplicationSoft(Integer id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        LocalDateTime deleted =LocalDateTime.now();

        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            application.setDeletedAt(deleted);
            applicationRepository.save(application);
            log.info("application {} archived successfully", application.getId());
        } else {
            throw new NotFoundCustomException("Application not found with id: " + id);
        }


    }

    /*public List<ApplicationResponse> getAllNonArchivedApplications(Pageable paging) {

        Iterable<Application> apps = applicationRepository.findAllByIsArchivedFalse(paging);

        List<ApplicationResponse> applicationResponses = new ArrayList<>();

        apps.forEach(application ->
                applicationResponses.add(ApplicationResponse.builder()
                        .id(application.getId())
                        .appName(application.getAppName())
                        .appDescription(application.getAppDescription())
                        .isArchived(application.getIsArchived())
                        .servers(application.getServers())
                        .build())
        );

        return applicationResponses;
    }*/




    /*public List<ApplicationResponse> getAllNonArchivedApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .filter(application -> !application.getIsArchived())
                .map(this::mapToApplicationResponse)
                .toList();
    }*/
    public List<ApplicationResponse> getAllNonArchivedApplications(Pageable paging) {

        Iterable<Application> apps = applicationRepository.findAllByDeletedAtNull(paging);

        List<ApplicationResponse> applicationResponses = new ArrayList<>();

        apps.forEach( application ->
                applicationResponses.add(ApplicationResponse
                        .builder()
                        .id(application.getId())
                        .appName(application.getAppName())
                        .appDescription(application.getAppDescription())
                        .servers(application.getServers())
                        .contacts(application.getContacts())
                        .createdAt(application.getCreatedAt())
                                .deletedAt(application.getDeletedAt())
                                .modifiedAt(application.getModifiedAt())
                        .build())
        );

        return applicationResponses;
    }

    public List<ApplicationResponse> getAllArchivedApplications(Pageable paging) {

        Iterable<Application> apps = applicationRepository.findAllByDeletedAtIsNotNull(paging);

        List<ApplicationResponse> applicationResponses = new ArrayList<>();

        apps.forEach( application ->
                applicationResponses.add(ApplicationResponse
                        .builder()
                        .id(application.getId())
                        .appName(application.getAppName())
                        .appDescription(application.getAppDescription())
                        .servers(application.getServers())
                        .contacts(application.getContacts())
                        .createdAt(application.getCreatedAt())
                        .deletedAt(application.getDeletedAt())
                        .modifiedAt(application.getModifiedAt())
                        .build())
        );

        return applicationResponses;
    }
//and not archived
    public List<ApplicationResponse> findByAppName(String appName) {

        List<Application> apps = applicationRepository.findByAppName(appName);
        if (apps != null) {
            return apps.stream()
                    .filter(app -> app.getDeletedAt()==null)
                    .map(this::mapToApplicationResponse)
                    .toList();
        } else {
            throw new NotFoundCustomException("Application not found with name: " + appName);
        }
    }

//and not archived
    public ApplicationResponse findById(Integer id) {
        Optional<Application> app = applicationRepository.findById(id);

        if (app.isPresent()) {
            Application application = app.get();
            if (application.getDeletedAt()!=null) {
                throw new NotFoundCustomException("Application not found with id: " + id);
            }
            return ApplicationResponse
                    .builder()
                    .id(application.getId())
                    .appName(application.getAppName())
                    .appDescription(application.getAppDescription())
                    .servers(application.getServers())
                    .contacts(application.getContacts())
                    .modifiedAt(application.getModifiedAt())
                    .deletedAt(application.getDeletedAt())
                    .createdAt(application.getCreatedAt())
                    .build();


            //return apps.stream().map(this::mapToApplicationResponse).toList();
        } else {
            throw new NotFoundCustomException("Application not found with id: " + id);
        }

    }

    public List<ApplicationResponse> getApplicationAll() {
        Iterable<Application> apps = applicationRepository.findAllByDeletedAtNull();

        List<ApplicationResponse> applicationResponses = new ArrayList<>();

        apps.forEach( application ->
                applicationResponses.add(ApplicationResponse
                        .builder()
                        .id(application.getId())
                        .appName(application.getAppName())
                        .appDescription(application.getAppDescription())
                        .servers(application.getServers())
                        .contacts(application.getContacts())
                        .createdAt(application.getCreatedAt())
                        .deletedAt(application.getDeletedAt())
                        .modifiedAt(application.getModifiedAt())
                        .build())
        );

        return applicationResponses;
    }
}
