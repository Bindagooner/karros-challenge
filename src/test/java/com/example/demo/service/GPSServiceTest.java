package com.example.demo.service;

import com.example.demo.dto.DetailViewDTO;
import com.example.demo.dto.ItemViewResponseDTO;
import com.example.demo.dto.ListViewResponseDTO;
import com.example.demo.persistence.entity.GPSEntity;
import com.example.demo.persistence.repository.GPSRepository;
import io.jenetics.jpx.GPX;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SerializationUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created on 2/22/20
 *
 * @author manhvan.nguyen
 */

@RunWith(SpringRunner.class)
public class GPSServiceTest {

    @InjectMocks
    private GPSServiceImpl gpsService;

    @Mock
    private GPSRepository gpsRepository;

    private GPX gpx;
    private GPSEntity gpsEntity;
    @Before
    public void setUp() throws IOException {
        gpx = GPX.read("./src/test/resources/sample.gpx");
        gpsEntity = new GPSEntity();
        gpsEntity.setId(1L);
        gpsEntity.setGpxData(SerializationUtils.serialize(gpx));
        gpsEntity.setUser("username");
        when(gpsRepository.findById(1L)).thenReturn(Optional.of(gpsEntity));
    }

    @Test
    public void testStoreGPX() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/sample.gpx");
        gpsService.storeGPXFile("username", fileInputStream);
        GPSEntity expected = new GPSEntity();
        expected.setGpxData(SerializationUtils.serialize(gpx));
        expected.setUser("username");
        verify(gpsRepository, times(1)).save(any(GPSEntity.class));
    }

    @Test
    public void testGetById() {
        DetailViewDTO actual = gpsService.getByID(1L);
        Assert.assertEquals(Long.valueOf(1L), actual.getId());
        Assert.assertEquals("username", actual.getUser());
        Assert.assertEquals(gpx, actual.getGpx());
    }

    @Test
    public void testGetList() {
        List<GPSEntity> contents = new ArrayList<>();
        contents.add(gpsEntity);
        Page<GPSEntity> page = new PageImpl<>(contents);
        when(gpsRepository.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "createdDate"))).thenReturn(page);
        ListViewResponseDTO actual = gpsService.getGPXList(1, 2);
        Assert.assertEquals(1, actual.getTotal());
        Assert.assertEquals(2, actual.getPageSize());
        Assert.assertEquals(1, actual.getCurrentPage());
        ItemViewResponseDTO actualItem = actual.getItems().get(0);
        Assert.assertEquals(gpx.getMetadata().get().getDescription().get(), actualItem.getDescription());
        Assert.assertEquals(gpx.getMetadata().get().getName().get(), actualItem.getName());
        Assert.assertEquals("username", actualItem.getUser());
        Assert.assertEquals(Long.valueOf(1L), actualItem.getId());
    }
}
