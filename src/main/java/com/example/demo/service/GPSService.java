package com.example.demo.service;

import com.example.demo.dto.DetailViewDTO;
import com.example.demo.dto.ListViewResponseDTO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 2/19/20
 *
 * @author manhvan.nguyen
 */
public interface GPSService {

    /**
     * serialize and store uploaded GPX file to database
     * @param user
     * @param fileInputStream
     */
    void storeGPXFile(String user, InputStream fileInputStream) throws IOException;

    /**
     * get list view
     * @param currentPage
     * @param pageSize
     * @return ListViewResponseDTO
     */
    ListViewResponseDTO getGPXList(int currentPage, int pageSize);

    /**
     * get DetailView by ID
     * @param id long
     * @return DetailViewDTO
     * */
    DetailViewDTO getByID(long id);

}
