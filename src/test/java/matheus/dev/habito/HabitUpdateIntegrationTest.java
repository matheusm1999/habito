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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"})
@org.springframework.test.context.ActiveProfiles("dev")
public class HabitUpdateIntegrationTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }
    @Test
    public void updateFlow_success_and_conflict_and_validations() throws Exception {
        HabitRequest req = new HabitRequest();
        req.setName("Exercício");
        req.setDescription("Correr 3km");
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
        assertThat(created.getVersion()).isEqualTo(0);
        // Successful update
        HabitRequest up = new HabitRequest();
        up.setName("Exercício diário");
        up.setDescription("Correr 5km");
        up.setFrequency("DAILY");
        up.setTarget(2);
        up.setVersion(created.getVersion());
        String upBody = mapper.writeValueAsString(up);
        String upRes = mvc.perform(put("/api/habits/" + created.getId()).header("Authorization","Bearer user-1").contentType(MediaType.APPLICATION_JSON).content(upBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        HabitResponse updated = mapper.readValue(upRes, HabitResponse.class);
        assertThat(updated.getName()).isEqualTo("Exercício diário");
        assertThat(updated.getVersion()).isEqualTo(1);
        // Missing version -> 400
        HabitRequest missingVersion = new HabitRequest();
        missingVersion.setName("X");
        missingVersion.setTarget(1);
        String missBody = mapper.writeValueAsString(missingVersion);
        mvc.perform(put("/api/habits/" + created.getId()).header("Authorization","Bearer user-1").contentType(MediaType.APPLICATION_JSON).content(missBody))
                .andExpect(status().isBadRequest());
        // Stale version -> 409 (use old version 0)
        HabitRequest stale = new HabitRequest();
        stale.setName("Old attempt");
        stale.setTarget(1);
        stale.setVersion(0);
        String staleBody = mapper.writeValueAsString(stale);
        mvc.perform(put("/api/habits/" + created.getId()).header("Authorization","Bearer user-1").contentType(MediaType.APPLICATION_JSON).content(staleBody))
                .andExpect(status().isConflict());
        // Forbidden (different user) -> 403
        HabitRequest up2 = new HabitRequest();
        up2.setName("Tentativa");
        up2.setTarget(1);
        up2.setVersion(updated.getVersion());
        String up2Body = mapper.writeValueAsString(up2);
        mvc.perform(put("/api/habits/" + created.getId()).header("Authorization","Bearer user-2").contentType(MediaType.APPLICATION_JSON).content(up2Body))
                .andExpect(status().isForbidden());
        // Not found -> 404
        HabitRequest nf = new HabitRequest();
        nf.setName("NF"); nf.setTarget(1); nf.setVersion(0);
        String nfBody = mapper.writeValueAsString(nf);
        mvc.perform(put("/api/habits/nonexistent").header("Authorization","Bearer user-1").contentType(MediaType.APPLICATION_JSON).content(nfBody))
                .andExpect(status().isNotFound());
        // Unauthenticated -> 401
        mvc.perform(put("/api/habits/" + created.getId()).contentType(MediaType.APPLICATION_JSON).content(upBody))
                .andExpect(status().isUnauthorized());
    }
}