package com.example.mppproject.Controller;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Service.AppUserService;
import com.example.mppproject.Service.MyUserDetailService;
import com.example.mppproject.Service.PropertyService;
import com.example.mppproject.Service.ReviewService;
import com.example.mppproject.security.JwtRequestFilter;
import com.example.mppproject.security.SecurityConfiguration;
import com.example.mppproject.util.JwtUtil;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PropertyController.class)
@AutoConfigureMockMvc(addFilters = false)
class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PropertyService propertyService;
    @MockBean
    private AppUserService appUserService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtRequestFilter filter;
    @MockBean
    private MyUserDetailService myUserDetailService;
    @MockBean
    private ReviewService reviewService;
    private List<Property> propertyList;
    private Property property;
    private HashMap<Object,Object> prop;




    @BeforeEach
    void setUp() {
        Account account = new Account(123465898, 672.2);
        Address address = new Address("Addis Ababa", "Addis Ababa", "Ethiopia", "1234", "5678", "19.2", "38.9");
        Set<Role> role = new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser = new AppUser("Dawit", "Demelash", "davespot10", role, "123456", address, account);
        appUser.setId(1l);

        Type type = Type.HOME;
        Space space = Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus = ApprovedStatus.PENDING;
        HomeProperty homeProperty = new HomeProperty(2, 3, 3, "Excellent Condition");
        Property property = new Property("Luxury Apartment", type, space,
                "stay safe", address, 123.22, approvedStatus, true, 2, homeProperty, appUser);
        property.setId(1l);
        property.builder()
                .address(address)
                .approvedStatus(approvedStatus)
                .appUser(appUser)
                .homeProperty(homeProperty)
                .availabiltyStatus(true)
                .capacity(2)
                .description("stay safe")
                .pricePerNight(1000.00)
                .title("Luxury Apartment")
                .id(1L)
                .build();
        propertyList = new ArrayList<>(Arrays.asList(property));
    }

    @Test
    void getProperty() throws Exception {
        Mockito.when(propertyService.getProperty())
                .thenReturn(propertyList);
        mockMvc.perform(get("/api/v1/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getAllMyPropertyByUserId() throws Exception {

        Mockito.when(propertyService.getAllMyPropertyByUserId(1L))
                .thenReturn(propertyList);
        mockMvc.perform(get("/api/v1/property/getAllMyPropertyByUserId/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("id")
//                        .value(property.getAppUser().getId()));

    }
}