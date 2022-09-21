package com.example.ad.service;
import com.example.ad.model.Ad;

import java.util.List;

public interface AdService {
    Ad save(Ad user);
    List<Ad> findAll();
    Ad findFirstById(Long id);
    void delete(Ad user);
}
