package ca.ualberta.cs.patenio_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Activity that tells the user that their input for adding a new subscription is invalid.
 * Allows user to go back to SubAddMenuActivity to try again.
 *
 * @author patenio
 *
 * @see SubAddMenuActivity
 */
public class SubAddErrorActivity extends AppCompatActivity {

    /**
     * Creates a continue button to allow user to go back to SubMenuActivity to try add another
     * subscription with valid input.
     * @param savedInstanceState - previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_add_error);

        Button contButton = (Button) findViewById(R.id.cont);
        contButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines behaviour for continue button. Allows user to go back to SubMenuActivity.
             * @param view - instance of View object
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubAddErrorActivity.this, SubAddMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activates necessary behaviour upon starting activity.
     */
    @Override
    protected void onStart() {
        Log.i("LifeCycle --->", "ADDERRORCALLED is called");
        super.onStart();
    }

    /**
     * Activates necessary behaviour upon stopping activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
