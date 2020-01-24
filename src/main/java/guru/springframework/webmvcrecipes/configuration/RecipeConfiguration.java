package guru.springframework.webmvcrecipes.configuration;

import guru.springframework.webmvcrecipes.bootstrap.DataLoader;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RecipeConfiguration implements CommandLineRunner {
    public RecipeConfiguration(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    DataLoader dataLoader;

    @Override
    public void run(String... args) throws Exception {
        this.dataLoader.loadData2();
    }
}
