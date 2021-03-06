package com.guard.restservice.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.operatorId=?1")
    List<Task> findTaskByOperatorId(long operatorId);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.isCompleted=true AND t.operatorId=?1")
    void deleteAllCompletedTasks(long id);
}
