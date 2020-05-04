package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.model.RepositoriesSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubSearchApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void searchRepositoriesTest() {
        String jwtToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwic3ViIjoiYWRtaW4iLCJpYXQiOjE1ODg1MDEzMDksImV4cCI6MTU4ODUzMDEwOX0.Nb-NCVkHEacWbJBSfIgxtlNYgVJQRnCaj3a2hht8tuaV9zLIxXSLFnkB0wRcJdPFy8atWI1QpmA4ExCeJOQy-A";
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repository")
                        .queryParam("queryString", "codeschool-training")
                        .queryParam("sort", "stats")
                        .queryParam("page", 1)
                        .queryParam("perPage", 10)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RepositoriesSearchResponse.class)
                .value(RepositoriesSearchResponse::getTotalCount, greaterThan(0));
    }

    @Test
    void searchRepositoriesValidationTest(){
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/search/repository")
                        .queryParam("queryString", "")
                        .queryParam("sort", "stats")
                        .queryParam("page", 1)
                        .queryParam("perPage", 10)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
