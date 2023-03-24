package app.src.guessnumber


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.src.guessnumber.ui.theme.GuessNumberTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessNumberTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GuessingNumberGameScreen()
                }
            }
        }
    }
}


@Composable
fun GuessingNumberGameScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var value = 0
        val randNum = remember { mutableStateOf(Random.nextInt(1, 1000)) }
        var counter = remember { mutableStateOf(0) }
        var text = remember { mutableStateOf("") }
        var hintMessage = remember { mutableStateOf("") }

        TextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            label = {
                Text(text = "Enter number")
            },
            placeholder = {
                Text(text = "1-1000")
            }
        )

        if (hintMessage.value.contains("Correct")) {
            Button(onClick = {
                randNum.value = Random.nextInt(1, 1000)
                text.value = ""
                hintMessage.value = ""
                counter.value = 0
            }) {
                Text("Restart")
            }

        }else{
            Button(
                onClick = {
                    val num = text.value.toIntOrNull()
                    if (num != null) {
                        counter.value++
                        if (num == randNum.value) {
                            hintMessage.value = "Correct! You guessed ${counter.value} times."
                        } else if (num < randNum.value) {
                            hintMessage.value = "Higher"
                        } else {
                            hintMessage.value = "Lower"
                        }
                        text.value = ""
                    }
                },
            )
            {
                Text("Guess")
            }
        }

        Text(hintMessage.value)

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GuessNumberTheme {
        GuessingNumberGameScreen()
    }
}