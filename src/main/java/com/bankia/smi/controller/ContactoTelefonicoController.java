package com.bankia.smi.controller;


import java.util.HashMap;

import com.bankia.smi.ContactoTelefonicoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.bankia.smi.service.RedisService;


import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/contactoTelefonico", method = RequestMethod.GET)
public class ContactoTelefonicoController {

    @Autowired
    private RedisService redisService;

    private static final String SUCCESS = "success";
    private static final String MESSAGE = "message";
    private static final String URL_SERVICE = "/api/contactoTelefonico/";


    @RequestMapping(value = "/{centro}/{prefijo}/{telefono}", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> contactoTelefonico(@PathVariable String centro, @PathVariable String prefijo, @PathVariable String telefono) {

        boolean success = false;
        String message = null;
        boolean sendRedis;
        HashMap<String, Object> resp = null;

        try {

            ContactoTelefonicoUtils.validaTrama(centro, prefijo, telefono);
            String trama = ContactoTelefonicoUtils.construyeTrama(centro, prefijo, telefono);
            sendRedis = redisService.sendRedis(trama);
            if (sendRedis) {
                log.info("AUDIT [" + URL_SERVICE + centro + "/" + prefijo + "/" + telefono + "]");
                success = sendRedis;
            }
        } catch (Exception ex) {
            success = false;
            message = ex.getMessage();
            log.error(message + ". [" + URL_SERVICE + centro + "/" + prefijo + "/" + telefono + "]");
        }

        return getResponse(success, message);

    }


    @RequestMapping(value = {"",
            "/*",
            "/{centro}/*",
            "/{centro}/{prefijo}/*",
            "/{centro}/{prefijo}/{telefono}/*"},
            method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> error() {
        return getResponse(false, HttpStatus.BAD_REQUEST.name());
    }


    private HashMap<String, Object> getResponse(boolean success, String message) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put(SUCCESS, success);
        if (null != message)
            ret.put(MESSAGE, message);

        return ret;
    }


}

