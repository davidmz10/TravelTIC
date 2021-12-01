package com.example.jsonapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
class MainActivity : AppCompatActivity() {

    private var siteImage:ArrayList<String> = ArrayList()
    private var siteNames: ArrayList<String> = ArrayList()
    private var siteDescription: ArrayList<String> = ArrayList()
    private var sitePoints: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "TravelTic-Bucaramanga"
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        try {

            val obj = JSONObject(loadJSONFromAsset())
            val siteArray = obj.getJSONArray("sites")
            for (i in 0 until siteArray.length()) {
                val siteDetail = siteArray.getJSONObject(i)

                siteImage.add(siteDetail.getString("image"))
                siteNames.add(siteDetail.getString("name"))
                siteDescription.add(siteDetail.getString("description"))
                sitePoints.add(siteDetail.getString("points"))

            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
        val customAdapter = CustomAdapter(this@MainActivity, siteImage,siteNames,siteDescription,sitePoints)
        recyclerView.adapter = customAdapter
    }
    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("sites_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}