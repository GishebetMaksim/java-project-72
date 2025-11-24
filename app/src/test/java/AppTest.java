import hexlet.code.App;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.junit.jupiter.api.BeforeEach;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;


public class AppTest {
    private Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();

        try (var connection = BaseRepository.dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DELETE FROM urls;");
        }
    }

    @Test
    public void testBuildUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testIndex() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://www.example.com");
            UrlRepository.save(url);
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body).contains("https://www.example.com");
        });
    }

    @Test
    public void testShow() throws SQLException {
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body).contains("https://www.example.com");
        });
    }

    @Test
    public void testCreate() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = RequestBody.create(
                    "url=https://www.example.com",
                    MediaType.get("application/x-www-form-urlencoded")
            );

            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.example.com");
        });
    }

    @Test
    public void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheck() throws Exception {
        var mockServer = new MockWebServer();

        mockServer.enqueue(
                new MockResponse.Builder()
                        .code(200)
                        .body("""
                <html>
                    <head>
                        <title>Hello</title>
                        <meta name="description" content="Test desc">
                    </head>
                    <body>
                        <h1>Welcome!</h1>
                    </body>
                </html>
            """)
                        .build()
        );

        mockServer.start();

        var mockUrl = mockServer.url("/").toString();

        var url = new Url(mockUrl);
        UrlRepository.save(url);

        Long id = url.getId();

        var savedUrl = UrlRepository.find(id).get().getName();
        System.out.println("MOCK URL:  " + mockUrl);
        System.out.println("SAVED URL: " + savedUrl);
        System.out.println("LENGTH:    " + savedUrl.length());

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + id + "/checks");
            assertThat(response.code()).isEqualTo(200);
            var lastCheck = UrlCheckRepository.getLastUrlCheck(id);

            assertThat(lastCheck).isNotNull();
            assertThat(lastCheck.getStatusCode()).isEqualTo(200);

            assertThat(lastCheck.getTitle()).isEqualTo("Hello");
            assertThat(lastCheck.getH1()).isEqualTo("Welcome!");
            assertThat(lastCheck.getDescription()).isEqualTo("Test desc");
        });
        mockServer.close();
    }
}
