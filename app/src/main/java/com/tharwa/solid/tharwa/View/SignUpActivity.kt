package com.tharwa.solid.tharwa.View

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.tharwa.solid.tharwa.Base.BaseActivity
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Presenter.SignUp
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.InputType
import kotlinx.android.synthetic.main.sign_up_activity.*


class SignUpActivity : BaseActivity<SignUp>(), TakePictureFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener,
        FormInterface {


    val mail get() = email.editText?.text.toString()
    val password get() =  motdepasse.editText?.text.toString()
    val phone_number get() = phone.editText?.text.toString()
    val lastName get() = nom.editText?.text.toString()
    val firstName get() = prenom.editText?.text.toString()
    val adress get() = adresse.editText?.text.toString()
    val function get() = fonction.editText?.text.toString()
    val wilaya get() = wilayaSpinner.selectedItem.toString()
    val commune get() = communeSpinner.selectedItem.toString()
    val type get() = if (simple.isChecked) 0 else 1



    override var takePictureFragment:TakePictureFragment? = null

    val loadingFragment by lazy {LoadingFragment()}

    val communeIdArray by lazy { resources.obtainTypedArray(R.array.wilaya_commune) }

    @NonNull
    override fun createPresenter(@NonNull context: Context): SignUp {
        return SignUp(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        val toolbar = signupToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Inscription"
        setUpWilayaSpinner()
        setUpCommuneSpinner(0)
        (mPresenter as SignUp).picturePresenter = takePictureFragment?.presenter
        sign_up.setOnClickListener({( mPresenter as SignUp).onConnectClicked()})


    }

    fun setUpWilayaSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.Wilaya, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wilayaSpinner.adapter = adapter
        wilayaSpinner.onItemSelectedListener = this
    }

    fun setUpCommuneSpinner(id: Int) {
        val adapter = ArrayAdapter.createFromResource(this, communeIdArray.getResourceId(id, -1), android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        communeSpinner.adapter = adapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == wilayaSpinner)
            setUpCommuneSpinner(position)
    }

    fun isValidInputs(): Boolean
    {
        clearErrors(arrayOf(nom,prenom,email,motdepasse,phone,fonction,wilayaTextInput,adresse))
        try {
            verifyField(lastName, nom, InputType.NAME, true, this)
            verifyField(firstName, prenom, InputType.NAME, true, this)
            verifyField(mail, email, InputType.EMAIL, true, this)
            verifyField(password, motdepasse, InputType.OTHER, true, this)
            verifyField(phone_number, phone, InputType.TEL, true, this)
            verifyField(function, fonction, InputType.OTHER, true, this)
            verifyField(wilaya, wilayaTextInput, InputType.WILAYA, true, this)
            verifyField(adress, adresse, InputType.OTHER, true, this)
            return true
        } catch (e: InvalideInputException) {
            return false
        }


    }

    fun showProgressDialog()
    {
        loadingFragment.show(supportFragmentManager.beginTransaction(),"loadingFrag")
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }

    fun hideProgressDialog()
    {
        supportFragmentManager.beginTransaction().remove(loadingFragment).commit()
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    fun showSuccessDialog()
    {
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Inscription réussie")
        builder.setMessage("Votre compte est en phase de validation, cela prendra " +
                "moins de 24hrs")
        builder.setNeutralButton("Terminé",DialogInterface.OnClickListener {
            _,_ ->
            mPresenter?.onSuccessDialogEnded()
        })
        builder.setOnCancelListener{ mPresenter?.onSuccessDialogEnded() }

        val dialog = builder.create()
        dialog.show()
    }





}
