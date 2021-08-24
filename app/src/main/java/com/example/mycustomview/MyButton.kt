package com.example.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class MyButton: AppCompatButton {

    private var enabledBackground: Drawable? = null
    private var disabledBackground: Drawable? = null
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // mengubah bentuk dari button
    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable

    /*
    Ketika Anda menggunakan CustomView, jangan memanggil resource di dalam metode onDraw() karena
    resource akan dipanggil terus menerus ketika activity berhasil dipanggil.
    Oleh karena itu, tempatkan pemanggilan resource ke dalam constructor.
     */

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //memasukkan background dan attribute lain pada MyButton (Mengubah background dari Button)
        background = when {
            isEnabled -> enabledBackground
            else -> disabledBackground
        }
        // Mengubah warna text pada button
        setTextColor(txtColor)
        // Mengubah ukuran text pada button
        textSize = 12f
        // Menjadikan object pada button menjadi center
        gravity = Gravity.CENTER
        // Mengubah text pada button pada kondisi enable dan disable
        text = when {
            isEnabled -> "Submit"
            else -> "Isi Dulu"
        }
    }
    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ResourcesCompat.getDrawable(resources, R.drawable.bg_button, null)
        disabledBackground = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_disable, null)
    }
}