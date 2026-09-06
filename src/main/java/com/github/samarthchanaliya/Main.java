package com.github.samarthchanaliya;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Command(
        name = "findpck",
        mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "Package search machine for terminal"
)
public class Main implements Callable<Integer> {

    @Parameters(index = "0",description = "The package name or keyword to search for.")
    private String query;

    @Option(names = {"-l","--limit"}, description = "Number of results to display (default: 3).", defaultValue = "3")
    private int limit;

    @Option(names = {"-c", "--copy"}, description = "Copy dependency snippet to clipboard (default: top result).")
    private boolean copy;

    @Option(names = {"--copy-index"}, description = "Index of the result to copy (1-based, default: 1).")
    private Integer copyIndex;

    @Option(names = {"-f", "--format"}, description = "Format to copy: 'gradle' (default) or 'maven'.", defaultValue = "gradle")
    private String format;

    @Override
    public Integer call() throws Exception {

        SearchService service = new SearchService();
        Optional<SearchResponse> results = service.searchDocs(query, limit);
        if (results.isEmpty() || results.get().response().docs().isEmpty()){
            System.out.println("No packages found for: " + query);
            return 0;
        }

        int resultCount = results.get().response().numFound();
        List<SearchResponse.ArtifactDoc> docs = results.get().response().docs();

        System.out.printf("Found %d packages matching \"%s\" (showing top %d)%n%n",resultCount, query,limit);

        int index = 0;
        for (SearchResponse.ArtifactDoc doc : docs){
            index ++;
            String card = DependencyFormatter.formatCard(index,doc);
            System.out.println(card);
        }

        if (copy || copyIndex != null) {
            int targetIndex = (copyIndex != null) ? copyIndex : 1;
            if (targetIndex < 1 || targetIndex > docs.size()) {
                System.err.printf("Cannot copy result #%d: index out of range (1 - %d)%n", targetIndex, docs.size());
            } else {
                SearchResponse.ArtifactDoc targetDoc = docs.get(targetIndex - 1);
                String snippet;
                String formatLabel;
                if ("maven".equalsIgnoreCase(format) || "mvn".equalsIgnoreCase(format)) {
                    snippet = DependencyFormatter.formatMaven(targetDoc);
                    formatLabel = "Maven";
                } else {
                    snippet = DependencyFormatter.formatGradle(targetDoc);
                    formatLabel = "Gradle";
                }

                if (ClipboardUtil.copy(snippet)) {
                    System.out.printf("Copied %s dependency for %s:%s to clipboard!%n",
                            formatLabel, targetDoc.groupId(), targetDoc.artifactId());
                } else {
                    System.err.println("Failed to copy to clipboard.");
                }
            }
        }

        return 0;
    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}