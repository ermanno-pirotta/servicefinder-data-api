package servicefinder.data.api;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import servicefinder.data.model.Resource;

@Component
public class HttpHeaderBuilder {
	public static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	public HttpHeaders buildHttpHeadersForResource(Resource resource){
		HttpHeaders httpHeaders = buildHttpHeaders();		
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(resource.getId()).toUri());
		
		return httpHeaders;
	}
	
	public HttpHeaders buildHttpHeaders(){
		HttpHeaders httpHeaders = new HttpHeaders();		
		httpHeaders.setContentType(CONTENT_TYPE);
		
		return httpHeaders;
	}
}
