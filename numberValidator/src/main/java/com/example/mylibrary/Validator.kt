package com.example.mylibrary

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

fun checkValidateNumber(
    countryCode:String,
    mobileNumber:String
):Boolean {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val numberProto = phoneUtil.parse(mobileNumber, countryCode)
            val isValid = phoneUtil.isValidNumber(numberProto)
            (isValid)
        } catch (e: Exception) {
            e is NumberParseException
        }
}

fun getFormattedNumber(
    countryCode:String,
    mobileNumber:String
):String {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val numberProto = phoneUtil.parse(mobileNumber, countryCode)
            val isValid = phoneUtil.isValidNumber(numberProto)
            if (isValid) {
                phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
            } else {
                "Mobile number is Invalid"
            }
        } catch (e: Exception) {
            e is NumberParseException
            "Mobile number is Invalid"
        }
}
