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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }



    @Test
    void getAllAttractions() throws Exception {
        mockMvc.perform(get("")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void getAttractionByName() throws Exception{

        String name = "Fuji";
        String description = "A volcano in japan.";
        TouristAttraction attraction = new TouristAttraction(name, description);

        when(touristService.findAttractionByName(name, description)).thenReturn(attraction);

        //chat gpt seems to think this is how one tests path variables:
        mockMvc.perform(get("/attractions/{name}", name).param("description", description))
                .andExpect(status().isOk())
                .andExpect(view().name("details"));

        verify(touristService).findAttractionByName(name, description);
    }

    @Test
    void getAttractionTagsByName() throws Exception{

        String name = "Fuji";
        String description = "Volcano in japan, very pretty";
        TouristAttraction attraction = new TouristAttraction(name, description);

        when(touristService.findAttractionByName(name)).thenReturn(attraction);

        mockMvc.perform(get("/attractions/{name}/tags", name)).andExpect(status().isOk()).andExpect(view().name("tagList"));

    }

    @Test
    void viewallAttactions() throws Exception {
        mockMvc.perform(get("/attractions/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionsList"));
    }

    @Test
    void showAddForm() throws Exception {
        //performs a get request on the attraction/add path, checks if the status it gets is OK
        //and expects that the view name, that is the string that is the html filename without the
        //.html, is add-form
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-form"));
    }

    @Test
    void addAttraction() throws Exception {
        mockMvc.perform(post("/attractions/save")
                .param("name", "Fuji-Q Highland")
                .param("description", "An amusement park near mt Fuji"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions/all"));
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void updateAttraction() {
    }
}