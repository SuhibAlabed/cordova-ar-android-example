package com.outsystems.plugins.arexample

import android.Manifest
import android.content.Intent
import com.google.ar.core.examples.java.helloar.ArTradeActivity
import com.google.ar.core.examples.java.helloar.CameraPermissionHelper
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PermissionHelper
import org.json.JSONArray
import android.util.Base64

class ARPlugin: CordovaImplementation(){

    override var callbackContext: CallbackContext? = null

    var folderName = ""
    val ACCESS_CAMERA = 0

    override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
        super.initialize(cordova, webView)
    }

    override fun areGooglePlayServicesAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        this.callbackContext = callbackContext

        when (action) {
            "openARView" -> {
                doOpenAR(args)
            }
        }
        return true
    }

    private fun doOpenAR(args: JSONArray) {

        folderName = args.getString(0)
        val base64model = args.getString(1)
        val base64texture= args.getString(2)

        val model = Base64.decode(base64model, Base64.DEFAULT)

        val texture = Base64.decode(base64texture, Base64.DEFAULT)


        if(folderName.isNullOrEmpty()){
            return
            //later we can return an error here
        }

        PermissionHelper.requestPermission(this, ACCESS_CAMERA, Manifest.permission.CAMERA);
    }

    override fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>?,
        grantResults: IntArray?
    ) {
        super.onRequestPermissionResult(requestCode, permissions, grantResults)


        if(requestCode == ACCESS_CAMERA){

            if (!CameraPermissionHelper.hasCameraPermission(cordova.activity)) {
                //if permission wasn't granted, end
                return
            }
            else{
                val intent = Intent(cordova.activity, ArTradeActivity::class.java)
                intent.putExtra("obj_path", model)
                intent.putExtra("texture_path", texture)

                cordova.activity.startActivityForResult(intent, 1)
            }

        }
    }
}


