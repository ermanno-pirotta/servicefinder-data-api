package servicefinder.data.api.init;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import servicefinder.data.api.category.CategoryBuilder;
import servicefinder.data.api.category.CategoryRepository;

@Component
@Profile(value="init")
public class InitRunner implements CommandLineRunner{

	@Autowired
	CategoryRepository repo;
	
	@Override
	public void run(String... arg0) throws Exception {
        initCategories();
	}

    
    public void initCategories(){
    	repo.deleteAll();
    	
    	CategoryBuilder builder = new CategoryBuilder().withName("avvocati").withServices(Arrays.asList("diritto penale","diritto civile","pratiche per il divorzio"));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("commercialisti").withServices(Arrays.asList("modello UNICO", "730"));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("notai").withServices(Arrays.asList("pratiche di successione"));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("consulenti finanziari").withServices(Arrays.asList("analisi portfolio"));
    	repo.save(builder.build());
    }
}
