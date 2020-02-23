package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.GPSEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created on 2/21/20
 *
 * @author manhvan.nguyen
 */
public interface GPSRepository extends PagingAndSortingRepository<GPSEntity, Long> {

}
