/* SubBookActivity
 *
 *  Feb 05, 2018
 *
 *  Copyright © 2018 patenio CMPUT301, University of Alberta - All Rights Reserved.
 *  You may use, distribute or modify this code under terms and conditions of Code
 *  of Student Behaviour at
 *  University of Alberta.
 *  You can find a copy of the license in this project. Otherwise, please contact patenio@ualberta.ca
 */

package ca.ualberta.cs.patenio_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import java.util.Date;

import android.view.View;
import android.widget.Button;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Acts as the main activity of patenio-SubBook
 *
 * @author patenio
 */
public class SubBookActivity extends AppCompatActivity {
    private ArrayList<Subscription> subList;
    private static final String FILENAME = "sub_list.sav";

    /**
     * Creates and initializes menu buttons upon creating the app.
     * @param savedInstanceState - the previously saved state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_book);

        Log.i("LifeCycle ---->", "onCreate SUBBOOKACTIVITY is called");

        // Buttons
        Button showButton = (Button) findViewById(R.id.show);
        Button addButton = (Button) findViewById(R.id.add);

        showButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Determines behaviour of show button. Must lead to SubsDisplayActivity for viewing
             * current subscriptions.
             * @param view - instance of View object
             *
             * @see SubsDisplayActivity
             */
            public void onClick(View view){
                setResult(RESULT_OK);
                Intent intent = new Intent(SubBookActivity.this, SubsDisplayActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines behaviour of add button. Must lead to SubAddMenuActivity for adding
             * subscriptions.
             * @param view instance of View object
             *
             * @see SubAddMenuActivity
             */
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(SubBookActivity.this, SubAddMenuActivity.class);
                startActivity(intent);
            }
        });

    } // End of onCreate()

    /**
     * Loads a saved file or creates a new file upon starting activity
     * by calling loadFromFile method. Is called whenever the app starts.
     */
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    /**
     * Attempt to load saved file. If file is not found, make a new file already containing
     * subscriptions. Creates a new ArrayList containing the subscriptions. Items are loaded by
     * being converted from Json to a token type of ArrayList<Subscription>.
     *
     * @see Subscription
     */
    private void loadFromFile() {

        // Try to load file
        try {
            Log.i("LifeCycle --->", "try is called");
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Convert from Json to ArrayList
            // Source: stackoverflow: https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // Jan 25 2018
            Type listType = new TypeToken<ArrayList<Subscription>>() {
            }.getType();
            subList = gson.fromJson(in, listType);

            // File missing - make new one and make
        } catch (FileNotFoundException e) {
            Log.i("LifeCycle --->", "catch is called");
            subList = new ArrayList<Subscription>();

            // Pre-made subscriptions
            Date date1 = new Date();
            Date date2 = new Date();
            Subscription sub = new Subscription("First Suscription", date1, 22.2,
                    "My first subscription!");
            Subscription sub2 = new Subscription("secondone", date2,24.3,
                    "this is a test");
            subList.add(sub);
            subList.add(sub2);

            // Save file
            saveInFile();
        }
        catch (IOException e) {
            throw new RuntimeException();
            }
        }

    /**
     * Saves newly created ArrayList to a file from loadInFile method.
     * Uses Gson to convert Arraylist to Json so that the ArrayList can be saved.
     *
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
     * Calls superclass onDestroy method - is called Activity is closed or user moves to a new
     * activity.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

