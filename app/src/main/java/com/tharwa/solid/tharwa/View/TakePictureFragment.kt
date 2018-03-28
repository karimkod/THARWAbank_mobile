package com.tharwa.solid.tharwa.View

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tharwa.solid.tharwa.Contract.addPictureContract
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.take_picture_fragment.*
import kotlinx.android.synthetic.main.take_picture_fragment.view.*
import java.io.File
import java.io.IOException




class TakePictureFragment : Fragment(), addPictureContract.View {


    private var mListener: OnFragmentInteractionListener? = null

    val presenter: TakePicturePresenter by lazy { TakePicturePresenter(this) }

    val choosePictureModal by lazy {ChoosePictureMethod()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.take_picture_fragment, container, false)

        view.addpicture.setOnClickListener { presenter.addPictureClicked() }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            context.takePictureFragment = this
            mListener = context

        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun dispatchTakePictureIntent()
    {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            val imageFile = try {
                presenter.createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val photoURI = FileProvider.getUriForFile(activity, activity.packageName + ".fileprovider", imageFile as File)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent,presenter.REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun dispatchChoosePictureIntent()
    {
        val photoPickerIntent= Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent,presenter.REQUEST_IMAGE_GALLERY)

    }

    override fun getExternalFilesDir():File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if(requestCode == presenter.REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK )
        {
            presenter.ImageReceived()

        }else if (requestCode == presenter.REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK)
        {
            presenter.ImageReceived(getRealPathFromURI(data!!.data))
        }
    }


    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    override fun setImage(path:String)
    {
        val targetH = addpicture.height
        val targetW = addpicture.width
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true

        val matrix = Matrix()

        matrix.postRotate(0f)
        val bitmap = BitmapFactory.decodeFile(path, bmOptions)
        val rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0,
                bitmap.width, bitmap.height, matrix, true)
        addpicture.setImageBitmap(rotatedBitmap)

    }

    override fun showPictureMethodModal()
    {
        choosePictureModal.show(fragmentManager.beginTransaction(), "Modal" )
        choosePictureModal.presenter = presenter

    }

    override fun hidePictureMethodModal() {

        fragmentManager.beginTransaction().remove(choosePictureModal).commit()
        choosePictureModal.presenter = null
    }


    override fun clearMissingImageError()
    {
        imageLayout.error = ""
    }

    override fun signalMissingImage()
    {
        imageLayout.error = "Veuillez séléctionner une photo de vous"
    }

    interface OnFragmentInteractionListener {
        var takePictureFragment: TakePictureFragment?

    }

}
