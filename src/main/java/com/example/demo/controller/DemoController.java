package com.example.demo.controller;

import com.example.demo.dto.DetailViewDTO;
import com.example.demo.dto.ListViewResponseDTO;
import com.example.demo.service.GPSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created on 2/19/20
 *
 * @author manhvan.nguyen
 */

@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private GPSService gpsService;

    @PostMapping(value = "upload-gpx")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String user) throws IOException {
        LOGGER.info("upload file request received - by {}", user);
        gpsService.storeGPXFile(user, file.getInputStream());
        return new ResponseEntity<>("uploaded", HttpStatus.OK);
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListViewResponseDTO> getListView(@RequestParam("page") int page, @RequestParam("size") int size) {
        LOGGER.info("list view request received");
        ListViewResponseDTO gpxList = gpsService.getGPXList(page, size);
        return new ResponseEntity<>(gpxList, HttpStatus.OK);
    }

    @GetMapping(value = "gpx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailViewDTO> getGPX(@RequestParam("id") long id) {
        DetailViewDTO dto = gpsService.getByID(id);
        return dto == null ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
