package com.example.mycustomview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class MyEditText: AppCompatEditText, View.OnTouchListener {

    internal lateinit var mClearButtonImage: Drawable

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private fun init(){
        mClearButtonImage = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_close_24,null) as Drawable
        setOnTouchListener(this)
        // menampilkan clearbutton ketika ada perubahan teks
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
    // aksi ketika di klik akan dihapus
    private fun showClearButton() {
        // menampilkan gambar pada EditText dengan parameter sebagai berikut (left, top, right, bottom)
        // Karena itulah pada showClearButton kita memasukkan gambar pada parameter ketiga karena kita ingin menampilkan gambar pada sebelah kanan EditText,
        setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null)
    }
    private fun hideClearButton() {
        // hideClearButton diisi dengan null semua untuk menghilangkan gambar
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        hint = "Masukkan nama Anda disini"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    // ketika komponen ditekan, maka akan terbaca pada OnTouch
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            //dilakukan pengecekan apakah area yang ditekan adalah area tempat tombol silang berada. Jika iya maka isClearButtonClick akan kita tandai true
            when (layoutDirection) {
                //pengecekan jenis handphone apakah menggunakan format RTL (Right-to-left) seperti bahasa Arab atau format LTR (Left-to-right) seperti bahasa Indonesia/Inggris
                View.LAYOUT_DIRECTION_RTL -> {
                    clearButtonEnd = (mClearButtonImage.intrinsicWidth + paddingStart).toFloat()
                    when {
                        event.x < clearButtonEnd -> isClearButtonClicked = true
                    }
                }
                else -> {
                    clearButtonStart = (width - paddingEnd - mClearButtonImage.intrinsicWidth).toFloat()
                    when {
                        event.x > clearButtonStart -> isClearButtonClicked = true
                    }
                }
            }
            when {
                isClearButtonClicked ->
                    when {
                        // ketika aksi ACTION_DOWN (ketika tombol ditekan) tombol akan tetap tampil. dan menampilkan showClearButton
                    event.action == MotionEvent.ACTION_DOWN -> {
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_close_24, null) as Drawable
                        showClearButton()
                        return true
                    }
                        //namun ketika ACTION_UP (ketika tombol dilepas setelah ditekan) teks yang di dalam EditText akan dihapus (clear) dan tombol silang akan disembunyikan dengan memanggil fungsi hideClearButton
                    event.action == MotionEvent.ACTION_UP -> {
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_close_24, null) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
                else -> return false
            }
        }
        return false
    }
}