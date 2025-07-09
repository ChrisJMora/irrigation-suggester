//package com.example.demo.tasks;
//
//import com.example.demo.application.ports.mappers.CropMapper;
//import com.example.demo.application.ports.services.*;
//import com.example.demo.application.usecases.IrrigationScheduleManager;
//import com.example.demo.application.usecases.IrrigationService;
//import com.example.demo.infrastructure.config.security.SecurityConfig;
//import com.example.demo.infrastructure.config.security.admin.CustomUserDetailsService;
//import com.example.demo.infrastructure.config.security.admin.JwtUtil;
//import com.example.demo.interfaces.controllers.CropController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CropController.class)
//@Import(SecurityConfig.class)
//public class CropControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private JwtUtil jwtUtil;
//    @MockBean
//    private CustomUserDetailsService customUserDetailsService;
//    @MockBean
//    private CropService cropService;
//    @MockBean
//    private SensorService sensorService;
//    @MockBean
//    private SprinklerService sprinklerService;
//    @MockBean
//    private LocationService locationService;
//    @MockBean
//    private SoilService soilService;
//    @MockBean
//    private CropMapper cropMapper;
//    @MockBean
//    private IrrigationScheduleManager irrigationScheduleManager;
//    @MockBean
//    private IrrigationService irrigationService;
//
//
//    @Test
//    @WithMockUser(roles = "ADMINISTRATOR")
//    public void testGetAllCropsAdmin() throws Exception {
//        mockMvc.perform(get("/api/crop/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "SUPERVISOR")
//    public void testGetAllCropsSupervisor() throws Exception {
//        mockMvc.perform(get("/api/crop/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    public void testGetAllCropsForbidden() throws Exception {
//        mockMvc.perform(get("/api/crop/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
//}
