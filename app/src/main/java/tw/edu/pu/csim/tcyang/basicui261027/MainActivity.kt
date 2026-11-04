package tw.edu.pu.csim.tcyang.basicui261027

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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
                Toast.makeText(
                    this,"作者：蔡岳彤",Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    // 建立影片狀態變數
    var currentVideoRes by remember { mutableStateOf(R.raw.love) }

    Column(modifier = modifier.fillMaxSize()) {
        // 1. 影片播放器
        SimpleVideoPlayer(videoRes = currentVideoRes)

        // 2. 空間與切換按鍵（塞在中間）
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                currentVideoRes = if (currentVideoRes == R.raw.love) {
                    R.raw.handpan
                } else {
                    R.raw.love
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = if (currentVideoRes == R.raw.love) "切換handpan影片" else "切換love影片")
        }
        Spacer(modifier = Modifier.height(12.dp))

        // 3. 下方的卡片與其他元件
        LazyRowExample()
        ColumnExample()
        TextFieldExample()
        LazyColumnExample()
    }
}

@Composable
fun LazyColumnExample(){
    val context = LocalContext.current

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
            .background(Color(0xFFE0BBE4))
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
fun RowExample(modifier: Modifier = Modifier) {
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

data class Animal(
    val name: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun LazyRowExample(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val animals = listOf(
        Animal("鴨子", R.drawable.animal0),
        Animal("企鵝", R.drawable.animal1),
        Animal("青蛙", R.drawable.animal2),
        Animal("貓頭鷹", R.drawable.animal3),
        Animal("海豚", R.drawable.animal4),
        Animal("牛", R.drawable.animal5),
        Animal("無尾熊", R.drawable.animal6),
        Animal("獅子", R.drawable.animal7),
        Animal("狐狸", R.drawable.animal8),
        Animal("小雞", R.drawable.animal9)
    )

    LazyRow(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 直接傳入 animals 清單，每個迴圈拿到一個 animal 物件
        itemsIndexed(animals) { index,animal ->
            Card(
                modifier = Modifier.fillParentMaxWidth(0.5f)
                    .height(260.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD0BCFF)
                )
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = animal.imageRes),
                        contentDescription = animal.name,
                        modifier = Modifier.size(150.dp)
                            .clickable {
                                // 點擊時跳出 Toast 訊息
                                Toast.makeText(
                                    context,
                                    "這是 ${animal.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$index${animal.name}"
                    )
                }
            }


        }}}

@Composable
fun SimpleVideoPlayer(@RawRes videoRes: Int) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(videoRes) {
        val uri = "android.resource://${context.packageName}/$videoRes"
        exoPlayer.setMediaItem(MediaItem.fromUri(android.net.Uri.parse(uri)))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = { ctx -> PlayerView(ctx).apply { player = exoPlayer } },
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}

@Composable
fun SimpleVideo1Player() {
        val context = LocalContext.current
        val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    val videoRes = null
    LaunchedEffect(videoRes) {
            val uri = "android.resource://${context.packageName}/$videoRes"
            exoPlayer.setMediaItem(MediaItem.fromUri(android.net.Uri.parse(uri)))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }

        DisposableEffect(Unit) {
            onDispose { exoPlayer.release() }
        }

        AndroidView(
            factory = { ctx -> PlayerView(ctx).apply { player = exoPlayer } },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }

@Composable
fun SimpleVideoPlayer() {
    val context = LocalContext.current

    // 建立並記住 ExoPlayer 實體，設定影片路徑與自動播放
    // 1. 使用 remember 儲存目前正在播放的影片資源 ID
    // 假設您的兩個影片分別是 R.raw.love 和 R.raw.another_video
    var currentVideoRes by remember { mutableStateOf(R.raw.love) }

    // 2. 建立並記住 ExoPlayer 實例
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build()
        }

        // 當 currentVideoRes 改變時，這個 LaunchedEffect 會自動重新觸發，讓播放器載入新影片
        LaunchedEffect(currentVideoRes) {
            val uri = "android.resource://${context.packageName}/$currentVideoRes"
            exoPlayer.setMediaItem(MediaItem.fromUri(android.net.Uri.parse(uri)))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true // 載入完成自動播放
        }

        // 當組件銷毀時，釋放播放器資源
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }

        // 4. 使用 Column 排版，將按鈕與影片播放器放在一起
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {// 內嵌的原生 PlayerView
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 5. 切換影片的按鈕
            Button(
                onClick = {
                    // 如果目前是 love，就換成另一個影片；反之亦然
                    currentVideoRes = if (currentVideoRes == R.raw.love) {
                        R.raw.handpan
                    } else {
                        R.raw.love
                    }
                }

            ) {
                // 動態顯示按鈕文字
                Text(
                    text = if (currentVideoRes == R.raw.love) "切換handpan影片" else "切換love影片"
                )
            }
        }
    }