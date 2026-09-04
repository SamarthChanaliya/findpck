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
    private String quarry;

    @Option(names = {"-l","--limit"}, description = "Number of results to display (default: 3).", defaultValue = "3")
    private int limit;

    @Override
    public Integer call() throws Exception {

        SearchService service = new SearchService();
        Optional<SearchResponse> results = service.searchDocs(quarry, limit);
        if (results.isEmpty() || results.get().response().docs().isEmpty()){
            System.out.println("No packages found for: " + quarry);
            return 0;
        }

        int resultCount = results.get().response().numFound();
        List<SearchResponse.ArtifactDoc> docs = results.get().response().docs();

        System.out.printf("Found %d packages matching \"%s\" (showing top %d)%n%n",resultCount,quarry,limit);

        int index = 0;
        for (SearchResponse.ArtifactDoc doc : docs){
            index ++;
            String card = DependencyFormatter.formatCard(index,doc);
            System.out.println(card);
        }

        return 0;
    }

    public static void main(String[] args) throws Exception{
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}