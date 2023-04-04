package com.example.demo_upload_cloudinary.controller;

import com.example.demo_upload_cloudinary.service.CloudinaryService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;

@Controller
@CrossOrigin()
public class CloudController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        return "uploadForm";
    }

    @GetMapping("/uploads/findAll")
    public ResponseEntity<?> findAll(@RequestParam Integer limit, @RequestParam String next_cursor) throws IOException {
        return new ResponseEntity<>(cloudinaryService.findAll(limit,next_cursor),HttpStatus.OK);
    }

    @GetMapping("/uploads/{asset_id}")
    public ResponseEntity<?> findImage(@PathVariable String asset_id) throws IOException {
        return new ResponseEntity<>(cloudinaryService.findFile(asset_id),HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImage(@RequestParam String public_id) throws IOException {
        return new ResponseEntity<>(cloudinaryService.deleteFile(public_id),HttpStatus.OK);
    }

    @RequestMapping(value = "/uploads",method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = cloudinaryService.uploadFile(file);
        return new ResponseEntity<>("File uploaded successfully: File path :  " + url,HttpStatus.OK);
    }
}
