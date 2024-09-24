package com.example.charlesli_quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.charlesli_quizapp.ui.theme.CharlesLiQuizAppTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharlesLiQuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Quiz(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Quiz( modifier: Modifier = Modifier) {
    var i by remember { mutableIntStateOf(0) }
    var questions by remember { mutableStateOf(listOf<String>()) }
    var answers by remember { mutableStateOf(listOf<String>()) }

    var quizComplete by remember { mutableStateOf(false)}

    questions = listOf("1 + 2", "3 + 4", "5 + 6", "2 + 3", "7 + 9", "8 + 3")
    answers = listOf("3", "7", "11", "5", "16", "11")

    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    var ans by remember { mutableStateOf("") }

    if (quizComplete == false) {

        Column(modifier = Modifier.fillMaxSize().padding(20.dp).padding(top = 100.dp))
        {
            QuizCard(cardContent = questions[i])

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Type the answer to the card here!", textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            BasicTextField(
                value = ans,
                onValueChange = {
                    ans = it
                    showSnackbar = false
                },
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                onClick = {
                    if (answers[i].equals(ans)) {
                        snackbarMessage = "Your answer was correct!"
                    } else {
                        snackbarMessage = "Your answer was incorrect :("
                    }
                    ans = ""
                    i += 1
                    if (i == 6) {
                        i = 0
                        quizComplete = true
                        snackbarMessage = "You've finished the quiz!"
                    }
                    showSnackbar = true

                }) {
                Text("Answer!")
            }
        }
    } else {
        Column (modifier = Modifier.fillMaxSize().padding(20.dp).padding(top = 100.dp)){
            Text("Would you like to restart the quiz?")

            Button(onClick = {
                quizComplete = false
                i = 0
            }) {
                Text("Restart the quiz!")
            }
        }
    }
    if (showSnackbar) {
        Snackbar(
            action = {
                Button(onClick = { showSnackbar = false }) {
                    Text("OK")
                }
            }
        ) {
            Text(snackbarMessage)
        }
    }
}

@Composable
fun QuizCard( cardContent: String,  modifier: Modifier = Modifier ) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Text(
                text = cardContent,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CharlesLiQuizAppTheme {
        Quiz()
    }
}