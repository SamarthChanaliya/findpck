package com.github.samarthchanaliya;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.Optional;

public class SearchService {
    private static final String BASE_URL = "https://search.maven.org/solrsearch/select";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Optional<SearchResponse> searchDocs(String query, int rows) throws Exception{
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String endpoint = String.format("%s?q=%s&rows=%d&wt=json", BASE_URL,encodedQuery,rows);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200){
            return Optional.empty();
        }

        return Optional.of(mapper.readValue(response.body(), SearchResponse.class));
    }


}