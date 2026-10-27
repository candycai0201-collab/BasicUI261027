package tw.edu.pu.csim.tcyang.basicui261027

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import tw.edu.pu.csim.tcyang.basicui261027.ui.theme.BasicUI261027Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicUI261027Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                // 需要三張圖時直接呼叫並傳入清單
                ThreeRowExample(
                    imageResIds = listOf(
                        R.drawable.animal1,
                        R.drawable.animal2,
                        R.drawable.animal0
                    )
                )
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    Box {
        LazyColumnExample()
        ColumnExample(modifier)
    }
}

@Composable
fun LazyColumnExample(){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{Text("標題列")}
        items(50){ index -> Text("$index")
        }
        item{Text("結尾列")}
    }
}

@Composable
fun ColumnExample(modifier: Modifier = Modifier) {
    var msg by remember { mutableStateOf(value = "") }
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x8AE0BBE4))
            .pointerInput(Unit){
                    detectTapGestures(
                        onTap = {
                            msg = "短按"
                        },
                        onDoubleTap = {
                            msg = "連按兩下"
                        },
                        onLongPress = {
                            msg = "長按"
                        }

                    )
        },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("行動應用軟體開發：基本元件")
        Text("作者：蔡岳彤")
        Text(msg)

        Spacer(modifier = Modifier.height(8.dp))
        RowExample()
        TextFieldExample()


    }
}

@Composable
fun RowExample() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Android icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Yellow),
            alpha = 0.6f,
        )

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Compose icon",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun TextFieldExample(modifier: Modifier = Modifier) {
    // 管理輸入框的文字狀態
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("請輸入文字") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (inputText.isBlank()) "尚未輸入內容" else "您輸入的是：$inputText"
        )
    }
}

@Composable
fun ThreeRowExample(imageResIds: List<Int>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // 將內部的 Row 放在正中央
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 第一個圖片
            Image(
                painter = painterResource(id = R.drawable.animal1),
                contentDescription = "animal1 icon",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                alpha = 0.6f
            )

            Spacer(modifier = Modifier.width(20.dp))

            // 第二個圖片
            Image(
                painter = painterResource(id = R.drawable.animal2),
                contentDescription = "animal2 icon",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // 第三個圖片
            Image(
                painter = painterResource(id = R.drawable.animal0),
                contentDescription = "animal0 icon",
                modifier = Modifier.size(100.dp)
            )
    }

    }
}