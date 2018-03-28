package com.tharwa.solid.tharwa.Contract

import java.io.File

/**
 * Created by thinkpad on 26/03/2018.
 */
interface addPictureContract
{
    interface View
    {
        fun showPictureMethodModal()
        fun hidePictureMethodModal()
        fun getExternalFilesDir(): File
        fun setImage(path:String)
        fun dispatchTakePictureIntent()
        fun dispatchChoosePictureIntent()
        fun signalMissingImage()
        fun clearMissingImageError()
    }


}