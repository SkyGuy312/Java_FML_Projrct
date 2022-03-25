package com.example.wuzzufJobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WuzzufJobsApplication.class)
@AutoConfigureWebMvc
@ActiveProfiles({"test"})
class WuzzufJobsApplicationTests {


	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup(){
		System.out.println("From Setup");
		this.mockMvc =
				MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void TestDataShow() throws Exception {

		File file  = ResourceUtils.getFile("classpath:show.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/show").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataSummary() throws Exception {

		File file  = ResourceUtils.getFile("classpath:summary.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/summary").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataPopular_company() throws Exception {

		File file  = ResourceUtils.getFile("classpath:popular_company.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/popular_company").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataPopular_title() throws Exception {

		File file  = ResourceUtils.getFile("classpath:popular_title.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/popular_title").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataPopular_area() throws Exception {

		File file  = ResourceUtils.getFile("classpath:popular_area.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/popular_area").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataPopular_skill() throws Exception {

		File file  = ResourceUtils.getFile("classpath:popular_skill.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/popular_skill").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDataAfter_factoriz() throws Exception {

		File file  = ResourceUtils.getFile("classpath:after_factorize.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/after_factorize").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}

	@Test
	public void TestDatakMeans() throws Exception {

		File file  = ResourceUtils.getFile("classpath:kMeans.json");
		List<Map> expected = objectMapper.readValue(file, ArrayList.class);
		String testExpected = expected.toString().substring(0,50);

		ResultActions result =
				this.mockMvc.perform(get("/kMeans").
								contentType(MediaType.APPLICATION_JSON).
								header("Content-Type", "application/json").
								header("Accept", "application/json"))
						.andDo(print());

		result.andExpect(status().isOk());
		String res = result.andReturn().getResponse().getContentAsString();
		List<Map> actual = objectMapper.readValue(res, ArrayList.class);
		String testactual = actual.toString().substring(0,50);

		Assert.assertEquals(testExpected, testactual);
	}


}
