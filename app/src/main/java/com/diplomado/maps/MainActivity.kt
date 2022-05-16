package com.diplomado.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createMapFragment()

        findViewById<ImageView>(R.id.imgRestaurante1).setOnClickListener(this)
        findViewById<ImageView>(R.id.imgRestaurante2).setOnClickListener(this)
        findViewById<ImageView>(R.id.imgRestaurante3).setOnClickListener(this)
        findViewById<ImageView>(R.id.imgRestaurante4).setOnClickListener(this)
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
        }
        enableMyLocation()
    }
    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isPermissionsGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.imgRestaurante1 -> {
                val favoritePlace = LatLng(19.4398883,-99.2028772)
                map.addMarker(MarkerOptions().position(favoritePlace).title("Hooters"))
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
                    2000,
                    null
                )
            }
            R.id.imgRestaurante2 -> {
                val favoritePlace = LatLng(19.4338788,-99.1419985)
                map.addMarker(MarkerOptions().position(favoritePlace).title("Corona"))
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
                    2000,
                    null
                )
            }
            R.id.imgRestaurante3 -> {
                val favoritePlace = LatLng(19.4243385,-99.1556891)
                map.addMarker(MarkerOptions().position(favoritePlace).title("Santa"))
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
                    2000,
                    null
                )
            }
            R.id.imgRestaurante4 -> {
                val favoritePlace = LatLng(19.4260526,-99.1670843)
                map.addMarker(MarkerOptions().position(favoritePlace).title("Vips"))
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
                    2000,
                    null
                )
            }
        }
    }

}