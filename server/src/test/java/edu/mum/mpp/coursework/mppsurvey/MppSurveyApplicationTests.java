package edu.mum.mpp.coursework.mppsurvey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.model.SignUpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MppSurveyApplicationTests {


//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	RoleRepository roleRepository;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
//	@Autowired
//	JwtTokenProvider tokenProvider;

	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/admin/surveys")).andExpect(status().isUnauthorized());
	}


	@Test
	public void whenPostHttpRequesttoApiAuthSignup_thenStatusCreated() throws Exception {
		SignUpRequest customer = new SignUpRequest("Anhaa123", "passanhaa");
		mvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(customer.toString())
				)
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
