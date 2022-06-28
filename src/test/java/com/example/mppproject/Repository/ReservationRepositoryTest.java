package com.example.mppproject.Repository;
import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.*;
import com.example.mppproject.Service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ReservationRepositoryTest {
    @Autowired
    private ReservationService reservationService;
    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void calculatePriceTest(){
        assertEquals(30.0,reservationService.calculatePrice(6,5.0));
    }
    @Test


    public void getReservationTest(){
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
        Mockito.when(reservationRepository.findAll()).thenReturn(Stream.
                of(reservation).collect(Collectors.toList()));
        assertEquals(1,reservationService.getReservations().size());

    }

}


