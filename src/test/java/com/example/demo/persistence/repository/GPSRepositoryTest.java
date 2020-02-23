package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.GPSEntity;
import io.jenetics.jpx.GPX;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * Created on 2/22/20
 *
 * @author manhvan.nguyen
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class GPSRepositoryTest {

    @Autowired
    private GPSRepository gpsRepository;

    @Test
    public void testSaveAndFindById() throws IOException {
        GPX gpx = GPX.read("./src/test/resources/sample.gpx");
        GPSEntity entity = new GPSEntity();
        entity.setUser("test-user");
        entity.setGpxData(SerializationUtils.serialize(gpx));
        gpsRepository.save(entity);

        Optional<GPSEntity> optional = gpsRepository.findById(1L);
        GPSEntity entity1 = optional.get();

        byte[] gpxData = entity1.getGpxData();
        GPX savedGPX = (GPX) SerializationUtils.deserialize(gpxData);
        Assert.assertEquals(gpx, savedGPX);
        Assert.assertEquals("test-user", entity1.getUser());
    }

    @Test
    public void testGetListPaging() throws IOException {
        GPX gpx = GPX.read("./src/test/resources/sample.gpx");
        GPSEntity entity = new GPSEntity();
        entity.setUser("test-user");
        entity.setGpxData(SerializationUtils.serialize(gpx));
        gpsRepository.save(entity);

        GPSEntity entity2 = new GPSEntity();
        gpx = GPX.read("./src/test/resources/BF.gpx");
        entity2.setUser("test-user2");
        entity2.setGpxData(SerializationUtils.serialize(gpx));
        gpsRepository.save(entity2);

        Page<GPSEntity> page = gpsRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "createdDate"));
        Assert.assertEquals(2, page.getTotalElements());
        Assert.assertEquals(2, page.getNumberOfElements());
        Assert.assertEquals("test-user2", page.getContent().get(0).getUser());
        Assert.assertEquals("test-user", page.getContent().get(1).getUser());
    }
}
