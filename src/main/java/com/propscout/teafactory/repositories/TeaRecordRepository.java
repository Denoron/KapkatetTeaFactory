package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.MonthTeaWeight;
import com.propscout.teafactory.models.entities.Center;
import com.propscout.teafactory.models.entities.TeaRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeaRecordRepository extends CrudRepository<TeaRecord, Long> {

    List<TeaRecord> findAllByCenter(Center center);

    @Query(value = "SELECT account_id as accountId, SUM(weight_kgs) AS totalWeight FROM tea_weight_records WHERE YEAR(created_at) = YEAR(CURDATE()) AND MONTH(created_at) =  MONTH(CURDATE()) GROUP BY account_id", nativeQuery = true)
    List<MonthTeaWeight> getCumulativeAccountTeaRecords();

}
