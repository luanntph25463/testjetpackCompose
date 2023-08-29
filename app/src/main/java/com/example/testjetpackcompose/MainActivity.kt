package com.example.testjetpackcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testjetpackcompose.ui.theme.TestjetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestjetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: com.example.testjetpackcompose.Message) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(80.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                // tạo viền bo ngoài (viền , màu , kiểu)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        // gọi biến isExpanded kiểm  tra xem có bị thay đổi hay không
        // remember hàm khởi tạo gía trị ban đầu là false
        // mutableStateOf hàm gọi lại mỗi khi thay đổi giá trị
        // ví dụ khởi tạo là false khi nhấn nút gọi lại và gắn isExpanded  true
        var isExpanded by remember { mutableStateOf(false) }
        // thêm khoảng trống ở phần tử phía dưới
        // gắn click khi nhấn nút sẽ đổi thành giá trị ngược lại
//        val surfaceColor sẽ cập nhật từ màu naỳ sang màu khác
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        Column(Modifier.fillMaxWidth().clickable { isExpanded = !isExpanded }) {
            Text(text = msg.author,
                color = Color.DarkGray,
            style = MaterialTheme.typography.titleLarge )
            Spacer(modifier = Modifier.height(7.dp))
            Surface(shape = MaterialTheme.shapes.medium,
                // gắn bề mặt ban đầu là màu surfaceColor
                color = surfaceColor,
                // animateContentSize sẽ Thay đổi màu từ
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // nếu là là đúng thì gắn Int max value  con không gắn bằng 1
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // surfaceColor color will be changing gradually from primary to surface

                    // animateContentSize will change the Surface size gradually
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}
@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
@Composable
fun PreviewMessageCard() {
    TestjetpackComposeTheme {
        Surface {
            Conversation(SampleData.conversationSample)
        }
    }
}
// lazycolum là phiên bản  của recycle