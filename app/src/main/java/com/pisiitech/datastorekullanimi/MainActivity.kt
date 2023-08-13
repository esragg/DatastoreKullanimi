package com.pisiitech.datastorekullanimi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pisiitech.datastorekullanimi.ui.theme.DatastoreKullanimiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoreKullanimiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Sayfa()
                }
            }
        }
    }
}

@Composable
fun Sayfa() {
    val context = LocalContext.current
    val ads = AppDatastore(context)
    val sayac = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = true) {
        val job:Job = CoroutineScope(Dispatchers.Main).launch {//Dispatcher gonderici, arayuzle ilgili islemler icin Main kullaniyoruz
            // Kayitlar
            ads.kayitAd("Ahmet")
            ads.kayitYas(23)
            ads.kayitBoy(1.78)
            ads.kayitBekarMi(true)

            val liste = HashSet<String>()
            liste.add("Mehmet")
            liste.add("Zeynep")

            ads.kayitArkadasListe(liste)

            // Okuma Islemleri
            val gelenAd = ads.okuAd()
            val gelenYas = ads.okuYas()
            val gelenBoy = ads.okuBoy()
            val gelenBekarMi = ads.okuBekarMi()
            val gelenListe = ads.okuArkadasListe()

            Log.e("Gelen Ad", gelenAd)
            Log.e("Gelen Yas", gelenYas.toString())
            Log.e("Gelen Boy", gelenBoy.toString())
            Log.e("Gelen Bekar Mi", gelenBekarMi.toString())

            for (a in gelenListe!!) {
                Log.e("Gelen Arkadas", a)
            }

            //Sayac uygulamasi
            var gelenSayac = ads.okuSayac()
            sayac.value = ++gelenSayac
            ads.kayitSayac(gelenSayac)
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Acilis Sayisi : ${sayac.value}", fontSize = 36.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DatastoreKullanimiTheme {
        Sayfa()
    }
}