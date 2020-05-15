package io.github.mattpvaughn.compass.features.library

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.mattpvaughn.compass.application.APP_NAME
import io.github.mattpvaughn.compass.databinding.FragmentLibraryBinding


class CompassFragment : Fragment() {

    companion object {
        fun newInstance() = CompassFragment()
    }

    var magnetometerReading = FloatArray(3)
    var accelerometerReading = FloatArray(3)
    val rotationMatrix = FloatArray(9)
    val orientationAngles = FloatArray(3)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLibraryBinding.inflate(inflater, container, false)

        // First, get an instance of the SensorManager
        val sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

        // Second, get the sensor you're interested in
        val magnetSensor: Sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val accelSensor: Sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Third, implement a SensorEventListener class
        val magnetListener: SensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // do things if you're interested in accuracy changes
            }

            override fun onSensorChanged(event: SensorEvent) {
                // implement what you want to do here
                if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    magnetometerReading[0] = event.values[0]
                    magnetometerReading[1] = event.values[1]
                    magnetometerReading[2] = event.values[2]
                }
                if (event.sensor.type ==Sensor.TYPE_ACCELEROMETER) {
                    accelerometerReading[0] = event.values[0]
                    accelerometerReading[1] = event.values[1]
                    accelerometerReading[2] = event.values[2]
                }

                // Rotation matrix based on current readings from accelerometer and magnetometer.
                SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)

                // Express the updated rotation matrix as three orientation angles.
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                val rotation = -1 * (orientationAngles[0] * (57.32484076F))
                Log.i(APP_NAME, rotation.toString())
                Log.i(APP_NAME, orientationAngles.contentToString())

                ObjectAnimator.ofFloat(binding.needle, "rotation", rotation).apply {
                    duration = 330
                    start()
                }
            }
        }


        sensorManager.registerListener(magnetListener, magnetSensor, 330000)
        sensorManager.registerListener(magnetListener, accelSensor, 330000)

        return binding.root
    }

}
