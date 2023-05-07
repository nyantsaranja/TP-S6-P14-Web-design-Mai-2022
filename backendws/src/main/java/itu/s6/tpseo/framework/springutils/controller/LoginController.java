package itu.s6.tpseo.framework.springutils.controller;


import itu.s6.tpseo.framework.springutils.LoginEntity;
import itu.s6.tpseo.framework.springutils.service.ServiceLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;

import static itu.s6.tpseo.framework.springutils.util.ControllerUtil.returnSuccess;

public abstract class LoginController <E extends LoginEntity, S extends ServiceLogin<E>> {

    protected S service;

    public LoginController(S service) {
        this.service = service;
    }

    public abstract String getRequestHeaderKey ();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody E entity) throws Exception {
//        return ResponseEntity.ok("Mety");
        return returnSuccess(service.login(entity), HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
        return returnSuccess(service.logout(request.getHeader(getRequestHeaderKey()).substring(7)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody E entity) throws Exception {
        return returnSuccess(service.register(entity), HttpStatus.OK);
    }

}
