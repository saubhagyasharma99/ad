package com.example.ad.controller;
import com.example.ad.exception.BadRequestException;
import com.example.ad.exception.NotFoundException;
import com.example.ad.model.Ad;
import com.example.ad.service.AdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/rest")
public class AdController {


    @Autowired
    private AdServiceImpl adServiceimpl;

    public AdController(AdServiceImpl adServiceimpl){
        this.adServiceimpl=adServiceimpl;

    }
    @GetMapping("/getAds")
    public ResponseEntity<List<Ad>> getAllAds(){
        return ResponseEntity.ok(adServiceimpl.findAll());
    }
    @PostMapping("/saveAd")
    public ResponseEntity<Ad>  createAd(@RequestBody Ad ad){
        try {
            Ad addedAd = adServiceimpl.save(ad);
            return  ResponseEntity.ok(addedAd);
        }
        catch (RuntimeException exception){
            System.out.println("Missing");
            return (ResponseEntity<Ad>) ResponseEntity.badRequest();
        }

    }
    @PatchMapping("/ads/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable(value = "id") Long id, @RequestBody Ad adDetails) {
        try {
            Ad updatedAd = adServiceimpl.updateAd(id, adDetails);
            return ResponseEntity.ok(updatedAd);
        }catch (NotFoundException exception){
            return ResponseEntity.notFound().build();
        }
        catch (BadRequestException exception){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/ads/{id}")
    public ResponseEntity<Ad> deleteAd(@PathVariable(value = "id") Long id) {
        Ad ad = adServiceimpl.findFirstById(id);
        if(ad == null) {
            return ResponseEntity.notFound().build();
        }
        adServiceimpl.delete(ad);
        return ResponseEntity.ok().build();
    }


}
