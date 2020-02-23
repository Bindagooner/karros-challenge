package com.example.demo.controller;

import com.example.demo.dto.DetailViewDTO;
import com.example.demo.dto.ListViewResponseDTO;
import com.example.demo.service.GPSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created on 2/22/20
 *
 * @author manhvan.nguyen
 */

@RunWith(SpringRunner.class)
@WebMvcTest(DemoController.class)
public class DemoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GPSService gpsService;

    @Test
    public void testStore() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload-gpx")
                .file("file", Files.readAllBytes(Paths.get("./src/test/resources/sample.gpx")))
                .param("user", "user-test");

        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("uploaded"));
        Mockito.verify(gpsService, Mockito.times(1)).storeGPXFile(anyString(), any(InputStream.class));
    }

    @Test
    public void testGetList() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/list")
                .param("page", "1")
                .param("size", "2");

        ListViewResponseDTO dto = new ListViewResponseDTO();
        dto.setItems(new ArrayList<>());
        dto.setTotal(0);
        dto.setCurrentPage(1);
        dto.setPageSize(2);
        when(gpsService.getGPXList(1, 2)).thenReturn(dto);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value("1"))
                .andExpect(jsonPath("$.total").value("0"))
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    public void testGetById() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/gpx")
                .param("id", "1");

        DetailViewDTO dto = new DetailViewDTO();
        dto.setId(1L);
        dto.setUser("test-user");
        when(gpsService.getByID(1L)).thenReturn(dto);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user").value("test-user"));
    }

}
