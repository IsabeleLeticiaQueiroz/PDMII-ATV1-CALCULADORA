package com.example.calculadora
class Calculadora(
    private var num01: Double,
    private var num02: Double
) {

    fun somar(): Double {
        return num01 + num02
    }

    fun subtrair(): Double {
        return num01 - num02
    }

    fun multiplicar(): Double {
        return num01 * num02
    }

    fun dividir(): Double {
        if (num02 == 0.0) {
            throw ArithmeticException("Divisão por zero")
        }
        return num01 / num02
    }
}