package com.softarena.checagocoffee.Acitivity.Signin

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.listener.OnPlacesDetailsListener
import `in`.madapps.placesautocomplete.model.Address
import `in`.madapps.placesautocomplete.model.Place
import `in`.madapps.placesautocomplete.model.PlaceDetails
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.softarena.checagocoffee.R

class SelectLocationActivity : AppCompatActivity() {
    val placesApi = PlaceAPI.Builder()
            .apiKey("AIzaSyAWD3r8_xNLfwvjlpSvXc_FPgBRncfHXg4")
            .build(this@SelectLocationActivity)
    lateinit var autoCompleteEditText : AutoCompleteTextView
    var street = ""
    var city = ""
    var state = ""
    var country = ""
    var zipCode = ""
    var lat1 = ""
    var lon1 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        autoCompleteEditText = findViewById<AutoCompleteTextView>(R.id.autoCompleteEditText)

        autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        autoCompleteEditText.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val place = parent.getItemAtPosition(position) as Place
                    autoCompleteEditText.setText(place.description)
                    getPlaceDetails(place.id)
                }
        findViewById<View>(R.id.img_back).setOnClickListener {
            if (!autoCompleteEditText.text.toString().equals("")){
                val data = Intent()
                data.putExtra("locationselected", autoCompleteEditText.text.toString())
                data.putExtra("lat", lat1)
                data.putExtra("lon", lon1)
                data.putExtra("city", city)
                data.putExtra("state", state)
                data.putExtra("zip_code", zipCode)
                setResult(210, data)
                finish()
            }

        }
    }
    private fun getPlaceDetails(placeId: String) {
        placesApi.fetchPlaceDetails(placeId, object :
                OnPlacesDetailsListener {
            override fun onError(errorMessage: String) {
//                Toast.makeText(this@SelectLocationActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceDetailsFetched(placeDetails: PlaceDetails) {
                setupUI(placeDetails)
            }
        })
    }

    private fun setupUI(placeDetails: PlaceDetails) {
        val address = placeDetails.address
        parseAddress(address)
        runOnUiThread {
            var streetTextView = findViewById<TextView>(R.id.streetTextView)
            streetTextView.text = street+" "+city+" "+state+" "+country+" "+zipCode+" "+placeDetails.lat.toString()+" "+placeDetails.lng.toString();
            lat1=placeDetails.lat.toString()
            lon1=placeDetails.lng.toString()
        }
    }

    private fun parseAddress(address: ArrayList<Address>) {
        (0 until address.size).forEach { i ->
            when {
                address[i].type.contains("street_number") -> street += address[i].shortName + " "
                address[i].type.contains("route") -> street += address[i].shortName
                address[i].type.contains("locadministrative_area_level_1ality") -> city += address[i].shortName
                address[i].type.contains("") -> state += address[i].shortName
                address[i].type.contains("country") -> country += address[i].shortName
                address[i].type.contains("postal_code") -> zipCode += address[i].shortName

            }
        }
    }

    override fun onBackPressed() {
        if (!autoCompleteEditText.text.toString().equals("")){
            val data = Intent()
            data.putExtra("locationselected", autoCompleteEditText.text.toString())
            data.putExtra("lat", lat1)
            data.putExtra("lon", lon1)
            data.putExtra("city", city)
            data.putExtra("state", state)
            data.putExtra("zip_code", zipCode)
            setResult(210, data)
            finish()
        }
    }
}

