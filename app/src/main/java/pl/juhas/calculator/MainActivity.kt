package pl.juhas.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val simpleCalculatorButton : Button = findViewById(R.id.simpleCalcButton)
        simpleCalculatorButton.setOnClickListener {
            changeActivity(it, SimpleCalculator::class.java)
        }

        val advancedCalculatorButton : Button = findViewById(R.id.advancedCalcButton)
        advancedCalculatorButton.setOnClickListener {
            changeActivity(it, AdvancedCalculator::class.java)
        }

        val aboutButton : Button = findViewById(R.id.aboutButton)
        aboutButton.setOnClickListener {
            changeActivity(it, About::class.java)
        }

        val exitButton : Button = findViewById(R.id.exitButton)
        exitButton.setOnClickListener {
            finish()
        }
    }





    fun changeActivity(view: View, activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}