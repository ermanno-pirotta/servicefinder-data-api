package servicefinder.data.api.search;

import java.io.Serializable;

public class AutocompleteHint implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String hint;
	
	public AutocompleteHint(String hint){
		this.hint = hint;
	}
	
	public String getHint() {
		return hint;
	}	
}
