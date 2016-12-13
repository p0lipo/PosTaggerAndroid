package aksw.org.pos_tagger.model;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;


public class OpenNLP extends AppCompatActivity {

	public static void tagFile(String inputFile, String outputFile) throws IOException {
		POSModel model = new POSModelLoader().load(new File("src/main/resources/open_nlp_model/en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);
		TokenizerModel tokModel = null;
		try {
			tokModel = new TokenizerModel(new File("src/main/resources/open_nlp_model/en-token.bin"));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tokenizer tokenizer = new TokenizerME(tokModel);
		
		// init in and out file
		FileWriter fileWriter = new FileWriter(outputFile);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile)); 

		String line;
	    while ((line = bufferedReader.readLine()) != null) {
			// process the line.
	    	String[] stringArray = tokenizer.tokenize(line); //line input
			String[] tags = tagger.tag(stringArray);
			POSSample sample = new POSSample(stringArray, tags);
			String taggedLine = sample.toString(); //line output
	    	
			fileWriter.write(taggedLine + "\n");
	    }
			
		fileWriter.close();
		bufferedReader.close();

		
		
		
		
	}
	

	public String testOpenNLP(String string) throws IOException {
		AssetManager am = getAssets();
		InputStream inputStream = am.open("en-pos-maxent");

		POSModel model = new POSModel(inputStream);

		//POSModel model = new POSModelLoader()
		//		.load(new File("src/main/resources/open_nlp_model/en-pos-maxent.bin"));
			//PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
			POSTaggerME tagger = new POSTaggerME(model);
		 
			//InputStream modelIn = new FileInputStream("src/main/resources/open_nlp_model/en-token.bin");

			TokenizerModel tokModel = null;
			try {
				AssetManager am1 = getAssets();
				InputStream inputStream1 = am1.open("open_nlp_model/en-token.bin");

				tokModel = new TokenizerModel(inputStream1);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Tokenizer tokenizer = new TokenizerME(tokModel);
			String[] stringArray = tokenizer.tokenize(string);
			
			
			//String[] stringArray  = WhitespaceTokenizer.INSTANCE.tokenize(string);
			//String[] stringArray = new Tokenizer().tokenizeStringToStringArray(string);
			
			
			
			String[] tags = tagger.tag(stringArray);
			
			POSSample sample = new POSSample(stringArray, tags);
			
			
			
			return sample.toString();
			

    }

	public void openFile(String fileName){


	}

	
	
}
