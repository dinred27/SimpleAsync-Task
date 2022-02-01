package com.example.simpleasynctask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var mTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.tv);
        if(savedInstanceState!=null) {
            mTextView.setText(savedInstanceState.getString("prev"));
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("prev",mTextView.text.toString())
    }

    fun startTask(view: android.view.View) {
        mTextView.setText(R.string.napping);
        SimpleAsyncTask(mTextView).execute()

    }
}

class SimpleAsyncTask(tv:TextView) : AsyncTask<Void?, Void?, String?>() {
    private var mTextView: WeakReference<TextView>? = null
    init {
        mTextView = WeakReference(tv)
    }
    override fun doInBackground(vararg p0: Void?): String? {
        val r = Random()
        val n: Int = r.nextInt(11)

        val s = n * 200
        try {
            Thread.sleep(s.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return "Awake at last after sleeping for $s milliseconds!"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        mTextView?.get()?.setText(result)
    }
}