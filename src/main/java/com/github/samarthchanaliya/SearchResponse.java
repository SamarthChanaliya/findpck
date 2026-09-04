package com.github.samarthchanaliya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponse(
        @JsonProperty("response") ResponseData response
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ResponseData(
            @JsonProperty("numFound") int numFound,
            @JsonProperty("start") int start,
            @JsonProperty("docs") List<ArtifactDoc> docs
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ArtifactDoc(
            @JsonProperty("id") String id,
            @JsonProperty("g") String groupId,
            @JsonProperty("a") String artifactId,
            @JsonProperty("latestVersion") String latestVersion,
            @JsonProperty("p") String packaging,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("versionCount") int versionCount
    ) {
        public LocalDate getReleaseDate() {
            return Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }
}
