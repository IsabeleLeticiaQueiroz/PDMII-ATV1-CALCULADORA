package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculadoraUI(modifier: Modifier = Modifier) {

    var display by remember { mutableStateOf("") }
    var num1 by remember { mutableStateOf("") }
    var operacao by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        //resultado visor
        Text(
            text = if (display.isEmpty()) "0" else display,
            fontSize = 56.sp,
            color = Color(0xFFE9BCC0),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            maxLines = 1
        )

        Column {

            //opcoes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OperadorBotao("+", Modifier.weight(1f)) {
                    num1 = display
                    display = ""
                    operacao = "+"
                }
                OperadorBotao("-", Modifier.weight(1f)) {
                    num1 = display
                    display = ""
                    operacao = "-"
                }
                OperadorBotao("*", Modifier.weight(1f)) {
                    num1 = display
                    display = ""
                    operacao = "*"
                }
                OperadorBotao("/", Modifier.weight(1f)) {
                    num1 = display
                    display = ""
                    operacao = "/"
                }
                OperadorBotao("C", Modifier.weight(1f)) {
                    display = ""
                    num1 = ""
                    operacao = ""
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //nums
            NumerosRow(listOf("1", "2", "3")) { display += it }
            Spacer(modifier = Modifier.height(8.dp))

            NumerosRow(listOf("4", "5", "6")) { display += it }
            Spacer(modifier = Modifier.height(8.dp))

            NumerosRow(listOf("7", "8", "9")) { display += it }
            Spacer(modifier = Modifier.height(8.dp))

            //zero
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                NumeroBotao("0", Modifier.weight(1f)) { display += "0" }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))
            //resultado
            Button(
                onClick = {
                    try {
                        if (num1.isNotEmpty() && display.isNotEmpty()) {

                            val calc = Calculadora(
                                num1.toDouble(),
                                display.toDouble()
                            )

                            val resultado = when (operacao) {
                                "+" -> calc.somar()
                                "-" -> calc.subtrair()
                                "*" -> calc.multiplicar()
                                "/" -> calc.dividir()
                                else -> 0.0
                            }

                            display = resultado.toString()
                        }
                    } catch (e: Exception) {
                        display = "Erro"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE9BCC0)
                )
            ) {
                Text("=", fontSize = 26.sp)
            }
        }
    }
}

@Composable
fun NumeroBotao(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFDE5E1)
        )
    ) {
        Text(
            texto,
            color = Color.Gray,
            fontSize = 22.sp
        )
    }
}

@Composable
fun OperadorBotao(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFCBC6)
        )
    ) {
        Text(
            texto,
            color = Color.DarkGray,
            fontSize = 22.sp
        )
    }
}

@Composable
fun NumerosRow(
    numeros: List<String>,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        numeros.forEach {
            NumeroBotao(
                texto = it,
                modifier = Modifier.weight(1f)
            ) {
                onClick(it)
            }
        }
    }
}