package com.example.spring_test.repository;

import com.example.spring_test.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Modifying
    @Transactional
    @Query(value="delete from user_result where result_id = :resultId", nativeQuery = true )
    int deleteForeignKeyUsRes(@Param("resultId")Long resultId);


}
