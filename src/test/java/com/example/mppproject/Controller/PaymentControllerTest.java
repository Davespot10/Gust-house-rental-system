package com.example.mppproject.Controller;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.*;
import com.example.mppproject.Service.AppUserService;
import com.example.mppproject.Service.MyUserDetailService;
import com.example.mppproject.Service.PaymentService;
import com.example.mppproject.security.JwtRequestFilter;
import com.example.mppproject.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {
    @MockBean
    private PaymentService paymentService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;
    @MockBean
    private MyUserDetailService myUserDetailService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtRequestFilter filter;

    private Payment payment;


    @BeforeEach
    void setUp() {
        Account account = new Account(123465898, 672.2);
        Address address = new Address("Addis Ababa", "Addis Ababa", "Ethiopia", "1234", "5678", "19.2", "38.9");
        Set<Role> role = new HashSet<>();
        Set<Role> roleHost = new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        roleHost.add(new Role(RoleType.HOST));
        AppUser appUser = new AppUser("Dawit", "Demelash", "davespot10", role, "123456", address, account);
        AppUser appUserHost = new AppUser("Dawit", "Demelash", "davespot10", roleHost, "123456", address, account);
        appUser.setId(1l);
        appUserHost.setId(2L);
        Type type = Type.HOME;
        Space space = Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus = ApprovedStatus.PENDING;
        HomeProperty homeProperty = new HomeProperty(2, 3, 3, "Excellent Condition");
        Property property = new Property("Luxury Apartment", type, space,
                "stay safe", address, 123.22, approvedStatus, true, 2, homeProperty, appUser);
        property.setId(2l);
        ReservationStatusEnum reservationStatusEnum = ReservationStatusEnum.PENDING;
        Reservation reservation = new Reservation(123.3, "11/11/22", "12/12/22", appUser, property, "132", reservationStatusEnum);

        payment.builder()
                .amount(1000)
                .guestAppUser(appUser)
                .hostAppUser(appUserHost)
                .reservation(reservation)
                .id(1L)
                .build();
    }

    @Test
    void payment() throws Exception {

        Account account = new Account(123465898, 672.2);
        Address address = new Address("Addis Ababa", "Addis Ababa", "Ethiopia", "1234", "5678", "19.2", "38.9");
        Set<Role> role = new HashSet<>();
        Set<Role> roleHost = new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        roleHost.add(new Role(RoleType.HOST));
        AppUser appUser = new AppUser("Dawit", "Demelash", "davespot10", role, "123456", address, account);
        AppUser appUserHost = new AppUser("Dawit", "Demelash", "davespot10", roleHost, "123456", address, account);
        appUser.setId(1l);
        appUserHost.setId(2L);
        Type type = Type.HOME;
        Space space = Space.ENTIRE_PLACE;
        ApprovedStatus approvedStatus = ApprovedStatus.PENDING;
        HomeProperty homeProperty = new HomeProperty(2, 3, 3, "Excellent Condition");
        Property property = new Property("Luxury Apartment", type, space,
                "stay safe", address, 123.22, approvedStatus, true, 2, homeProperty, appUser);
        property.setId(2l);
        ReservationStatusEnum reservationStatusEnum = ReservationStatusEnum.PENDING;
        Reservation reservation = new Reservation(123.3, "11/11/22", "12/12/22", appUser, property, "132", reservationStatusEnum);

        String refNumber="123ee";
        Payment inputPayment = Payment.builder()
                .amount(1000)
                .guestAppUser(appUser)
                .hostAppUser(appUserHost)
                .reservation(reservation)
                .build();

        Mockito.when(paymentService.createPayment(refNumber,1L,reservation))
                .thenReturn(inputPayment);

        mockMvc.perform(post("/api/v1/payment/123ee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": null,\n" +
                                "    \"calculatedPrice\": 450.0,\n" +
                                "    \"startDate\": \"2022-11-29\",\n" +
                                "    \"endDate\": \"2022-11-30\",\n" +
                                "    \"appUser\": {\n" +
                                "        \"id\": 11,\n" +
                                "        \"firstName\": \"Nati\",\n" +
                                "        \"roles\": [],\n" +
                                "        \"lastName\": null,\n" +
                                "        \"userName\": \"nati@gmail.com\",\n" +
                                "        \"password\": null,\n" +
                                "        \"address\": {\n" +
                                "            \"id\": 6,\n" +
                                "            \"state\": \"Iowa\",\n" +
                                "            \"city\": \"Fairfiled\",\n" +
                                "            \"country\": \"USA\",\n" +
                                "            \"zipCode\": \"52557\",\n" +
                                "            \"streetNumber\": \"1000 N 4th Street\",\n" +
                                "            \"lat\": \"41°24'12.2\\\"N\",\n" +
                                "            \"lon\": \"2°10'26.5\\\"E\"\n" +
                                "        },\n" +
                                "        \"account\": {\n" +
                                "            \"id\": 1,\n" +
                                "            \"accountNumber\": 123,\n" +
                                "            \"balance\": 2000.0\n" +
                                "        }\n" +
                                "    },\n" +
                                "    \"property\": {\n" +
                                "        \"id\": 21,\n" +
                                "        \"title\": \"Entire home hosted by Wende\",\n" +
                                "        \"type\": \"HOME\",\n" +
                                "        \"space\": \"ENTIRE_PLACE\",\n" +
                                "        \"description\": \"Relax with the whole family or a group of friends in this awesome Lake Ozark Vacation Home! Located on the 4MM Powerline Cove.\",\n" +
                                "        \"address\": {\n" +
                                "            \"id\": 19,\n" +
                                "            \"state\": \"Iowa\",\n" +
                                "            \"city\": \"Iowa City\",\n" +
                                "            \"country\": \"USA\",\n" +
                                "            \"zipCode\": \"12354\",\n" +
                                "            \"streetNumber\": \"1000 N 4th Street\",\n" +
                                "            \"lat\": \"41°24'12.2\\\"N\",\n" +
                                "            \"lon\": \"2°10'26.5\\\"E\"\n" +
                                "        },\n" +
                                "        \"pricePerNight\": 450.0,\n" +
                                "        \"approvedStatus\": \"APPROVED\",\n" +
                                "        \"availabiltyStatus\": true,\n" +
                                "        \"capacity\": 3,\n" +
                                "        \"homeProperty\": {\n" +
                                "            \"id\": 18,\n" +
                                "            \"bathRoomNumber\": 2,\n" +
                                "            \"bedNumber\": 3,\n" +
                                "            \"bedRoomNumber\": 2,\n" +
                                "            \"description\": \"Relax with the whole family or a group of friends in this awesome Lake Ozark Vacation Home! Located on the 4MM Powerline Cove.\"\n" +
                                "        },\n" +
                                "        \"appUser\": {\n" +
                                "            \"id\": 20,\n" +
                                "            \"firstName\": \"hrqev\",\n" +
                                "            \"roles\": [],\n" +
                                "            \"lastName\": null,\n" +
                                "            \"userName\": \"45@gmail.com\",\n" +
                                "            \"password\": null,\n" +
                                "            \"address\": {\n" +
                                "                \"id\": 19,\n" +
                                "                \"state\": \"Iowa\",\n" +
                                "                \"city\": \"Iowa City\",\n" +
                                "                \"country\": \"USA\",\n" +
                                "                \"zipCode\": \"12354\",\n" +
                                "                \"streetNumber\": \"1000 N 4th Street\",\n" +
                                "                \"lat\": \"41°24'12.2\\\"N\",\n" +
                                "                \"lon\": \"2°10'26.5\\\"E\"\n" +
                                "            },\n" +
                                "            \"account\": {\n" +
                                "                \"id\": 17,\n" +
                                "                \"accountNumber\": 948472919,\n" +
                                "                \"balance\": 5000.0\n" +
                                "            }\n" +
                                "        },\n" +
                                "        \"cover_image\": null\n" +
                                "    },\n" +
                                "    \"refNumber\": \"f6be798\",\n" +
                                "    \"reservationStatus\": \"PENDING\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }
}