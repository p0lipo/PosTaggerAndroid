package aksw.org.pos_tagger;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import aksw.org.pos_tagger.model.OpenNLP;
import aksw.org.pos_tagger.model.POSSample;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;

import static android.content.res.AssetManager.ACCESS_UNKNOWN;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public final InputStream readFromFile(final String filename) {
        FileInputStream fis = null;
        InputStream is = null;
        try {
            //fis = getApplicationContext().openFileInput(filename);
            is = getApplicationContext().getResources().getAssets()
                    .open(filename, ACCESS_UNKNOWN);

        } catch (IOException e) {
            Log.e("Exception", "" + e.toString());
        }
        return is;
    }


    public TokenizerModel loadTokenizerModel() {
        InputStream modelIn = null;
        TokenizerModel model = null;
        try {
            //modelIn = getApplicationContext().getResources().openRawResource(R.raw.en_token);
            //the huge delay is mainly on the line below
            //model = new TokenizerModel(modelIn);
            model = new TokenizerModel(((InputStream) readFromFile("en-token.bin")));
        } catch (IOException e) {
            // Model loading failed, handle the error
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    public POSModel loadPosModel() {
        InputStream modelIn = null;
        POSModel model = null;
        try {
            //modelIn = getApplicationContext().getResources().openRawResource(R.raw.en_pos_maxent);

            //the huge delay is mainly on the line below
            //model = new POSModel(modelIn);
            model = new POSModel(readFromFile("en-pos-maxent.bin"));
        } catch (IOException e) {
            // Model loading failed, handle the error
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }


    public void OpenNLP(View view) {

        String string = "This is a Test!";
        //OpenNLP open = new OpenNLP();

        POSModel model = loadPosModel();

        POSTaggerME tagger = new POSTaggerME(model);


        //TokenizerModel tokModel = loadTokenizerModel();

        //Tokenizer tokenizer = new TokenizerME(tokModel);

        //String[] stringArray = tokenizer.tokenize(string);


        String[] stringArray  = WhitespaceTokenizer.INSTANCE.tokenize(string);
        //String[] stringArray = new Tokenizer().tokenizeStringToStringArray(string);



        String[] tags = tagger.tag(stringArray);

        POSSample sample = new POSSample(stringArray, tags);



        String result = sample.toString();





        Log.e("Test:" , result);

    }



}
