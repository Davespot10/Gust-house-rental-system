package com.example.mppproject.Controller;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.*;
import com.example.mppproject.Service.ReservationService;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppUserController.class)
@ActiveProfiles("test")
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;
    private List<Reservation> reservationList;


    @BeforeEach
    void setUp() {
        Account account=new Account(123465898,672.2);
        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role=new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser=new AppUser("Dawit","Demelash","davespot10",role,"123456",address,account);
        Type type=Type.HOME;
        Space space=Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus=ApprovedStatus.PENDING;
        HomeProperty homeProperty=new HomeProperty(2,3,3,"Excellent Condition");
        Property property=new Property("Luxury Apartment",type,space,
                "stay safe",address,123.22,approvedStatus,true,2,homeProperty,appUser);
        ReservationStatusEnum reservationStatusEnum=ReservationStatusEnum.PENDING;
        Reservation reservation=new Reservation(123.3,"11/11/22","12/12/22",appUser,property,"132",reservationStatusEnum);

        Account account1 =new Account(123465898,672.2);
        Address address1 =new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role1 =new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser1 =new AppUser("Dawit","Demelash","davespot100",role,"123456",address,account);
        Type type1=Type.HOME;
        Space space1 =Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus1 =ApprovedStatus.PENDING;
        HomeProperty homeProperty1=new HomeProperty(2,3,3,"Excellent Condition");
        Property property1 =new Property("Luxury Apartment",type,space,
                "stay safe",address,123.22,approvedStatus,true,2,homeProperty,appUser);
        ReservationStatusEnum reservationStatusEnum1=ReservationStatusEnum.PENDING;
        Reservation reservation1 =new Reservation(123.3,"11/11/22","12/12/22",appUser,property,"132",reservationStatusEnum);

        reservationList = new ArrayList<>(Arrays.asList(reservation,reservation1));
    }

    @Test
    void getReservations() throws Exception {
        given(reservationService.getReservations()).willReturn(reservationList);
        this.mockMvc.perform(get("/api/v1/reservation"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()", ElementMatchers.is(reservationList.size())));
    }

    @Test
    void getResrvationByRef() {
    }

    @Test
    void createReservation() {
    }

    @Test
    void cancelReservation() {
    }
}