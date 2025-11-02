package com.example.a30joursgteaux

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.xmlpull.v1.XmlPullParser
import com.example.a30joursgteaux.ui.theme._30JoursGteauxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cakes = parseCakesXml(this)

        setContent {
            _30JoursGteauxTheme {
                CakeListScreen(cakes = cakes)
            }
        }
    }

    // Data class
    data class Cake(
        val day: Int,
        val name: String,
        val description: String,
        val imageUrl: String
    )


    fun parseCakesXml(context: Context): List<Cake> {
        val cakes = mutableListOf<Cake>()
        val parser: XmlPullParser = context.resources.getXml(R.xml.cake)

        var eventType = parser.eventType
        var currentDay = 0
        var currentName = ""
        var currentDesc = ""
        var currentImage = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (tagName) {
                        "day" -> currentDay = parser.nextText().toInt()
                        "name" -> currentName = parser.nextText()
                        "description" -> currentDesc = parser.nextText()
                        "image" -> currentImage = parser.nextText()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (tagName == "cake") {
                        cakes.add(Cake(currentDay, currentName, currentDesc, currentImage))
                        currentDay = 0
                        currentName = ""
                        currentDesc = ""
                        currentImage = ""
                    }
                }
            }
            eventType = parser.next()
        }
        return cakes
    }

    @Composable
    fun CakeCard(cake: Cake) {
        var expanded by remember { mutableStateOf(false) }

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Jour ${cake.day} : ${cake.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(cake.imageUrl),
                    contentDescription = cake.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = cake.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }


    @Composable
    fun CakeListScreen(cakes: List<Cake>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .background(Color(0xFFFFC0CB))
        ) {
            items(cakes) { cake ->
                CakeCard(cake = cake)
            }
        }
    }
}
