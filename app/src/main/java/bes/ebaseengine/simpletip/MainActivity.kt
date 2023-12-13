package bes.ebaseengine.simpletip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.text.NumberFormat
import android.view.inputmethod.InputMethodManager
import bes.ebaseengine.simpletip.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()

            val view: View? = this.currentFocus
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
    private fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            binding.totalResult.text = ""
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else ->0.05
        }
        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked
        var totalwTip = tip + cost

        if(roundUp){
            tip = kotlin.math.ceil(totalwTip) - cost
            totalwTip = tip + cost
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotal = NumberFormat.getCurrencyInstance().format(totalwTip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        binding.totalResult.text = getString(R.string.total_amount,formattedTotal)
    }
}