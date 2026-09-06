package com.github.samarthchanaliya;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyFormatterTest {

    @Test
    void testFormatGradle() {
        SearchResponse.ArtifactDoc doc = new SearchResponse.ArtifactDoc(
                "com.google.code.gson:gson",
                "com.google.code.gson",
                "gson",
                "2.10.1",
                "jar",
                1673000000000L,
                15
        );

        String gradle = DependencyFormatter.formatGradle(doc);
        assertEquals("implementation(\"com.google.code.gson:gson:2.10.1\")", gradle);
    }

    @Test
    void testFormatMaven() {
        SearchResponse.ArtifactDoc doc = new SearchResponse.ArtifactDoc(
                "com.google.code.gson:gson",
                "com.google.code.gson",
                "gson",
                "2.10.1",
                "jar",
                1673000000000L,
                15
        );

        String maven = DependencyFormatter.formatMaven(doc);
        assertTrue(maven.contains("<groupId>com.google.code.gson</groupId>"));
        assertTrue(maven.contains("<artifactId>gson</artifactId>"));
        assertTrue(maven.contains("<version>2.10.1</version>"));
    }

    @Test
    void testFormatCard() {
        SearchResponse.ArtifactDoc doc = new SearchResponse.ArtifactDoc(
                "com.google.code.gson:gson",
                "com.google.code.gson",
                "gson",
                "2.10.1",
                "jar",
                1673000000000L,
                15
        );

        String card = DependencyFormatter.formatCard(1, doc);
        assertTrue(card.contains("[1] com.google.code.gson : gson"));
        assertTrue(card.contains("Latest: 2.10.1"));
        assertTrue(card.contains("implementation(\"com.google.code.gson:gson:2.10.1\")"));
    }
}
