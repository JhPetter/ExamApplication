package com.petter.examapplication.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.petter.examapplication.R

class CompassView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet),
    SensorEventListener {

    private var mPaint = Paint()
    private var textPaint = TextPaint()
    private val mPath = Path()
    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f
    private var sizeNeedle = 350f

    private var accelerometer: Sensor
    private var magnetometer: Sensor
    private var sensorManager: SensorManager

    private var currentDegree = 0.0f
    private var lastAccelerometer = FloatArray(3)
    private var lastMagnetometer = FloatArray(3)
    private var lastAccelerometerSet = false
    private var lastMagnetometerSet = false

    init {
        textPaint.textSize = context.resources.getDimension(R.dimen.textSizeSixteen)
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true

        mPaint.color = ContextCompat.getColor(context,
            R.color.colorPrimary
        )
        mPaint.strokeWidth = 2f
        mPaint.isAntiAlias = true

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun draw(canvas: Canvas?) {
        mCenterX = width / 2f
        mCenterY = height / 2f

        drawPathNeedle(canvas)
        drawPathNeedle(canvas, false)
        super.draw(canvas)
    }

    private fun drawPathNeedle(canvas: Canvas?, isToNorth: Boolean = true) {
        mPaint.color = ContextCompat.getColor(
            context, R.color.colorPrimary
        )
        mPaint.isAntiAlias = true
        if (isToNorth) {
            drawText(
                context.resources.getString(R.string.north), canvas,
                mCenterX, mCenterY - sizeNeedle
            )
            mPath.moveTo(mCenterX, mCenterY - sizeNeedle)
            mPath.lineTo((mCenterX - 30), mCenterY)
            mPath.lineTo(mCenterX, mCenterY + sizeNeedle)
            mPath.lineTo((mCenterX + 30), mCenterY)
            mPath.lineTo(mCenterX, mCenterY - sizeNeedle)
            drawText(
                context.resources.getString(R.string.south), canvas, mCenterX,
                mCenterY + sizeNeedle + 35
            )
        } else {
            drawText(
                context.resources.getString(R.string.west), canvas,
                mCenterX - sizeNeedle - 15,
                mCenterY + 15
            )
            mPath.moveTo(mCenterX - sizeNeedle, mCenterY)
            mPath.lineTo(mCenterX, mCenterY - 30)
            mPath.lineTo((mCenterX + sizeNeedle), mCenterY)
            mPath.lineTo(mCenterX, mCenterY + 30)
            mPath.lineTo(mCenterX - sizeNeedle, mCenterY)
            drawText(
                context.resources.getString(R.string.east), canvas,
                mCenterX + sizeNeedle + 15,
                mCenterY + 15
            )
        }

        mPath.close()

        canvas?.drawPath(mPath, mPaint)
        mPaint.color = ContextCompat.getColor(context,
            R.color.colorWhite
        )
        canvas?.drawCircle(mCenterX, mCenterY, 20f, mPaint)
    }

    private fun drawText(text: String, canvas: Canvas?, startX: Float, startY: Float) {
        canvas?.apply {
            drawText(text, startX, startY, textPaint)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            if (sensor === accelerometer) {
                lowPass(values, lastAccelerometer)
                lastAccelerometerSet = true
            } else if (sensor === magnetometer) {
                lowPass(values, lastMagnetometer)
                lastMagnetometerSet = true
            }

            if (lastAccelerometerSet && lastMagnetometerSet) {
                val r = FloatArray(9)
                if (SensorManager.getRotationMatrix(r, null, lastAccelerometer, lastMagnetometer)) {
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(r, orientation)
                    val degree = (Math.toDegrees(orientation[0].toDouble()) + 360).toFloat() % 360

                    val rotateAnimation = RotateAnimation(
                        currentDegree,
                        -degree,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                    )
                    rotateAnimation.duration = 1000
                    rotateAnimation.fillAfter = true

                    startAnimation(rotateAnimation)
                    currentDegree = -degree
                }
            }
        }

    }

    private fun lowPass(input: FloatArray, output: FloatArray) {
        val alpha = 0.05f
        for (i in input.indices) {
            output[i] = output[i] + alpha * (input[i] - output[i])
        }
    }
}