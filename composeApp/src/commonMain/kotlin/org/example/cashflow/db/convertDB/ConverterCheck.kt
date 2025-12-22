package org.example.cashflow.db.convertDB


class ConverterCheck(val qrResult: String){
    fun getCost(): Float{
        val qrList = qrResult.split("&")
        val cost = qrList[1].slice(2 until qrList[1].length).toFloat()
        return cost
    }
}