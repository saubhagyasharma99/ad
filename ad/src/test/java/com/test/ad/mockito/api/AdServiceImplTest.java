package com.test.ad.mockito.api;

import com.example.ad.exception.BadRequestException;
import com.example.ad.exception.NotFoundException;
import com.example.ad.model.Ad;
import com.example.ad.repository.AdRepository;
import com.example.ad.service.AdServiceImpl;
import lombok.var;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.MatcherAssert.*;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

public class AdServiceImplTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private AdServiceImpl adService;
    @Mock
    private AdRepository adRepository;

    public  void setup(){
      AdServiceImpl  adService = new AdServiceImpl(adRepository);
    }
    @Test
    public void testUpdateAd_ok() throws NotFoundException, BadRequestException {
        Ad ad = new Ad();
        ad.setId(90);
        ad.setTitle("Test Title");

        Ad newAd = new Ad();
        ad.setTitle("New Test Title");

        given(adRepository.findById((long) ad.getId())).willReturn(Optional.of(ad));
        adService.updateAd((long) ad.getId(), newAd);

        verify(adRepository).save(newAd);
        verify(adRepository).findById((long) newAd.getId());
    }

    @Test
    public void testUpdateAd_NotFound()  {
        int id = 103;
        var exception = assertThrows(NotFoundException.class,
                () -> adService.updateAd((long) id, new Ad()));
        assertThat(exception.getMessage()).isEqualTo("Ad Not Found");
    }



    @Test
    public void testUpdateAd_BadRequest(){
        int userid = 123;
        var exception = assertThrows(BadRequestException.class,
                () -> adService.updateAd((long) userid, new Ad()));
        assertThat(exception.getMessage()).isEqualTo("Invalid Request");

    }
}
