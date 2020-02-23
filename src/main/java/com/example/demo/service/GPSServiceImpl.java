package com.example.demo.service;

import com.example.demo.dto.DetailViewDTO;
import com.example.demo.dto.ItemViewResponseDTO;
import com.example.demo.dto.ListViewResponseDTO;
import com.example.demo.persistence.entity.GPSEntity;
import com.example.demo.persistence.repository.GPSRepository;
import io.jenetics.jpx.GPX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 2/19/20
 *
 * @author manhvan.nguyen
 */
@Service
public class GPSServiceImpl implements GPSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPSServiceImpl.class);

    @Autowired
    private GPSRepository gpsRepository;

    @Override
    public void storeGPXFile(String user, InputStream fileInputStream) throws IOException {
        LOGGER.info("Reading GPX file...");
        GPX gpxObject = GPX.read(fileInputStream);
        byte[] serialize = SerializationUtils.serialize(gpxObject);
        GPSEntity entity = new GPSEntity();
        entity.setGpxData(serialize);
        entity.setUser(user);
        gpsRepository.save(entity);
        LOGGER.info("Stored GPX successfully");
    }

    @Override
    public ListViewResponseDTO getGPXList(int currentPage, int pageSize) {
        int page0 = currentPage - 1;
        LOGGER.info("Querying GPX List, page: {}, pageSize: {}", page0, pageSize);
        Page<GPSEntity> page = gpsRepository.findAll(PageRequest.of(page0, pageSize, Sort.Direction.DESC, "createdDate"));
        ListViewResponseDTO responseDTO = new ListViewResponseDTO();

        List<GPSEntity> entities = page.getContent();
        List<ItemViewResponseDTO> items = new ArrayList<>();
        entities.stream().forEach(entity -> {
            ItemViewResponseDTO item = new ItemViewResponseDTO();
            GPX gpxObject = (GPX) SerializationUtils.deserialize(entity.getGpxData());
            gpxObject.getMetadata().ifPresent(metadata -> {
                metadata.getName().ifPresent(s -> item.setName(s));
                metadata.getDescription().ifPresent(s -> item.setDescription(s));
            });
            item.setUploadedDate(entity.getCreatedDate());
            item.setId(entity.getId());
            item.setUser(entity.getUser());
            items.add(item);
        });
        responseDTO.setPageSize(pageSize);
        responseDTO.setCurrentPage(currentPage);
        responseDTO.setTotal(page.getTotalElements());
        responseDTO.setItems(items);
        LOGGER.info("Get {} item in {}.", page.getNumberOfElements(), page.getTotalElements());
        return responseDTO;
    }

    @Override
    public DetailViewDTO getByID(long id) {
        LOGGER.info("Get GPS by ID = {}", id);
        Optional<GPSEntity> optional = gpsRepository.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        GPSEntity entity = optional.get();
        GPX gpx = (GPX) SerializationUtils.deserialize(entity.getGpxData());
        DetailViewDTO dto = new DetailViewDTO();
        dto.setGpx(gpx);
        dto.setId(entity.getId());
        dto.setUser(entity.getUser());
        dto.setUploadedDate(entity.getCreatedDate());
        return dto;
    }


}
