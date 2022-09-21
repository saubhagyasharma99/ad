package com.test.ad.mockito.api;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.ad.exception.BadRequestException;
import com.example.ad.exception.NotFoundException;
import com.example.ad.model.Ad;

import com.example.ad.repository.AdRepository;
import com.example.ad.service.AdService;
import com.example.ad.service.AdServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AdControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private AdService adService;

    @MockBean
    private AdServiceImpl adServiceimpl;
    private ObjectMapper objectMapper;

    @Mock
    private AdRepository adRepository;


    @Test
    void testCreateAd() throws Exception {


            Ad ad = new Ad();
            ad.setTitle("Test Tile");

            when(adRepository.save(ArgumentMatchers.any(Ad.class))).thenReturn(ad);

            Ad created = adService.save(ad);
            //  assertThat(created.getTitle()).isSameAs(ad.getTitle());
            String title = created.getTitle();
            String title_verify = ad.getTitle();
            //assertThat(title = title_verify);
            verify(adRepository).save(ad);

    }
        @Test
        public void whenGivenId_shouldUpdateAd_ifFound() throws NotFoundException, BadRequestException {
            Ad ad = new Ad();
            ad.setId(90);
            ad.setTitle("Test Title");

            Ad newAd = new Ad();
            ad.setTitle("New Test Title");

            given(adRepository.findById((long) ad.getId())).willReturn(Optional.of(ad));
            adServiceimpl.updateAd((long) ad.getId(), newAd);

            verify(adRepository).save(newAd);
            verify(adRepository).findById((long) newAd.getId());
        }

        @Test(expected = RuntimeException.class)
        public void should_throw_exception_when_ad_doesnt_exist() throws NotFoundException, BadRequestException {
            Ad ad = new Ad();
            ad.setId(89);
            ad.setTitle("Test title");

            Ad newAd = new Ad();
            newAd.setId(90);
            newAd.setTitle("New Test Title");

            given(adRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
            adServiceimpl.updateAd((long) ad.getId(), newAd);
        }

    @Test
    public void whenGivenId_shouldDeleteAd_ifFound(){
        Ad ad = new Ad();
        ad.setTitle("Test Title");
        ad.setId(1);

        when(adRepository.findById((long) ad.getId())).thenReturn(Optional.of(ad));
        adService.delete(ad);

        verify(adRepository).deleteById((long) ad.getId());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_ad_doesnt_exist_deletingß() {
        Ad ad = new Ad();
        ad.setId(89);
        ad.setTitle("Test Name");

        given(adRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        adService.delete(ad);

    }

    @Test
    public void whenGivenId_shouldReturnAd_ifFound() {
        Ad ad = new Ad();
        ad.setId(89);

        when(adRepository.findById((long) ad.getId())).thenReturn(Optional.of(ad));

        Ad expected = (Ad) adService.findAll();


        //assertThat(expected).isSameAs(ad);
        verify(adRepository).findById((long) ad.getId());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_user_doesnt_exist() {
        Ad ad = new Ad();
        ad.setId(89);
        ad.setTitle("Test Title");

        given(adRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        adService.findAll();
    }
    }





