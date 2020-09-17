package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.ScheduleItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface ScheduleRepository extends CrudRepository<ScheduleItem, Long> {

    @Query("SELECT s FROM ScheduleItem s WHERE s.month = :month AND s.daytime = :daytime")
    Optional<ScheduleItem> findByMonthAndTime(@Param("month") Integer month, @Param("daytime") LocalTime daytime);

}
