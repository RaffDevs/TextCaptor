package com.example.textcaptor.app.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.textcaptor.app.presentation.viewmodel.MainViewModel
import com.example.textcaptor.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var bitmap: Bitmap
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupListeners()

    }

    private val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                startImageCropper(Uri.fromFile(File(currentPhotoPath)))
            }
        }

    private val cropImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageResult = CropImage.getActivityResult(result.data)

                val resultUri = imageResult.uri

                try {
                    val source = ImageDecoder.createSource(this.contentResolver, resultUri)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    getTextFromImage(bitmap)
                } catch (error: Error) {
                    Log.d("Error", "onActivityResult: $error")
                }
            }
        }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
                null
            }
            photoFile?.also {
                val photoURI: Uri =
                    FileProvider.getUriForFile(this, "com.example.textcaptor.fileprovider", it)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                openCamera.launch(takePictureIntent)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(null)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent()
        }
    }

    private fun getTextFromImage(bitmap: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.d("RECONHECIMENTO", "SUCESSO: ${visionText.text}")

                Toast.makeText(this, visionText.text, Toast.LENGTH_LONG)
            }
            .addOnFailureListener { e ->
                Log.d("RECONHECIMENTO", "getTextFromImage: $e")
            }

    }

    private fun startImageCropper(imageURI: Uri) {
        val intent = CropImage.activity(imageURI)
            .setGuidelines(CropImageView.Guidelines.ON)
            .getIntent(this)
        cropImage.launch(intent)

    }

    private fun setupListeners() {
        binding.btnScan.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                dispatchTakePictureIntent()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }
    }


}