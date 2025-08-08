package com.htetvehiclerental.htetvehiclerental.controller;

 
import lombok.AllArgsConstructor;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;

import com.htetvehiclerental.htetvehiclerental.CustomUtils;
import com.htetvehiclerental.htetvehiclerental.dto.NotificationDto;
import com.htetvehiclerental.htetvehiclerental.service.NotificationService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam Long customerId) {
        List<NotificationDto> notifications = notificationService.getCustomerNotifications(customerId);
        return ResponseEntity.ok(notifications);
    }
}