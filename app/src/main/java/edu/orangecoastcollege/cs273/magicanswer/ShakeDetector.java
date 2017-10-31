package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by pdavis11 on 10/31/2017.
 */
public class ShakeDetector implements SensorEventListener {

    private static final long ELAPSED_TIME = 1000L;
    // Accelerometer data uses the float
    private static final float THRESHOLD = 20;

    private long previousShake;

    private  onShakeListener mListener;

    /**
     * Enables the shakeDetector to accept the accelerometers movement
     * @param listener Interface of the movement
     */
    public ShakeDetector(onShakeListener listener){
        mListener = listener;
    }

    /**
     * Monitors the difference of how far the movement has traveled. Once recorded, the data is
     * computed to find out if enough movement has occurred to change the MagicAnswer
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // Ignore all other events, except ACCELEROMETERS
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Neutralize the effect of gravity (subtract it out from  each value)
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;

            float netForce = (float) Math.sqrt(Math.pow(gForceX, 2) + Math.pow(gForceY, 2) + Math.pow(gForceZ, 2));

            if (netForce >= THRESHOLD){
                // Get current time
                long currentTime = System.currentTimeMillis();
                if (currentTime > previousShake + ELAPSED_TIME){
                    // Reset the previous shake to the current time
                    previousShake = currentTime;

                    // Register a shake event (it qualifies)
                    mListener.onShake();
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do Nothing, not being used

    }

    // Define an interface for others to implement whenever a
    // true shake occurs. Interface = contract (method declaration WITHOUT implementation)
    // Some other class has to implement the method.
    public interface onShakeListener{
        void onShake();
    }
}
