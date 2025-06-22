package com.hexaware.casestudy.carrs.restcontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hexaware.casestudy.carrs.entity.Car;
import com.hexaware.casestudy.carrs.service.ICarService;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarRestController.class)
class CarRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICarService carService;

    

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).deleteCarById(1L);

        mockMvc.perform(delete("/api/cars/1"))
                .andExpect(status().isOk());
    }
}
