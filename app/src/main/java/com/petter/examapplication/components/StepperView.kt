package com.petter.examapplication.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.petter.examapplication.R


class StepperView(context: Context?, private val attrs: AttributeSet?) : View(context, attrs) {
    private val linePaint = Paint()
    private val textPaint = TextPaint()
    private var mWidth = 0
    private var selectPosition = 2

    var selectedColor: Int = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }
    var unSelectedColor: Int = Color.GRAY
        set(value) {
            field = value
            invalidate()
        }
    var heightLine: Float = 1f
        set(value) {
            field = value
            invalidate()
        }
    var radius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var iconSelected: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    var countSteps: Int = 3
        set(value) {
            field = value
            invalidate()
        }

    init {
        linePaint.color = Color.BLUE
        linePaint.strokeWidth = toDp(heightLine)
        linePaint.isAntiAlias = true

        textPaint.color = Color.WHITE
        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        context?.apply {
            textPaint.textSize = resources.getDimension(R.dimen.textSizeFourteen)
        }

        loadAttributes()
    }

    private fun loadAttributes() {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.StepperView)
        selectedColor = attributeSet.getColor(R.styleable.StepperView_selectedColor, Color.BLUE)
        unSelectedColor = attributeSet.getColor(R.styleable.StepperView_unselectedColor, Color.GRAY)
        heightLine = attributeSet.getDimension(R.styleable.StepperView_heightLine, 3f)
        radius = attributeSet.getDimension(R.styleable.StepperView_radiusStep, 24f).div(2)
        iconSelected = attributeSet.getDrawable(R.styleable.StepperView_iconSelected)
            ?: context.getDrawable(android.R.drawable.star_on)
        attributeSet.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        mWidth = width
        drawLines(canvas)
        println("AQUI: $radius")
    }

    private fun drawLines(canvas: Canvas?) {
        val numberLines = countSteps - 1
        val sizeLines = (mWidth - (calculateMargin() * 2)) / numberLines
        var startX = calculateMargin()
        for (value: Int in 1..numberLines) {
            linePaint.color = getSelectColor(value)
            canvas?.drawLine(
                startX,
                height.toFloat() / 2,
                startX + sizeLines,
                height.toFloat() / 2,
                linePaint
            )
            drawPoint(canvas, startX, value)
            startX += sizeLines
        }
        drawPoint(canvas, mWidth.toFloat() - calculateMargin(), countSteps)
    }

    private fun drawTextStepper(canvas: Canvas?, startWidth: Float, value: String) {
        canvas?.apply {
            drawText(value, startWidth, height.div(1.5).toFloat(), textPaint)
        }
    }

    private fun calculateMargin(): Float {
        if (iconSelected != null)
            return iconSelected?.intrinsicWidth?.toFloat() ?: 0f
        return radius
    }

    private fun drawPoint(canvas: Canvas?, startWidth: Float, position: Int) {
        if (selectPosition == position)
            drawSelectPoint(canvas, startWidth)
        else
            canvas?.drawCircle(
                startWidth,
                height.toFloat() / 2,
                radius,
                linePaint
            )
        drawTextStepper(canvas, startWidth, position.toString())
    }

    private fun drawSelectPoint(canvas: Canvas?, startWidth: Float) {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = selectedColor
        canvas?.drawCircle(startWidth, height.toFloat() / 2, radius, paint)
        drawTextStepper(canvas, startWidth, selectPosition.toString())
    }

    private fun getSelectColor(position: Int): Int {
        return if (position < selectPosition) selectedColor else unSelectedColor
    }

    private fun toDp(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context?.resources?.displayMetrics
        )
    }

    fun setSelected(position: Int) {
        if (position < 1 || position > countSteps)
            return
        selectPosition = position
        invalidate()
    }
}