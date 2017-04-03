package com.bankia.smi;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication
public class ContactosClientesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactosClientesServiceApplication.class, args);
    }


}
