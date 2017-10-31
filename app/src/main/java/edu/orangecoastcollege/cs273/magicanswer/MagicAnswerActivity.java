package edu.orangecoastcollege.cs273.magicanswer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * The controller - Creates variables that allow the program to interact with the devices hardware.
 */
public class MagicAnswerActivity extends AppCompatActivity {

    MagicAnswer magicAnswer;
    private TextView answerTextView;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    // Create a reference to our ShakeDetector
    private ShakeDetector mShakeDector;

    /**
     * Sets up the user interface and initializes the UI that user will interact with.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_answer);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        // TASK 2: CREATE A NEW MAGIC ANSWER OBJECT
        magicAnswer = new MagicAnswer(this);

        // TASK 3: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDector = new ShakeDetector(new ShakeDetector.onShakeListener() {
            @Override
            public void onShake() {
                displayMagicAnswer();
            }
        });
    }

    /**
     * Displays the answer once onShake() is called.
     */
    public void displayMagicAnswer(){
        String randomAnswer = magicAnswer.getRandomAnswer();
        answerTextView.setText(randomAnswer);
    }

    /**
     * Enables the reset of the app if exited
     */
    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mShakeDector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Enables the reset of the app if exited
     */
    @Override
    protected void onStop(){
        super.onStop();
        mSensorManager.unregisterListener(mShakeDector, accelerometer);
    }
}
