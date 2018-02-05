package ca.ualberta.cs.patenio_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Allows the user to add new subscriptions. User input is read. Input must be validated.
 *
 * @author patenio
 */
public class SubAddMenuActivity  extends AppCompatActivity {

    private EditText nametext;
    private EditText datetext;
    private EditText monthlychargetext;
    private EditText commenttext;

    private ArrayList<Subscription> subList;

    private static String FILENAME = "sub_list.sav";

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private double monthlycharge;
    private Date date;
    private String name;
    private String comment;
    private String inputdate;


    /**
     * Creates and initializes EditText (where user will enter input) and buttons
     * once app is created. Buttons allow the user to save and add new subscription or to cancel
     * the activity and go back to the main activity (SubBookActivity).
     *
     * @param savedInstanceState - previously saved state of the app.
     *
     * @see SubBookActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_add_menu);

        // EditText - get user input
        nametext = (EditText) findViewById(R.id.nametext);
        datetext = (EditText) findViewById(R.id.datetext);
        monthlychargetext = (EditText) findViewById(R.id.monthlychargetext);
        commenttext = (EditText) findViewById(R.id.commenttext);

        // Buttons - create buttons
        Button saveButton = (Button) findViewById(R.id.save);
        Button cancelButton = (Button) findViewById(R.id.delete);

        saveButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines behaviour of save button when clicked. Read user input from EditText
             * and validate input. If invalid, tell user there is an error by calling
             * InputErrorException and SubAddErrorActivity,
             *
             * Once input is validated, add new subscription and save it to the file.
             *
             * @param view - an instance of View object
             *
             * @see SubAddErrorActivity
             * @see InputErrorException
             * @see Subscription
             */
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                // Get minimal input and validate them.
                try {
                    name = nametext.getText().toString();
                    inputdate = datetext.getText().toString();
                    date = format.parse(inputdate);
                    monthlycharge = Double.parseDouble(monthlychargetext.getText().toString());

                    // Invalid Input but correct formatting.
                    if (name.isEmpty()){
                        throw new InputErrorException();
                    }
                    else if (inputdate.isEmpty()){
                        throw new InputErrorException();
                    }
                    else if (monthlycharge < 0) {
                        throw new InputErrorException();
                    }
                    // Valid input
                    else{
                        comment = commenttext.getText().toString();

                        // No comment
                        if (comment.isEmpty()) {
                            Log.i("LifeCycle --->", "NOCOMMENTCALL is called");
                            Subscription subscription = new Subscription(name, date, monthlycharge);
                            subList.add(subscription);
                            saveInFile();
                            Intent intent = new Intent(SubAddMenuActivity.this, SubsDisplayActivity.class);
                            startActivity(intent);
                        }
                            // Has comment
                         else {
                            Log.i("LifeCycle --->", "YESCOMMENTCALL is called");
                            Subscription subscription = new Subscription(name, date, monthlycharge, comment);
                            subList.add(subscription);
                            saveInFile();
                            Intent intent = new Intent(SubAddMenuActivity.this, SubsDisplayActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                // Invalid input and syntax or type.
                catch(NumberFormatException e){
                    Log.i("LifeCycle --->", "NUMBERFORMAT is called");
                    Intent intent = new Intent(SubAddMenuActivity.this, SubAddErrorActivity.class);
                    startActivity(intent);
                }
                catch(ParseException e){
                    Log.i("LifeCycle --->", "PARSE is called");
                    Intent intent = new Intent(SubAddMenuActivity.this, SubAddErrorActivity.class);
                    startActivity(intent);
                }
                catch(InputErrorException e){
                    Log.i("LifeCycle --->", "ERROR is called");
                    Intent intent = new Intent(SubAddMenuActivity.this, SubAddErrorActivity.class);
                    startActivity(intent);
                }
            } // End of onClick (save)
        }); // End of setOnClickListener

        cancelButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Determines behaviour of of cancel button. When clicked, lead user back to the main
             * activity (SubBookActivity). No input is saved and no new subscriptions are made.
             * @param v - instance of View object.
             */
           @Override
            public void onClick(View v){
               setResult(RESULT_OK);
               Intent intent = new Intent(SubAddMenuActivity.this, SubBookActivity.class);
               startActivity(intent);
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
     * Saves any new subscriptions into file. Gson is used to convert ArrayList to Json.
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
     *
     * @see Subscription
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
            subList = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Activates necessary behaviour when activity is closed.
     *
     * @see AppCompatActivity
     */
    protected void onDestroy() {
        super.onDestroy();
    }
}


