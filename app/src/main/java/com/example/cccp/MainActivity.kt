package com.example.cccp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.cccp.databinding.ActivityMainBinding
import com.google.i18n.phonenumbers.PhoneNumberUtil


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

            if (number.isNullOrEmpty()){

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
                checkNo(code, number)
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
                checkNo(binding.ccp.selectedCountryNameCode, s.toString())

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


    //function to check if the number is valid or not
    private fun checkNo(code: String,number:String) {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneUtil.parse(number, code)

            val isValid = phoneUtil.isValidNumber(numberProto)

            val formattedNumber =
                phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)


            if (isValid) {
                binding.validStatus.text = "Mobile Number is Valid"
                binding.formattedNo.text = "Formatted No : $formattedNumber"
            } else {
                binding.validStatus.text = "Mobile number is Invalid"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}