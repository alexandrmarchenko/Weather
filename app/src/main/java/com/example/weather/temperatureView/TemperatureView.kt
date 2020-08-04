package com.example.weather.temperatureView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.weather.R


class TemperatureView : View {

    private var batteryColor: Int = Color.GRAY
    private var tempColor: Int = Color.RED
    private var lineColor: Int = Color.BLACK
    private var emptyColor: Int = Color.WHITE

    private val thermometerRectangle = RectF()
    private val tempRectangle: Rect = Rect()
    private val emptyRectangle: Rect = Rect()

    private var thermometerPaint: Paint = Paint()
    private var emptyPaint: Paint = Paint()
    private var tempPaint: Paint = Paint()
    private var linePaint: Paint = Paint()

    private var width_ = 0
    private var height_ = 0
    private val tempHeight = 3.6f
    private var radius = 0.0f
    var temperature = 50.0f
    set(value) {
        field = value
        invalidate()
    }

    private val padding = 10
    private val round = 5.0f
    private var textAreaWidth = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
        initAttr(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
        initAttr(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
        initAttr(context, attrs)
    }

    private fun init() {
        thermometerPaint.color = batteryColor
        thermometerPaint.style = Paint.Style.FILL
        tempPaint.color = tempColor
        tempPaint.style = Paint.Style.FILL
        linePaint.color = lineColor
        linePaint.style = Paint.Style.FILL
        linePaint.strokeWidth = 3.0f

        emptyPaint.color = emptyColor
        emptyPaint.style = Paint.Style.FILL
    }

    private fun initAttr(context: Context?, attrs: AttributeSet?) {
        val typedArray =
            context!!.obtainStyledAttributes(attrs, R.styleable.ThermometerView, 0, 0)

        batteryColor =
            typedArray.getColor(R.styleable.ThermometerView_thermometer_color, Color.GRAY)

        tempColor = typedArray.getColor(R.styleable.ThermometerView_temperature_color, Color.GREEN)

        temperature = typedArray.getFloat(R.styleable.ThermometerView_temperature, 30.0f)

        typedArray.recycle()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textAreaWidth = ((w - paddingLeft - paddingRight) * 0.6).toInt()
        linePaint.textSize = textAreaWidth / 3.0f

        width_ = w - paddingLeft - paddingRight - textAreaWidth
        height_ = h - paddingTop - paddingBottom
        radius = (width_ - 3 * padding) / 2.0f
        thermometerRectangle.set(
            padding.toFloat(), (padding).toFloat(), width_ - padding.toFloat(),
            (height_ - padding).toFloat()
        )
        tempRectangle.set(
            3 * padding, (height_ / tempHeight + 2 * padding).toInt(), width_ - padding * 3,
            (height_ - 2 * padding - radius.toInt())
        )

        tempRectangle.set(
            3 * padding, (height_ / tempHeight + 2 * padding).toInt(), width_ - padding * 3,
            (height_ - 2 * padding - radius.toInt())
        )

        emptyRectangle.set(
            3 * padding, 2 * padding, width_ - padding * 3,
            (height_ / tempHeight + 2 * padding).toInt()
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(thermometerRectangle, round, round, thermometerPaint);
        canvas?.drawRect(tempRectangle, tempPaint);
        canvas?.drawRect(emptyRectangle, emptyPaint);
        canvas?.drawLine(
            padding / 2.0f,
            height_ / tempHeight + 2.0f * padding,
            width_ - padding / 2.0f,
            height_ / tempHeight + 2.0f * padding, linePaint
        )
        canvas?.drawCircle(
            width_ / 2.0f,
            height_ - padding - (width_ - padding) / 2.0f,
            radius,
            tempPaint
        )
        canvas?.drawText(temperature.toString(), width_.toFloat(), height_ / tempHeight, linePaint)
    }
}