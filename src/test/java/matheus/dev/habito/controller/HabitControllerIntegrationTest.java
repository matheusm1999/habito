package matheus.dev.habito.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HabitControllerIntegrationTest {
    @LocalServerPort private int port;

    @Test
    void shouldCreateHabitForAuthenticatedUser() throws Exception {
        String body =
                """
                {
                  "name": "Academia",
                  "description": "Treino de forca",
                  "goal": 5,
                  "frequencyDays": ["MONDAY", "WEDNESDAY", "FRIDAY"]
                }
                """;

        HttpURLConnection connection = openConnection("/api/habits", "POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", basicAuth("admin", "secret"));

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(body.getBytes(StandardCharsets.UTF_8));
        }

        assertThat(connection.getResponseCode()).isEqualTo(201);
        String responseBody =
                new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assertThat(responseBody).contains("\"name\":\"Academia\"");
        assertThat(responseBody).contains("\"description\":\"Treino de forca\"");
        assertThat(responseBody).contains("\"goal\":5");
        assertThat(responseBody).contains("\"frequencyDays\":[\"MONDAY\",\"WEDNESDAY\",\"FRIDAY\"]");
        assertThat(responseBody).contains("\"createdAt\"");
        assertThat(responseBody).contains("\"updatedAt\"");
    }

    private HttpURLConnection openConnection(String path, String method) throws IOException {
        URL url = URI.create("http://localhost:" + port + path).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        return connection;
    }

    private String basicAuth(String username, String password) {
        String rawCredentials = username + ":" + password;
        String encodedCredentials =
                Base64.getEncoder().encodeToString(rawCredentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}
