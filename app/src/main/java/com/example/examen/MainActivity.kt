@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.examen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.examen.ui.theme.ExamenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTheme {
                Column {
                    StudentCard(
                        studentName = "Alumno: " + nombre,
                        mensaje = "Soy un alumno",
                        foto_perfil = avatar,
                        borderColor = alumno8,
                        backgroundColor = background8,
                        shadowColor = shadow8
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        RandomColorButton()
                    }
                    Conversation(Mensajes.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

// mi numero de lista es el 8

val avatar = R.drawable.avatar8
val nombre = "Alejandro Gonzalez Dominguez"
val alumno8= Color(0xFFAA00FF)
val background8=Color(0xFFDED0E6)
val shadow8=Color(0xFFAA00FF)
val profesor= R.drawable.profesor

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) Color.Magenta else Color.Gray
        )
        val imageSize by animateDpAsState(targetValue = if (isExpanded) 150.dp else 50.dp)
        Image(
            painter = painterResource(profesor),
            contentDescription = null,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { isExpanded = !isExpanded }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(conversation: List<Message>) {
    Column {
        conversation.forEach { msg ->
            MessageCard(msg = msg)
        }
    }
}

@Composable
fun RandomColorButton() {
    val listaColores = listOf(Color.Green, Color.Blue, Color.Red, Color.Cyan)
    var buttonColor by remember { mutableStateOf(Color.Magenta) }

    Button(
        onClick = {
            buttonColor = listaColores.random()
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text("Elegir un nuevo color")
    }
}

@Composable
fun StudentCard(
    studentName: String,
    mensaje: String,
    foto_perfil: Int,
    borderColor: Color = Color.Gray,
    backgroundColor: Color = Color.Gray,
    shadowColor: Color = Color.Gray
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(8.dp), clip = true),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(foto_perfil),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = studentName, style = MaterialTheme.typography.headlineLarge)
            Text(text = mensaje, style = MaterialTheme.typography.bodyLarge)
        }
    }
}



object Mensajes {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Profesor",
            "Pues si utilizan todo lo que hemos dado en clase con sabiduría no deberían tener problemas"
        ),
        Message(
            "Profesor",
            "Con este ejercicio practican:\n" +
                    "Lo que vimos del instructivo, de hecho pueden partir de ahí\n" +
                    "De la aplicación de loterías cómo hacer observables, con variables que se leen en tiempo de ejecución\n" +
                    "También del de loterías cómo seleccionar un elemento aleatorio de una lista\n" +
                    "Los botones lo hemos visto en varios proyectos\n" +
                    "Así como el lazyrow y cómo modificar el layout\n" +
                    "También hemos visto cómo crear componentes que pueden cambiar dependiendo de los parámetros\n" +
                    "Espero que les sirva de ayuda)\n"
        ),
        Message(
            "Profesor",
            "Si no han practicado lo tienen fastidiado pero ...es lo que tiene.\n" +
                    "Hay que echarle horas para poder hacerlo rápido en los examenes!"
        ),
        Message(
            "Profesor",
            "También vimos cómo cambiar colores definidos en el MatherialTheme."

        ),
        Message(
            "Profesor",
            "Se me ocurrieron montón de cosas para mejorar esto como que pudieras poner distintos avatares automáticamente si eres profesor o alumno, pero ya me flipé un poco. "
        ),
        Message(
            "Profesor",
            "Pues mucha suerte!\n" +
                    "En este examen y en el futuro" +
                    "No queda nada para empezar las prácticas." +
                    "Ánimooooooooooooo:)"
        )
    )
}



