package com.example.Sistema_Requisicao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Sistema_Requisicao.dto.DashboardDTO;
import com.example.Sistema_Requisicao.services.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardDTO> getResumo() {
        try {
            return ResponseEntity.ok(service.getResumo());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}