package matheus.dev.habito;

import com.fasterxml.jackson.databind.ObjectMapper;
import matheus.dev.habito.dto.HabitRequest;
import matheus.dev.habito.dto.HabitResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"})
public class HabitControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void createAndListHabits() throws Exception {
        HabitRequest req = new HabitRequest();
        req.setUserId("user-1");
        req.setName("Beber água");
        req.setDescription("Beber 2 litros de água");
        req.setFrequency("DAILY");
        req.setGoal(1);

        String body = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(req);

        String res = mvc.perform(post("/api/habits").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HabitResponse created = new com.fasterxml.jackson.databind.ObjectMapper().readValue(res, HabitResponse.class);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo(req.getName());

        String listRes = mvc.perform(get("/api/habits?userId=user-1")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        HabitResponse[] arr = new com.fasterxml.jackson.databind.ObjectMapper().readValue(listRes, HabitResponse[].class);
        assertThat(arr).hasSize(1);
        assertThat(arr[0].getName()).isEqualTo(req.getName());
    }
}