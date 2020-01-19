package com.petter.examapplication.ui.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.petter.examapplication.R
import com.petter.examapplication.ui.compass.CompassActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_error.*
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeTimestamp: Long = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            val force = sqrt(
                getValue(this.values[0]).times(2)
                        + getValue(this.values[1]).times(2)
                        + getValue(this.values[2]).times(2)
            )
            if (force > 2.7f) {
                if (mShakeTimestamp + 500 > System.currentTimeMillis()) return
                mShakeTimestamp = System.currentTimeMillis()
                onShake()
            }
        }
    }

    private fun onShake() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getValue(value: Float): Float {
        return value.div(SensorManager.GRAVITY_EARTH)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomSheetBehavior = BottomSheetBehavior.from(contentBottom)
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        stepperView.countSteps = 3
        btnOpenCompass.setOnClickListener {
            CompassActivity.start(this)
        }
    }

}
