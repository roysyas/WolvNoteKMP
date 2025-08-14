package com.roys.wolvnotekmp.common

object SalaryCalculation {
    fun calculate(amount: String): String{
        val salary: Double = amount.toDouble()
        val expenditure = (salary*47.5)/100
        val alms = (salary*2.5)/100
        val instalment = (salary*30)/100
        val savings = (salary*20)/100

        return buildString {
            appendLine("Expenditure: ${formatWithCommas(expenditure)}")
            appendLine("Alms: ${formatWithCommas(alms)}")
            appendLine("Instalment: ${formatWithCommas(instalment)}")
            appendLine("Savings: ${formatWithCommas(savings)}")
        }
    }

    private fun formatWithCommas(number: Double): String {
        val longValue = number.toLong()
        val str = longValue.toString()
        val sb = StringBuilder()
        var count = 0
        for (i in str.length - 1 downTo 0) {
            sb.append(str[i])
            count++
            if (count % 3 == 0 && i != 0) sb.append(',')
        }
        return sb.reverse().toString()
    }
}