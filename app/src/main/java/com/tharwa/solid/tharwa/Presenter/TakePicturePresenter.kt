package com.tharwa.solid.tharwa.Presenter
import android.util.Log
import com.tharwa.solid.tharwa.Contract.addPictureContract
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by thinkpad on 27/03/2018.
 */

class MissingPicture : Exception()

class TakePicturePresenter(val view:addPictureContract.View) {
    private var image: File? = null

    val getImage: File
        get() =
            if (image == null) {
                view.signalMissingImage()
                throw MissingPicture()

            } else
                 image!!




    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GALLERY = 2

    fun addPictureClicked() {
        view.clearMissingImageError()
        view.showPictureMethodModal()
    }

    fun fromGalleryClicked() {
        view.hidePictureMethodModal()
        view.dispatchTakePictureIntent()
    }

    fun fromCameraClicked() {
        view.hidePictureMethodModal()
        view.dispatchChoosePictureIntent()
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val dirStorage = view.getExternalFilesDir()
        image = File.createTempFile(imageFileName, ".jpg", dirStorage)

        return image as File
    }

    fun ImageReceived() {
        Log.d("Presenter", image?.path)
        view.setImage(image?.path as String)

    }

    fun ImageReceived(filePath: String?) {
        Log.d("Presenter", filePath)
        image = File(filePath)
        view.setImage(filePath as String)
    }

    fun isImageValid()
    {
        if (image == null) throw  MissingPicture()
    }
}
