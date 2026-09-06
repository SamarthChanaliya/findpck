package com.github.samarthchanaliya;

public class DependencyFormatter {

    public static String formatCard(int index, SearchResponse.ArtifactDoc doc) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("[%d] %s : %s%n", index, doc.groupId(), doc.artifactId()));
        sb.append(String.format("    Latest: %s | Updated: %s%n%n", doc.latestVersion(), doc.getReleaseDate()));
        sb.append("  Maven:\n");
        sb.append("    <dependency>\n");
        sb.append(String.format("        <groupId>%s</groupId>%n", doc.groupId()));
        sb.append(String.format("        <artifactId>%s</artifactId>%n", doc.artifactId()));
        sb.append(String.format("        <version>%s</version>%n", doc.latestVersion()));
        sb.append("    </dependency>\n\n");
        sb.append("  Gradle (Kotlin DSL):\n");
        sb.append(String.format("    %s%n", formatGradle(doc)));

        return sb.toString();
    }

    public static String formatMaven(SearchResponse.ArtifactDoc doc) {
        return String.format(
                "<dependency>%n" +
                "    <groupId>%s</groupId>%n" +
                "    <artifactId>%s</artifactId>%n" +
                "    <version>%s</version>%n" +
                "</dependency>",
                doc.groupId(), doc.artifactId(), doc.latestVersion()
        );
    }

    public static String formatGradle(SearchResponse.ArtifactDoc doc) {
        return String.format("implementation(\"%s:%s:%s\")", doc.groupId(), doc.artifactId(), doc.latestVersion());
    }
}
