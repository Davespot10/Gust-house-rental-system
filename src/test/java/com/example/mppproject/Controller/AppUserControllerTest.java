package com.example.mppproject.Controller;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.Role;
import com.example.mppproject.Service.AppUserService;
import com.example.mppproject.utility.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppUserController.class)
@ActiveProfiles("test")
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@ExtendWith(MockitoExtension.class)
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    private List<AppUser> appUserList;


    @BeforeEach
    void setUp() {
////        Set<Role> role=new HashSet<>();
////        role.add()
//        appUser = AppUser.builder()
//                .userName("natty")
//                .account(new Account(1,5000.00))
//                .firstName("Natty")
//                .lastName("star")
//                .password("1234")
//                //.roles((Set<Role>) new Role(RoleType.GUEST))
//                .address(new Address("Iowa","Fairfield","US","52557","10004TH","10.9","37.9"))
//                .id(1L)
//                .build();
        Account account=new Account(RandomGenerator.generateAccount(),672.2);
        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role=new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser = new AppUser(RandomGenerator.generateName(5),RandomGenerator.generateName(5),RandomGenerator.generateEmail(),role,"123456",address,account);

        Account account1 =new Account(RandomGenerator.generateAccount(),672.2);
        Address address1=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role1 =new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser1 = new AppUser(RandomGenerator.generateName(5),RandomGenerator.generateName(5),RandomGenerator.generateEmail(),role,"123456",address,account);

         appUserList = new ArrayList<AppUser>();
        appUserList.addAll(Arrays.asList(appUser,appUser1));


    }

    @Test
    void getAppUser() throws Exception{
        given(appUserService.getAppUser()).willReturn(appUserList);
        this.mockMvc.perform(get("/api/v1/AppUser"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()",is(appUserList.size())));
    }

    @Test
    void saveAppUser() throws Exception{

//        Account account=new Account(RandomGenerator.generateAccount(),672.2);
//        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
//        Set<Role> role=new HashSet<>();
//        role.add(new Role(RoleType.GUEST));
//        AppUser appUser = new AppUser(RandomGenerator.generateName(5),RandomGenerator.generateName(5),RandomGenerator.generateEmail(),role,"123456",address,account);
//        Mockito.when(appUserService.addAppUser(appUser)).thenReturn(appUser);
//        assertEquals(appUser,appUserService.addAppUser(appUser));

//      AppUser  inputAppUser = AppUser.builder()
//                .userName("test73")
//                .account(new Account(1,5000.00))
//                .firstName("zelalem")
//                .lastName("Tsige")
//                .password("Abcd@1234")
//               // .roles((Set<Role>) new Role(RoleType.GUEST))
//                .address(new Address("Iowa","Fairfield","Addis Ababa","52557","10004TH","10.9","37.9"))
//                .build();
//        when(appUserService.addAppUser(inputAppUser)).thenReturn(appUser);


//       mockMvc.perform(post("/api/v1/AppUser")
//               .contentType(MediaType.APPLICATION_JSON)
//               .content("{\n" +
//                       "\t\"firstName\":\"zelalem\",\n" +
//                       "\t\"lastName\":  \"Tsige\",\n" +
//                       "\t\"password\"  :\"Abcd@1234\",\n" +
//                       " \t\"userName\"  :\"test73\",\n" +
//                       "  \t\"address\": {\n" +
//                       "    \t\"city\": \"Addis Ababa\"\n" +
//                       "  }\n" +
//                       "}")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void editAppUserAccount() {
    }
}