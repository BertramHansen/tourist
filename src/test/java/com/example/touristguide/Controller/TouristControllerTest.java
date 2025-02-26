package com.example.touristguide.Controller;

import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private TouristService touristService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllAttractions() {
    }

    @Test
    void getAttractionByName() {
    }

    @Test
    void getAttractionTagsByName() {
    }

    @Test
    void viewallAttactions() {
    }

    @Test
    void showAddForm() {
    }

    @Test
    void addAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void updateAttraction() {
    }
}