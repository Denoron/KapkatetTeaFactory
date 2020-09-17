package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Center;
import com.propscout.teafactory.repositories.CenterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CentersService {

    private CenterRepository centerRepository;

    public CentersService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public List<Center> getAllCenters() {

        final List<Center> centers = new ArrayList<>();

        centerRepository.findAll().forEach(centers::add);

        return centers;
    }

    public Optional<Center> getCenterById(Integer id) {
        return centerRepository.findById(id);
    }

    public boolean addCenter(Center center) {

        Optional<Center> optionalCenter = centerRepository.findByName(center.getName());

        if (optionalCenter.isPresent()) {
            return false;
        }

        centerRepository.save(center);

        return true;

    }

    public boolean updateCenterById(Center center) {

        if (centerRepository.existsById(center.getId())) {
            centerRepository.save(center);

            return true;
        }

        return false;
    }

    public void deleteCenterById(Integer id) {

        Optional<Center> optionalCenter = centerRepository.findById(id);

        if (optionalCenter.isEmpty()) {
            return;
        }
        centerRepository.delete(optionalCenter.get());
    }
}
