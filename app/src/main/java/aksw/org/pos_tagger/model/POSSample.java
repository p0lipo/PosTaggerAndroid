package aksw.org.pos_tagger.model;

public class POSSample {
	
	private String[] strings;
	private String[] tags;
	
	public POSSample(String[] strings,String[] tags) {
		this.strings = strings;
		this.tags = tags;
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; (i < strings.length) && (i < tags.length); i++) {
			result = result + strings[i] + "_" + tags[i] + " ";
		}
		return result;
		
			
	}

}
