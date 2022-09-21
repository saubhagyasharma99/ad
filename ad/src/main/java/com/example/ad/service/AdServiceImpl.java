package com.example.ad.service;

import com.example.ad.exception.BadRequestException;
import com.example.ad.exception.NotFoundException;
import com.example.ad.model.Ad;
import com.example.ad.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository){
        this.adRepository=adRepository;
    }


    @Override
    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    @Override
    public Ad findFirstById(Long id) {
        return adRepository.findFirstById(id);
    }

    @Override
    public void delete(Ad ad) {
        adRepository.delete(ad);
    }

    public Ad updateAd(Long id, Ad adDetails) throws NotFoundException, BadRequestException {

        Ad existingAd = adRepository.findFirstById(id);
        if(null == existingAd) {
            throw new NotFoundException();
        }
        try {
            Ad updatedAd = adRepository.save(adDetails);
            return updatedAd;
        }catch (Exception exception){
            throw new BadRequestException();
        }
    }
}