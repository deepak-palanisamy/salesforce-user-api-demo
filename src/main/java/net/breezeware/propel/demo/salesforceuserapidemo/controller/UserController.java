package net.breezeware.propel.demo.salesforceuserapidemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.propel.demo.salesforceuserapidemo.service.SalesforceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final SalesforceService salesforceService;

    // Retrieve all users
    @RequestMapping
    public Object getUsers() {
        log.info("Entering getUsers()");
        Object users = salesforceService.getUsers();
        log.info("Leaving getUsers()");
        return users;
    }

}
