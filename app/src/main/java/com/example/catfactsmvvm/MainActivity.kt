package com.example.catfactsmvvm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfactsmvvm.data.api.CatApi
import com.example.catfactsmvvm.di.AppModule
import com.example.catfactsmvvm.ui.theme.CatFactsMVVMTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var api: CatApi
    val fact = mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
               super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsMVVMTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            sendResponse()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {


                    sendResponse()


                    MyUi(fact = fact)

                }
            }
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun sendResponse() {
        GlobalScope.launch(Dispatchers.IO) {

            val response = try {
                AppModule.api.getCatResult()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                   fact.value = response.body()!!.fact

                }
            }
        }
    }
}

@Composable
fun MyUi(fact: MutableState<String>, modifier: Modifier = Modifier) {
    Column(modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Cat Fact:", modifier.padding(bottom = 25.dp), fontSize = 26.sp)
        Text(
            text = fact.value, fontSize = 26.sp,
            lineHeight = 40.sp,
            textAlign = TextAlign.Center
        )
    }
}

