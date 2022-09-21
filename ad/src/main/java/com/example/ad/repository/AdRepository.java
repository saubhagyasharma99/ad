package com.example.ad.repository;

import com.example.ad.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long>{
    Ad findFirstById(Long id);
}