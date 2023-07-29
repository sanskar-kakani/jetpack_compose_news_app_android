package com.example.news_app_jc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.news_app_jc.model.Article
import com.example.news_app_jc.model.Cheap
import com.example.news_app_jc.mvvm.MainViewModel
import com.example.news_app_jc.ui.theme.News_app_JCTheme
import com.example.news_app_jc.ui.theme.Purple80
import com.example.news_app_jc.ui.theme.deepBlue
import com.example.news_app_jc.ui.theme.lightBlue
import com.example.news_app_jc.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            News_app_JCTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   Main()
               }
            }
        }
    }
}

@Composable
fun Main() {

    val selectedIndex = remember{ mutableStateOf(0) }

    Column(Modifier.padding(15.dp)) {
        CheapSection(list = getCheapList(), selectedIndex.value){it
            selectedIndex.value = it
        }
        NewsSection(getCheapList()[selectedIndex.value].headline)
    }
}

@Composable
fun CheapSection(list: List<Cheap>, index:Int, changeIndex:(it:Int)->Unit) {

    LazyRow{
        items(list.size){
            Row (
               verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = if (index == it) lightBlue else deepBlue)
                    .clickable {
                        changeIndex(it)
                    }
            ) {

                Icon(
                    painter = painterResource(id = list[it].icon),
                    contentDescription = list[it].headline,
                    tint = if (index == it) Purple80 else Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(
                    text = list[it].headline,
                    style = TextStyle(
                        color = if (index == it) Purple80 else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                )
            }
        }
    }

}

@Composable
fun NewsSection(category: String){


    Spacer(modifier = Modifier.height(15.dp))

    val mainViewModel:MainViewModel = viewModel()
    mainViewModel.getNews(category)
    val newsListState : State<List<Article>> = mainViewModel.newsList.collectAsState()
    val list = newsListState.value


    LazyColumn(){
        items(list.size){
            NewsItem(
                title = list[it].title,
                source = list[it].source.name,
                urlToImg = list[it].urlToImage,
                url = list[it].url,
                publishedAt = list[it].publishedAt
            )
        }
    }
}

@Composable
fun NewsItem(title:String, source:String, urlToImg:String?, url:String, publishedAt:String) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .padding(10.dp)
            .clickable { uriHandler.openUri(url) }
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                urlToImg ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Image_not_available.png/640px-Image_not_available.png"
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 15.dp),
            textAlign = TextAlign.Start
        )

       Row(
           horizontalArrangement = Arrangement.SpaceBetween,
           modifier = Modifier
               .fillMaxWidth()
               .padding(bottom = 10.dp)
       ) {
           Text(
               text = source,
               fontSize = 15.sp,
               fontWeight = FontWeight.Thin,
           )

           Text(
               text = formatDate(publishedAt),
               fontSize = 15.sp,
               fontWeight = FontWeight.Thin,
           )
       }

        Divider()

    }

}

fun getCheapList():List<Cheap>{

    return listOf(
        Cheap(R.drawable.baseline_newspaper_24 ,"General"),
        Cheap(R.drawable.baseline_business_24, "Business"),
        Cheap(R.drawable.baseline_tv_24, "Entertainment"),
        Cheap(R.drawable.baseline_health_and_safety_24, "Health"),
        Cheap(R.drawable.baseline_science_24, "Science"),
        Cheap(R.drawable.baseline_sports_cricket_24, "Sports"),
        Cheap(R.drawable.baseline_code_24, "Technology"),
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    News_app_JCTheme {
        Main()
    }
}

