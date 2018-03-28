package com.tharwa.solid.tharwa.View

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tharwa.solid.tharwa.Contract.addPictureContract
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.image_picker_fragment.*
import kotlinx.android.synthetic.main.image_picker_fragment.view.*

/**
 * Created by thinkpad on 25/03/2018.
 */
class ChoosePictureMethod : BottomSheetDialogFragment()
{

    var presenter:TakePicturePresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater!!.inflate(R.layout.image_picker_fragment,container,false)
        view.camera.setOnClickListener { presenter?.fromCameraClicked() }
        view.gallery.setOnClickListener { presenter?.fromGalleryClicked() }

        return  view
    }

}