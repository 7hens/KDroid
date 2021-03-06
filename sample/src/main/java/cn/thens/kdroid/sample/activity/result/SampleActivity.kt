package cn.thens.kdroid.sample.activity.result

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import cn.thens.activity_request.ActivityIntentOptions
import cn.thens.kdroid.io.AndroidMainScope
import cn.thens.logdog.Logdog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SampleActivity : Activity(), CoroutineScope by AndroidMainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            Button(context).apply {
                text = "startForResultWithCallback"
                setOnClickListener {
                    launch {
                        val result = SecondActivity.startForResult(context)
                        Logdog.get().debug("resultCode: $result")
                    }
                }
            }.also { addView(it) }
        }
    }

    class SecondActivity : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(createContentView(this))
        }

        private fun createContentView(context: Context): View {
            return LinearLayout(context).apply {
                Button(context).apply {
                    text = "return"
                    setOnClickListener {
                        setResult(RESULT_OK)
                        finish()
                    }
                }.also { addView(it) }

                Button(context).apply {
                    text = "cancel"
                    setOnClickListener {
                        finish()
                    }
                }.also { addView(it) }
            }
        }

        companion object: ActivityIntentOptions<Companion>
    }
}