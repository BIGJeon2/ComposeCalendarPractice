package com.jeon.composecalendarpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeon.composecalendarpractice.ui.theme.ComposeCalendarPracticeTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalendarPracticeTheme {
                CalenderPractice()
            }
        }
    }
}

@Composable
private fun CalenderPractice(){
    val calenderInstance = Calendar.getInstance()
    val time = remember {
        mutableStateOf(calenderInstance)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalenderHeader(time)
        CalendarHeaderBtn(time)
        CalendarDayName(time)
        CalendarDayList(time)
    }

}

@Composable
private fun CalenderHeader(date: MutableState<Calendar>){
    // xxxx년 xx월
    val resultTime = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(date.value.time)

    Text(
        text = resultTime,
        fontSize = 30.sp
    )

}

@Composable
private fun CalendarHeaderBtn(date: MutableState<Calendar>){
    // xxxx년 xx월
    val resultTime = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(date.value.time)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, -1)
                date.value = newDate
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = "<",
                color = Color.Green
            )
        }

        Button(
            onClick = {
                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, +1)
                date.value = newDate
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = ">",
                color = Color.Green
            )
        }
    }
}

@Composable
private fun CalendarDayName(date: MutableState<Calendar>){
    val nameList = listOf("일","월","화","수","목","금","토")
    Row(
        modifier = Modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        nameList.forEach {
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun CalendarDayList(date: MutableState<Calendar>){
    //달력 그리는 공식 -> JetPack Compose로 달력 모양 그리는 방법
    date.value.set(Calendar.DAY_OF_MONTH, 1)

    val monthDayMax = date.value.getActualMaximum(Calendar.DAY_OF_MONTH)
    val monthFirstDay = date.value.get(Calendar.DAY_OF_WEEK) - 1
    val monthWeeksCount = (monthDayMax + monthFirstDay + 6) / 7

    Log.d("monthDayMax", monthDayMax.toString())
    Log.d("monthFirstDay", monthFirstDay.toString())
    Log.d("monthWeeksCount", monthWeeksCount.toString())

    Column(

    ) {
        repeat(monthWeeksCount) {week ->
            Row(

            ) {
                repeat(7) { day ->
                    //날짜 구하는 공식
                    val resultDay = week * 7 + day - monthFirstDay + 1

                    if (resultDay in 1 .. monthDayMax) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = resultDay.toString(),
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                        }
                    }else{
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCalendarPracticeTheme {
        CalenderPractice()
    }
}