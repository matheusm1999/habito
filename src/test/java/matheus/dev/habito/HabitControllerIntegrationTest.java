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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"})
@org.springframework.test.context.ActiveProfiles("dev")
public class HabitControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    public void createAndListHabits() throws Exception {
        HabitRequest req = new HabitRequest();
        req.setName("Beber água");
        req.setDescription("Beber 2 litros de água");
        req.setFrequency("DAILY");
        req.setTarget(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        String body = mapper.writeValueAsString(req);

        String res = mvc.perform(post("/api/habits").header("Authorization","Bearer user-1").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        HabitResponse created = mapper.readValue(res, HabitResponse.class);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo(req.getName());

        String listRes = mvc.perform(get("/api/habits").header("Authorization","Bearer user-1")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        HabitResponse[] arr = mapper.readValue(listRes, HabitResponse[].class);
        assertThat(arr).hasSize(1);
        assertThat(arr[0].getName()).isEqualTo(req.getName());
    }
}