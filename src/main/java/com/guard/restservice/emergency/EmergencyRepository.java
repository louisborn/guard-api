package com.guard.restservice.emergency;

import com.guard.restservice.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    @Query("SELECT e FROM Emergency e WHERE e.operatorId=?1")
    List<Emergency> findEmergencyByOperatorId(long operatorId);

    @Query("SELECT e FROM Emergency e WHERE e.id=?1")
    Emergency findEmergencyById(long id);
}
