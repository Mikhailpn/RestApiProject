package WebUtils;

import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

public class WebClientRequest {
    public String makeGetRequest(String uri) throws IOException {
        WebClient client = WebClient.create("http://localhost:8080");

        String response = client
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;

    }

    public String makePostRequest(String uri, Object body) throws IOException {
        WebClient client = WebClient.create("http://localhost:8080");

        String response = client
                .post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;

    }

}
