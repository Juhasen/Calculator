package pl.juhas.calculator

import net.objecthunter.exp4j.ExpressionBuilder
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.function.Function
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class AdvancedCalculator : AppCompatActivity() {

    companion object {
        private const val KEY_DISPLAY_TEXT = "display_text"
        private const val KEY_RESULT_PREVIEW_TEXT = "result_preview_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.advanced_calculator)

        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val button0: Button = findViewById(R.id.button0)
        val buttonPlus: Button = findViewById(R.id.buttonAdd)
        val buttonMinus: Button = findViewById(R.id.buttonSubtract)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val buttonDivide: Button = findViewById(R.id.buttonDivide)
        val buttonEquals: Button = findViewById(R.id.buttonEquals)
        val buttonClear: Button = findViewById(R.id.buttonClear)
        val buttonDot: Button = findViewById(R.id.buttonDot)
        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonSign: Button = findViewById(R.id.buttonChangeSign)

        // Add buttons for advanced operations
        val buttonSin: Button = findViewById(R.id.buttonSin)
        val buttonCos: Button = findViewById(R.id.buttonCos)
        val buttonTan: Button = findViewById(R.id.buttonTan)
        val buttonLn: Button = findViewById(R.id.buttonLn)
        val buttonSqrt: Button = findViewById(R.id.buttonSqrt)
        val buttonPower: Button = findViewById(R.id.buttonPower)
        val buttonPowerY: Button = findViewById(R.id.buttonPowerY)
        val buttonLog: Button = findViewById(R.id.buttonLog)
        val buttonPercent: Button = findViewById(R.id.buttonPercent)


        // Set click listeners
        button1.setOnClickListener { onButtonClick(it) }
        button2.setOnClickListener { onButtonClick(it) }
        button3.setOnClickListener { onButtonClick(it) }
        button4.setOnClickListener { onButtonClick(it) }
        button5.setOnClickListener { onButtonClick(it) }
        button6.setOnClickListener { onButtonClick(it) }
        button7.setOnClickListener { onButtonClick(it) }
        button8.setOnClickListener { onButtonClick(it) }
        button9.setOnClickListener { onButtonClick(it) }
        button0.setOnClickListener { onButtonClick(it) }
        buttonPlus.setOnClickListener { onButtonClick(it) }
        buttonMinus.setOnClickListener { onButtonClick(it) }
        buttonMultiply.setOnClickListener { onButtonClick(it) }
        buttonDivide.setOnClickListener { onButtonClick(it) }
        buttonEquals.setOnClickListener { onEqualsClick() }
        buttonClear.setOnClickListener { onClearClick() }
        buttonDot.setOnClickListener { onButtonClick(it) }
        buttonBack.setOnClickListener { onBackClick() }
        buttonSign.setOnClickListener { onSignClick() }

        // Set click listeners for advanced operations
        buttonSin.setOnClickListener { onButtonClick(it) }
        buttonCos.setOnClickListener { onButtonClick(it) }
        buttonTan.setOnClickListener { onButtonClick(it) }
        buttonLn.setOnClickListener { onButtonClick(it) }
        buttonSqrt.setOnClickListener { onButtonClick(it) }
        buttonPower.setOnClickListener { onButtonClick(it) }
        buttonPowerY.setOnClickListener { onButtonClick(it) }
        buttonLog.setOnClickListener { onButtonClick(it) }
        buttonPercent.setOnClickListener { onButtonClick(it) }

        // Restore saved state if available
        if (savedInstanceState != null) {
            val display: TextView = findViewById(R.id.display)
            val resultPreview: TextView = findViewById(R.id.resultPreview)

            display.text = savedInstanceState.getString(KEY_DISPLAY_TEXT, "")
            resultPreview.text = savedInstanceState.getString(KEY_RESULT_PREVIEW_TEXT, "0")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val display: TextView = findViewById(R.id.display)
        val resultPreview: TextView = findViewById(R.id.resultPreview)

        // Save the current display and result preview text
        outState.putString(KEY_DISPLAY_TEXT, display.text.toString())
        outState.putString(KEY_RESULT_PREVIEW_TEXT, resultPreview.text.toString())
    }


    fun onButtonClick(view: View) {
        val button: Button = view as Button
        val buttonText: String = button.text.toString()
        val display: TextView = findViewById(R.id.display)
        var currentText: String = display.text.toString()

        if (buttonText in listOf("sin", "cos", "tan", "log", "log10", "sqrt")) {
            if (currentText.isNotEmpty() && currentText.last().isDigit()) {
                val newText = "$buttonText($currentText)"
                display.text = newText
                onEqualsClick()
                return
            } else {
                Toast.makeText(this, "Nie można użyć tej funkcji bez liczby", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (buttonText in listOf("x^2", "x^y")) {
            if (currentText.isEmpty()) {
                Toast.makeText(this, "Nie można użyć tej funkcji bez liczby", Toast.LENGTH_SHORT).show()
                return
            }
            val newText: String = if (buttonText == "x^2") {
                "$currentText^2"
            } else {
                "$currentText^"
            }
            display.text = newText
            val resultPreview: TextView = findViewById(R.id.resultPreview)
            resultPreview.text = evaluate(newText).toString()
            return
        }


        if (currentText == "0" && buttonText != "." && buttonText != "-") {
            display.text = buttonText
            val resultPreview: TextView = findViewById(R.id.resultPreview)
            resultPreview.text = evaluate(buttonText).toString()
            return
        }

        // Handle when the user try to input "05" or "06" etc.
        if (currentText.isNotEmpty()) {
            if (currentText.startsWith("0") && buttonText != "." && buttonText != "-" && !currentText.contains('.') && !currentText.contains('^')) {
                display.text = buttonText
                val resultPreview: TextView = findViewById(R.id.resultPreview)
                resultPreview.text = evaluate(buttonText).toString()
                return
            }
            if (buttonText in "+-*/" && currentText.last() in "+-*/") {
                return
            }
        }

        // Handle blocking adding too many dots
        if (buttonText == ".") {
            if (currentText.isEmpty()) {
                display.text = getString(R.string.decimal_zero_format)
                return
            } else if (currentText.last() in "+-*/") {
                display.text = getString(R.string.add_zero_decimal_format, currentText)
                return
            }

            // Split the expression by operators to get individual numbers
            val parts = currentText.split(Regex("[+\\-*/]"))

            // Check if the last number already contains a decimal point
            if (parts.isNotEmpty() && parts.last().contains('.')) {
                return
            }
        }


        if (buttonText == "%") {
            if (currentText.isNotEmpty() && currentText.last().isDigit()) {
                // Match the last number before % and the operator before it (if any)
                val matchResult = Regex("([-+/*]?\\s*\\d+(\\.\\d+)?)\\s*$").find(currentText)
                if (matchResult != null) {
                    val numberStr = matchResult.value.trim()
                    val number = numberStr.toDouble()

                    // Find the previous number for percentage calculation
                    val operatorMatch = Regex("([-+/*])\\s*\\d+(\\.\\d+)?\\s*$").find(currentText)
                    if (operatorMatch != null) {
                        val operator = operatorMatch.groupValues[1]

                        // Find the number before the operator
                        val beforeOperatorMatch = Regex("(\\d+(\\.\\d+)?)\\s*[-+/*]").find(currentText)
                        if (beforeOperatorMatch != null) {
                            val baseNumber = beforeOperatorMatch.value.dropLast(1).toDouble()

                            val percentageValue = abs((baseNumber * number) / 100.0)
                            currentText =  "$percentageValue"

                            display.text = currentText
                            val resultPreview: TextView = findViewById(R.id.resultPreview)
                            resultPreview.text = evaluate(currentText).toString()
                            return
                        }
                    }

                    // Default behavior if there's no operator before the percentage
                    val percentageValue = number * 0.01
                    currentText = currentText.dropLast(numberStr.length) + "$percentageValue"

                    display.text = currentText
                    val resultPreview: TextView = findViewById(R.id.resultPreview)
                    resultPreview.text = evaluate(currentText).toString()
                }
            }
            return
        }

        val newText: String = currentText + buttonText
        display.text = newText


        val resultPreview: TextView = findViewById(R.id.resultPreview)
        resultPreview.text = evaluate(newText).toString()
    }

    fun onClearClick() {
        val display: TextView = findViewById(R.id.display)
        display.text = ""
        val resultPreview: TextView = findViewById(R.id.resultPreview)
        resultPreview.text = "0"
    }

    fun onBackClick() {
        val display: TextView = findViewById(R.id.display)
        val currentText: String = display.text.toString()
        if (currentText.isNotEmpty()) {
            display.text = currentText.substring(0, currentText.length - 1)
            val resultPreview: TextView = findViewById(R.id.resultPreview)
            resultPreview.text = evaluate(display.text.toString()).toString()
        } else {
            val resultPreview: TextView = findViewById(R.id.resultPreview)
            resultPreview.text = ""
        }
    }

    fun onSignClick() {
        val display: TextView = findViewById(R.id.display)
        val currentText: String = display.text.toString()

        if (currentText.isNotEmpty()) {
            // First, check for operators at the end
            val operatorPattern = Regex("([-+*/])(-?)(\\d+\\.?\\d*)$")
            val match = operatorPattern.find(currentText)

            if (match != null) {
                // We found an operator followed by a number (possibly with a negative sign)
                val operator = match.groupValues[1]  // The main operator (-, +, *, /)
                val signPart = match.groupValues[2]  // Possible negative sign after the operator
                val number = match.groupValues[3]    // The number after the operator and sign

                // Handle different operator cases
                if (operator == "+" || operator == "-") {
                    // For + and -, toggle the operator
                    val newOperator = if (operator == "-") "+" else "-"
                    val prefixEndIndex = match.range.first
                    val newText = currentText.substring(0, prefixEndIndex) + newOperator + number

                    display.text = newText
                    val resultPreview: TextView = findViewById(R.id.resultPreview)
                    resultPreview.text = evaluate(newText).toString()
                } else if (operator == "*" || operator == "/") {
                    // For * and /, toggle the presence of negative sign
                    val prefixEndIndex = match.range.first + 1  // +1 to include the operator
                    val newText: String

                    if (signPart == "-") {
                        // If negative, remove the negative sign
                        newText = currentText.substring(0, prefixEndIndex) + number
                    } else {
                        // If positive, add a negative sign
                        newText = currentText.substring(0, prefixEndIndex) + "-" + number
                    }

                    display.text = newText
                    val resultPreview: TextView = findViewById(R.id.resultPreview)
                    resultPreview.text = evaluate(newText).toString()
                }
            } else {
                // No operator found, checking for a single number
                val singleNumberPattern = Regex("^(-?\\d+\\.?\\d*)$")
                val singleMatch = singleNumberPattern.find(currentText)

                if (singleMatch != null) {
                    // Toggle the sign of the single number
                    val number = singleMatch.value.toDouble()
                    val newNumber = -number
                    display.text = newNumber.toString().removeSuffix(".0")

                    val resultPreview: TextView = findViewById(R.id.resultPreview)
                    resultPreview.text = evaluate(newNumber.toString()).toString()
                }
            }
        }
    }

    fun onEqualsClick() {
        val display: TextView = findViewById(R.id.display)
        val currentText: String = display.text.toString()

        if (currentText.contains("/0")) {
            Toast.makeText(this, "Nie dzielimy przez ZERO!", Toast.LENGTH_SHORT).show()
            return
        }

        val result: String = evaluate(currentText)

        if (result.isEmpty()) {
            Toast.makeText(this, "Złe wyrażenie matematyczne", Toast.LENGTH_SHORT).show()
            return
        }

        display.text = result

        val resultPreview: TextView = findViewById(R.id.resultPreview)
        resultPreview.text = ""
    }

    fun evaluate(expression: String): String {
        try {
            if (expression.contains("/0")) {
                Toast.makeText(this, "Nie dzielimy przez ZERO!", Toast.LENGTH_SHORT).show()
                return ""
            }
            // Create an expression builder
            val expressionBuilder = ExpressionBuilder(expression)

            // Add custom functions that work with degrees
            expressionBuilder.function(object : Function("sin", 1) {
                override fun apply(vararg args: Double): Double {
                    return sin(Math.toRadians(args[0]))
                }
            })

            expressionBuilder.function(object : Function("cos", 1) {
                override fun apply(vararg args: Double): Double {
                    return cos(Math.toRadians(args[0]))
                }
            })

            expressionBuilder.function(object : Function("tan", 1) {
                override fun apply(vararg args: Double): Double {
                    return tan(Math.toRadians(args[0]))
                }
            })

            val result = expressionBuilder.build().evaluate()

            return if (result.isNaN() || result.isInfinite()) {
                ""
            } else {
                // Check if very close to common exact values
                val epsilon = 1e-14
                val finalResult = when {
                    abs(result - 0.5) < epsilon -> 0.5
                    abs(result - 1.0) < epsilon -> 1.0
                    abs(result - 0.0) < epsilon -> 0.0
                    // Add other common values as needed
                    else -> result
                }
                finalResult.toString().trimEnd('0').trimEnd('.')
            }
        } catch (e: Exception) {
            return ""
        }
    }
}