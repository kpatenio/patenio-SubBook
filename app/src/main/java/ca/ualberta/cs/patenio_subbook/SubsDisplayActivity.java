package ca.ualberta.cs.patenio_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Activity that displays current subscriptions in a file.
 *
 * @author patenio
 *
 * @see Subscription
 */
public class SubsDisplayActivity extends AppCompatActivity {

    private ListView subListOld;

    private ArrayAdapter<Subscription> adapter;
    private ArrayList<Subscription> subList;

    private static final String FILENAME = "sub_list.sav";

    /**
     * Creates ListView for displaying subscriptions.
     * Also makes a value in the list clickable so that the user can edit a subscription and go
     * to SubEditMenuActivity
     *
     * @param savedInstanceState - previously saved state
     *
     * @see Subscription
     * @see SubEditMenuActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs_display);
        subListOld = (ListView) findViewById(R.id.subListOld);

        // CLICKABLE SUB
        // https://stackoverflow.com/questions/14175153/how-to-make-my-listview-items-clickable
        // Feb 3 2018
        subListOld.setOnItemClickListener(new ListView.OnItemClickListener() {
            /**
             * Allows user to click on a subscription that is displayed. When the user clicks on it,
             * lead to SubEditMenuActivity so that user can edit a subscription.
             * (INCOMPLETE)
             * @param a - instance of adapterView
             * @param v - instance of View
             * @param position - current of listview subListOld
             * @param row - id of current item in row
             *
             * @see SubEditMenuActivity
             */
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long row) {
                setResult(RESULT_OK);
                Intent intent = new Intent(SubsDisplayActivity.this, SubEditMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates an ArrayAdapter instance for listview and loads a file containing subscriptions.
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        Log.i("LifeCycle --->", "onStart is called");

        adapter = new ArrayAdapter<Subscription>(SubsDisplayActivity.this, R.layout.list_item, R.id.list_item, subList);
        subListOld.setAdapter(adapter);
    }

    /**
     * Loads file containing subscriptions.
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // from stackoverflow: https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
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
     * Activates necessary behaviour when activity is stopped.
     *
     * @see AppCompatActivity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }
}

