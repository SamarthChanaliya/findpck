package com.github.samarthchanaliya;

import picocli.CommandLine.Command;
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
    public static void main(String[] args) throws Exception{
        String quarry;
        int resultCount = 0;
        List<SearchResponse.ArtifactDoc> docs = List.of();
        int index = 0;
        String card;
        SearchService service = new SearchService();
        if (args.length != 0){
            quarry = args[0];
            resultCount = 10;

            Optional<SearchResponse> results = service.searchDocs(quarry, 3);

            if (results.isPresent()){
                resultCount = results.get().response().numFound();
                docs = results.get().response().docs();
            }

            System.out.printf("Found %d packages matching \"%s\" (showing top 3)%n%n",resultCount,quarry);

            for (SearchResponse.ArtifactDoc doc : docs){
                index ++;
                card = DependencyFormatter.formatCard(index,doc);
                System.out.println(card);
            }
        }else
            System.out.println("no args");
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}