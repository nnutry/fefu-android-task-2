package com.nutry.fefu_android_task_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutry.fefu_android_task_2.ui.theme.Fefuandroidtask2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fefuandroidtask2Theme {
                Scaffold() { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TestScreen()
                    }
                }
            }
        }
    }
}

// мои штуки

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

val questions = listOf(
    Question("Какой язык используется для разработки Android приложений?", listOf("Kotlin", "1С"), 0),
    Question("Автор альбома \"Коммунизма дух\"?", listOf("Вячеслав Мясников", "Дмитрий Брекоткин"), 1),
    Question("Автор курсовой \"Курсовая Ильи Морозова\"?", listOf("Илья Морозов", "Квенчик"), 1),
)

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }

    val question = questions[currentIndex]
    // колонка всего
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Вопрос: ${currentIndex + 1} из ${questions.size}",
            color = Color(100, 100, 100),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(text = question.text)

        // Ряд кнопок
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            question.options.forEachIndexed { i, option ->

                val color = when {
                    selectedIndex == null -> Color.Gray
                    i == question.correctIndex -> Color(0xFF6D982E)
                    i == selectedIndex -> Color(0xFFE45545)
                    else -> Color.Gray
                }

                AnswerButton(
                    text = option,
                    color = color,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (selectedIndex == null) {
                            selectedIndex = i
                            if (i == question.correctIndex) {
                                score++
                            }
                        }
                    }
                )
            }
        }

        // блок верного или неверного ответа
        if (selectedIndex != null) {
            val isCorrect = selectedIndex == question.correctIndex
            Text(if (isCorrect) "Правильно!!" else "Неправильно..")
            Text("Ваш счёт: $score")
        }

        // Бокс для занятия места до низа
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Button(
                shape = RoundedCornerShape(8.dp),
                enabled = selectedIndex != null && currentIndex < questions.lastIndex,
                onClick = {
                    currentIndex++
                    selectedIndex = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text("Следующий вопрос")
            }
        }
    }

}

@Composable
fun AnswerButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Fefuandroidtask2Theme {
        TestScreen()
    }
}