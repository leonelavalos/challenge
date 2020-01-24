package ar.com.mercadolibre.mutantes.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantControllerIT {

    private static final String CONTENT_TYPE_JSON = "application/json";

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + this.port + "/");
    }

    @Test
    public void testIsMutantOK() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("ATGCGA");
        jsonArray.put("CAGTGC");
        jsonArray.put("TTATGT");
        jsonArray.put("AGAAGG");
        jsonArray.put("GCGTCA");
        jsonArray.put("TCACTG");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dna", jsonArray);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = template.postForEntity(baseUrl.toString() + "api/mutant", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testIsMutantForbidden() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("ATGCGA");
        jsonArray.put("CAGTGC");
        jsonArray.put("TTATTT");
        jsonArray.put("AGACGG");
        jsonArray.put("GCGTCA");
        jsonArray.put("TCACTG");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dna", jsonArray);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = template.postForEntity(baseUrl.toString() + "api/mutant", request, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testIsMutantBadRequest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", CONTENT_TYPE_JSON);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dna", jsonArray);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = template.postForEntity(baseUrl.toString() + "api/mutant", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetStats() {
        ResponseEntity<String> response = template.getForEntity(baseUrl.toString() + "/api/stats", String.class);;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
