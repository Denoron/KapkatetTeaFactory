package com.propscout.teafactory.services;

import com.propscout.teafactory.models.dtos.ScheduleItemDto;
import com.propscout.teafactory.models.entities.Center;
import com.propscout.teafactory.models.entities.ScheduleItem;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.repositories.CenterRepository;
import com.propscout.teafactory.repositories.ScheduleRepository;
import com.propscout.teafactory.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CenterRepository centerRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            UserRepository userRepository,
            CenterRepository centerRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
    }

    public List<ScheduleItem> getScheduleItems() {
        List<ScheduleItem> scheduleItems = new ArrayList<>();

        scheduleRepository.findAll().forEach(scheduleItems::add);

        return scheduleItems;
    }

    public Optional<ScheduleItem> getScheduleItemById(Long id) {

        return scheduleRepository.findById(id);

    }

    public boolean addScheduleItem(ScheduleItemDto scheduleItemDto) {

        //Get the selected user from the database before proceeding else return false
        Optional<User> optionalUser = userRepository.findById(scheduleItemDto.getUserId());
        if (optionalUser.isEmpty()) return false;

        //Get the selected center from the database else return false
        Optional<Center> optionalCenter = centerRepository.findById(scheduleItemDto.getCenterId());
        if (optionalCenter.isEmpty()) return false;

        ScheduleItem scheduleItem = new ScheduleItem();

        scheduleItem.setDaytime(LocalTime.parse(scheduleItemDto.getDaytime()));
        scheduleItem.setUser(optionalUser.get());
        scheduleItem.setCenter(optionalCenter.get());
        scheduleItem.setMonth(scheduleItemDto.getMonth());

        Optional<ScheduleItem> optionalScheduleItem =
                scheduleRepository.findByMonthAndTime(scheduleItem.getMonth(), scheduleItem.getDaytime());

        if (optionalScheduleItem.isPresent()) {
            return false;
        }

        scheduleRepository.save(scheduleItem);

        return true;

    }

    public boolean updateScheduleItemById(ScheduleItemDto scheduleItemDto) {

        //Get the selected user from the database before proceeding else return false
        Optional<User> optionalUser = userRepository.findById(scheduleItemDto.getUserId());
        if (optionalUser.isEmpty()) return false;

        //Get the selected center from the database else return false
        Optional<Center> optionalCenter = centerRepository.findById(scheduleItemDto.getCenterId());
        if (optionalCenter.isEmpty()) return false;

        ScheduleItem scheduleItem = new ScheduleItem();

        scheduleItem.setDaytime(LocalTime.parse(scheduleItemDto.getDaytime()));
        scheduleItem.setUser(optionalUser.get());
        scheduleItem.setCenter(optionalCenter.get());
        scheduleItem.setMonth(scheduleItemDto.getMonth());

        //Set the id also
        scheduleItem.setId(scheduleItemDto.getId());

        //Get the schedule item in question from the database
        Optional<ScheduleItem> optionalScheduleItem = scheduleRepository.findById(scheduleItem.getId());

        if (optionalScheduleItem.isEmpty()) {
            return false;
        }

        ScheduleItem scheduleItemToUpdate = optionalScheduleItem.get();
        scheduleItemToUpdate.setCenter(scheduleItem.getCenter());
        scheduleItemToUpdate.setUser(scheduleItem.getUser());
        scheduleItemToUpdate.setDaytime(scheduleItem.getDaytime());
        scheduleItemToUpdate.setMonth(scheduleItem.getMonth());

        scheduleRepository.save(scheduleItemToUpdate);

        return true;
    }

    public void deleteScheduleItemById(Long id) {

        //Check if a schedule item with that ID exists
        if (!scheduleRepository.existsById(id)) {
            return;
        }

        //Delete the schedule item
        scheduleRepository.deleteById(id);

    }
}
