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
            setDialogTextColor(getColor(R.color.black))
            setExcludedCountries(
                "AF,AX,AL,DJ,DZ,XK,BL,MF,AS,AD,AO,AI,AQ,AG,AR,AM,AW,AU,AT,AZ,BS,BH,BD,BB,BY,BE,BZ,BJ" +
                        ",BM,BT,BO,BA,BW,BV,IO,BN,BG,BF,BI,KH,CM,CA,CV,KY,CF,TD,CL,CN,CX,CC,CO,KM,CG," +
                        "CD,CK,CR,CI,HR,CU,CW,CY,CZ,DK,DM,DO,EC,EG,SV,GQ,ER,EE,ET,FK,FO,FJ,FI,FR,GF," +
                        "PF,TF,GA,GM,GE,DE,GH,GI,GR,GL,GD,GP,GU,GT,GG,GN,GW,GY,HT,HM,VA,HN,HK,HU,IS," +
                        "ID,IR,IQ,IE,IM,IL,IT,JM,JE,JO,KZ,KI,KP,KR,KW,KG,LA,LV,LB,LS,LR,LY,LI,LT,LU," +
                        "MO,MK,MG,MW,MY,MV,ML,MT,MH,MQ,MR,MU,YT,MX,FM,MD,MC,MN,ME,MS,MA,MZ,MM,NA,NR," +
                        "NP,NL,NC,NZ,NI,NE,NG,NU,NF,MP,NO,OM,PK,PW,PS,PA,PG,PY,PE,PH,PN,PL,PT,PR,QA," +
                        "RE,RO,RU,RW,SH,KN,LC,PM,VC,WS,SM,ST,SA,SN,RS,SC,SL,SG,SX,SK,SI,SB,SO,ZA,GS," +
                        "SS,ES,LK,SD,SR,SJ,SZ,SE,CH,SY,TW,TJ,TH,TL,TG,TK,TO,TT,TN,TR,TM,TC,TV,UG,UA," +
                        "AE,GB,US,UM,UY,UZ,VU,VE,VN,VG,VI,WF,EH,YE,ZM,ZW"
            )

        }


        binding.validate.setOnClickListener {
            var code : String = binding.ccp.selectedCountryNameCode
            val number: String = binding.number.text.toString()

            if (number.isEmpty()){
                binding.error.text = "Mobile number is required"
                binding.error.visibility = android.view.View.VISIBLE
            }
            else {
                //listener to get the country code when the country is changed
                binding.ccp.setOnCountryChangeListener {
                    val countryCode = binding.ccp.selectedCountryNameCode
                    code = countryCode
                    binding.formattedNo.text = ""
                    binding.validStatus.text = ""
                    binding.number.setText("")
                }
                val tv =  checkValidateNumber(code, number)

                binding.formattedNo.text = "Is valid : $tv"

                val formattedNumber = getFormattedNumber(code, number)
                binding.validStatus.text = "Formatted number is: $formattedNumber"
                if (!tv){
                    binding.error.text = "Invalid number"
                    binding.error.visibility = android.view.View.VISIBLE
                }
                else{
                    binding.error.visibility = android.view.View.INVISIBLE
                }

            }
        }

        //text watcher
        binding.number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.error.visibility = android.view.View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getFormattedNumber(binding.ccp.selectedCountryNameCode, s.toString())

                binding.error.visibility = android.view.View.INVISIBLE
                binding.formattedNo.text= ""
                binding.validStatus.text = ""

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {
                    binding.error.text = "Mobile number is required"
                } else {
                    binding.error.visibility = android.view.View.INVISIBLE
                }
            }
        })
    }

}