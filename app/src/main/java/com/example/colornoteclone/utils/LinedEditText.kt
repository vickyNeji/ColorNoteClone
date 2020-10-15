package com.example.colornoteclone.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

class LinedEditText(context:Context,arrr:AttributeSet) : androidx.appcompat.widget.AppCompatEditText(context) {



    private var mRect:Rect = Rect()
    private var mPaint:Paint = Paint()

    init {
        mPaint.style=Paint.Style.STROKE
        mPaint.strokeWidth= 2F
        mPaint.color=(0xFFFFD966.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        val height:Int=(this.parent as View).height
        val lineHeight:Int= lineHeight
        val numberOfLines=height/lineHeight

        val r:Rect=mRect
        val paint:Paint=mPaint

        var baseLine:Int=getLineBounds(0,r)

        for(i in 0..numberOfLines){
            canvas?.drawLine(r.left.toFloat(), (baseLine+1).toFloat(),
                r.right.toFloat(), (baseLine+1).toFloat(),paint)
            baseLine+=lineHeight
        }

        super.onDraw(canvas)
    }
}