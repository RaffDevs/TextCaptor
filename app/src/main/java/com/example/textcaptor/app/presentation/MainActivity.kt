package com.example.textcaptor.app.presentation

import android.content.Intent
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
import java.net.URI


@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var bitmap: Bitmap
    private val REQUEST_IMAGE_CAPTURE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupListeners()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



    }

    private val openCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        Log.d("GOMERY", "${result.resultCode}")


        if (result.resultCode == RESULT_OK) {

            val imageBitmap = result?.data?.getParcelableExtra<Bitmap>("data")


            // Iniciar a atividade de recorte de imagem
            if (imageBitmap != null) {
                val imageUri = saveImageToCache(imageBitmap)

                startImageCropper(imageUri)
            }

        } else if (result.resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            // Obter o resultado do recorte de imagem

            val imageResult = CropImage.getActivityResult(result.data)


            if (result.resultCode == RESULT_OK) {
                val resultURI = imageResult.uri
                try {
                    val source = ImageDecoder.createSource(this.contentResolver, resultURI)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    getTextFromImage(bitmap)

                } catch (error: Error) {
                    Log.d("Error", "onActivityResult: $error")
                }
            }
            // ...
        } else if (result.resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Log.d("GOMERY", "ERROR: ${result.resultCode}")
        }
    }

    private fun saveImageToCache(bitmap: Bitmap): Uri {
        val cacheDir = applicationContext.cacheDir
        val imageFile = File(cacheDir, "captured_image.jpg")
        FileOutputStream(imageFile).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        return FileProvider.getUriForFile(this, "com.example.fileprovider", imageFile)
    }

    private fun dispatchTakePictureIntent() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        Log.d("CLICADO", "dispatchTakePictureIntent: ${takePictureIntent.resolveActivity(packageManager)}")

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            openCamera.launch(takePictureIntent)
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
        CropImage.activity(imageURI)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)// Defina a proporção desejada para o recorte

    }

    private fun setupListeners() {
        binding.btnScan.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }





}