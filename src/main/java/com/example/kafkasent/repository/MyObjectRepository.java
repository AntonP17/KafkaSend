package com.example.kafkasent.repository;

import com.example.kafkasent.model.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyObjectRepository extends JpaRepository<MyObject, Long> {
}
