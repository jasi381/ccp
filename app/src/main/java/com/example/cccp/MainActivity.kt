package com.example.cccp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.cccp.databinding.ActivityMainBinding
import com.example.mylibrary.checkValidateNumber
import com.example.mylibrary.getFormattedNumber

//import com.google.i18n.phonenumbers.PhoneNumberUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.ccp.apply {
            setDialogBackgroundColor(getColor(R.color.dialogBack))
            setDialogTextColor(getColor(R.color.black))
        }


        binding.validate.setOnClickListener {
            var code : String = binding.ccp.selectedCountryNameCode
            val number: String = binding.number.text.toString()

            if (number.isEmpty()){

                binding.numberLay.apply {
                    boxStrokeColor = getColor(R.color.error_text)
                    hintTextColor = getColorStateList(R.color.error_text)
                    error = "Mobile number is required"
                    startIconDrawable?.setTint(getColor(R.color.error_text))
                }
            }
            else {
                //listener to get the country code when the country is changed
                binding.ccp.setOnCountryChangeListener {
                    val countryCode = binding.ccp.selectedCountryNameCode
                    code = countryCode
                    binding.formattedNo.text = ""
                    binding.validStatus.text = ""
                    binding.number.setText("")
                    binding.numberLay.startIconDrawable?.setTint(getColor(R.color.purple_700))
                }
                val tv =  checkValidateNumber(code, number)

                binding.formattedNo.text = "Is valid : $tv"

                val formattedNumber = getFormattedNumber(code, number)
                binding.validStatus.text = "Formatted number is: $formattedNumber"

            }
        }

        //text watcher
        binding.number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                binding.numberLay.apply {
                    boxStrokeColor = getColor(R.color.purple_700)
                    hintTextColor = getColorStateList(R.color.black)
                    isErrorEnabled = false
                    startIconDrawable?.setTint(getColor(R.color.purple_700))
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getFormattedNumber(binding.ccp.selectedCountryNameCode, s.toString())

                binding.apply {
                    numberLay.boxStrokeColor = getColor(R.color.purple_700)
                    formattedNo.text = ""
                    validStatus.text = ""
                    numberLay.isErrorEnabled = false
                    numberLay.hintTextColor = getColorStateList(R.color.black)
                    numberLay.startIconDrawable?.setTint(getColor(R.color.purple_700))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {

                    binding.apply {
                        numberLay.boxStrokeColor = getColor(R.color.error_text)
                        numberLay.hintTextColor = getColorStateList(R.color.error_text)
                        numberLay.error = "Mobile number is required"
                        //set icon tint as red
                        numberLay.startIconDrawable?.setTint(getColor(R.color.error_text))
                    }
                }
                else{
                    binding.numberLay.apply {
                        boxStrokeColor = getColor(R.color.purple_700)
                        hintTextColor = getColorStateList(R.color.black)
                        startIconDrawable?.setTint(getColor(R.color.purple_700))
                    }
                }
            }
        })
    }

}