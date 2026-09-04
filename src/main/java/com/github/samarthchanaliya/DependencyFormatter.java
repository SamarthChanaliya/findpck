package com.github.samarthchanaliya;

public class DependencyFormatter {

    public static String formatCard(int index, SearchResponse.ArtifactDoc doc) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("[%d] %s : %s%n", index, doc.groupId(), doc.artifactId()));
        sb.append(String.format("    Latest: %s%n%n", doc.latestVersion()));
        sb.append("  Maven:\n");
        sb.append("    <dependency>\n");
        sb.append(String.format("        <groupId>%s</groupId>%n", doc.groupId()));
        sb.append(String.format("        <artifactId>%s</artifactId>%n", doc.artifactId()));
        sb.append(String.format("        <version>%s</version>%n", doc.latestVersion()));
        sb.append("    </dependency>\n\n");
        sb.append("  Gradle (Kotlin DSL):\n");
        sb.append(String.format("    implementation(\"%s:%s:%s\")%n", doc.groupId(), doc.artifactId(), doc.latestVersion()));

        return sb.toString();
    }
}
