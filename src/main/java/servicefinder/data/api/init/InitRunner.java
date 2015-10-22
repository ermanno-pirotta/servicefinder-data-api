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
    	//http://www.avvocatomatteobertocchi.it/servizi/
    	CategoryBuilder builder = new CategoryBuilder().withName("avvocati")
    			.withDescription("Professionisti per diritto civile, penale, internazionale e molto altro!")
    			.withServices(
    			Arrays.asList("Diritto civile",
    						  "Regime patrimoniale della famiglia",
    						  "Imprese familiari",
    						  "Successioni e Donazioni",
    						  "Obbligazioni e contratti",
    						  "Diritto internazionale privato",
    						  "Separazione e divorzio",
    						  "Convivenza",
    						  "Diritto immobiliare",
    						  "Pratiche di risarcimento danni",
    						  "Diritto commerciale",
    						  "Diritto societario",
    						  "Recupero crediti",
    						  "Diritto penale"
    						));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("commercialisti")
    			.withDescription("Professionisti per problemi fiscali del privato o dell'impresa.")
    			.withServices(
    			Arrays.asList("Compilazione modello UNICO",
    						  "Compilazione modello 730",
    						  "Consulenza in ambito amministrativo, fiscale e del lavoro",
    						  "Dichiarazioni dei redditi, Iva e Irap",
    						  "Dichiarazioni e versamenti Imu",
    						  "Dichiarazioni di successioni ereditarie",
    						  "Assistenza alle operazioni straordinarie (scissioni, fusioni, conferimenti)",
    						  "Assistenza alla costituzione di enti associativi no profit",
    						  "Assistenza alla gestione di contenziosi di natura tributaria"
    						  ));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("notai")
    			.withDescription("Professionisti per tutte le pratiche notarili di cui hai bisogno")
    			.withServices(
    			Arrays.asList("Compravendite, divisioni, permute e altri atti immobiliari",
    						  "Costituzioni e trasferimenti di servitù",
    						  "Usufrutto e altri diritti reali",
    						  "Mutui",
    						  "Concessioni ipoteche",
    						  "Costituzioni di pegni",
    						  "Cessione partecipazioni sociali",
    						  "Verbali di assemblee di società, associazioni e fondazioni",
    						  "Fusioni, scissioni, trasformazioni",
    						  "Costituzioni e modifiche di società di persone e capitali, di associazioni e fondazioni, di imprese familiari",
    						  "Testamenti",
    						  "Donazioni",
    						  "Procure",
    						  "Atti di destinazione",
    						  "Patti di famiglia",
    						  "Separazione beni, fondi patrimoniali e altre convenzioni matrimoniali"
    						));
    	repo.save(builder.build());
    	
    	builder = new CategoryBuilder().withName("consulenti finanziari")
    			.withDescription("Professionisti che ti aiutano a mettere sotto controllo le tue finanze e investimenti.")
    			.withServices(
    			Arrays.asList("Analisi portfolio"));
    	repo.save(builder.build());
    }
}
