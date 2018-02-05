package ca.ualberta.cs.patenio_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Allows user to edit or delete a current subscription. (INCOMPLETE)
 *
 * @author patenio
 *
 * @see SubsDisplayActivity
 * @see Subscription
 */
public class SubEditMenuActivity extends AppCompatActivity {

    private ArrayList<Subscription> subList;

    private static final String FILENAME = "sav_list.sav";

    private EditText nametext;
    private EditText datetext;
    private EditText monthlychargetext;
    private EditText commenttext;


    /**
     * Reads and gets user input. (INCOMPLETE)
     * @param savedInstanceState - previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_edit_menu);

        // EditText
        nametext = (EditText)this.findViewById(R.id.nametext);
        datetext = (EditText)this.findViewById(R.id.datetext);
        monthlychargetext = (EditText)this.findViewById(R.id.monthlychargetext);
        commenttext = (EditText)this.findViewById(R.id.commenttext);

        // Buttons
        Button saveButton = (Button) findViewById(R.id.save);
        Button deleteButton = (Button) findViewById(R.id.delete);

        saveButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Determines behaviour of save button when clicked. (INCOMPLETE)
             * Supposed to allow user to save new changes to a subscription.
             * @param view - Instance of View object.
             */
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Determines behaviour of delete button when clicked. (INCOMPLETE)
             * Supposed to allow user to delete current subscription.
             * @param v - instance of view object
             *
             * @see Subscription
             * @see SubsDisplayActivity
             */
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);
            }
        });
    }

    /**
     * Loads file upon starting activity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    /**
     * Saves any changes to subscription to file. ArrayList is converted to Json via Gson.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Loads file containing subscriptions.
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // from stackoverflow:
            // https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // Jan 25 2018
            Type listType = new TypeToken<ArrayList<Subscription>>() {
            }.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Activates necessary behaviour when activity stops.
     *
     * @see AppCompatActivity
     */
    protected void onDestroy() {
        super.onDestroy();
    }
}