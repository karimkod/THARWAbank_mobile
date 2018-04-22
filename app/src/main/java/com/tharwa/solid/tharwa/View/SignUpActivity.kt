package com.tharwa.solid.tharwa.View

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.tharwa.solid.tharwa.Contract.SignUpContrat

import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Presenter.SignUpPresenter
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.InputType
import kotlinx.android.synthetic.main.sign_up_activity.*


class SignUpActivity : AppCompatActivity(), TakePictureFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener,
        FormInterface,SignUpContrat.View{

    override val presenter: SignUpPresenter by lazy { SignUpPresenter(this) }
    override val mail get() = email.editText?.text.toString()
    override val password get() =  motdepasse.editText?.text.toString()
    override val phone_number get() = phone.editText?.text.toString()
    override val lastName get() = nom.editText?.text.toString()
    override val firstName get() = prenom.editText?.text.toString()
    override val adress get() = adresse.editText?.text.toString()
    override val function get() = fonction.editText?.text.toString()
    override val wilaya get() = wilayaSpinner.selectedItem.toString()
    override val commune get() = communeSpinner.selectedItem.toString()
    override val type get() = if (simple.isChecked) 0 else 1



    override var takePictureFragment:TakePictureFragment? = null

    val loadingFragment by lazy {LoadingFragment()}

    val communeIdArray by lazy { resources.obtainTypedArray(R.array.wilaya_commune) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        val toolbar = signupToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Inscription"
        setUpWilayaSpinner()
        setUpCommuneSpinner(0)
        presenter.picturePresenter = takePictureFragment?.presenter
        sign_up.setOnClickListener({( presenter ).onConnectClicked()})


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

    override fun isValidInputs(): Boolean
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

    override fun showProgressDialog()
    {
        loadingFragment.show(supportFragmentManager.beginTransaction(),"loadingFrag")
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }

    override fun hideProgressDialog()
    {
        supportFragmentManager.beginTransaction().remove(loadingFragment).commit()
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    override fun showSuccessDialog()
    {
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Inscription réussie")
        builder.setMessage("Votre compte est en phase de validation, cela prendra " +
                "moins de 24hrs")
        builder.setNeutralButton("Terminé",DialogInterface.OnClickListener {
            _,_ ->
            presenter.onSuccessDialogEnded()
        })
        builder.setOnCancelListener{presenter.onSuccessDialogEnded() }

        val dialog = builder.create()
        dialog.show()
    }

    override  fun showMessage(message:String)
    {

        this.showMessage(message)
    }



}
