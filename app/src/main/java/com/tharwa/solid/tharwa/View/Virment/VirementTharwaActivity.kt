package com.tharwa.solid.tharwa.View.Virment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.tharwa.solid.tharwa.Contract.VirementTharwaContract
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.Presenter.Virement.VirementTharwaPresenter
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.View.LoadingFragment
import com.tharwa.solid.tharwa.View.TakePictureFragment
import com.tharwa.solid.tharwa.enumration.InputType
import kotlinx.android.synthetic.main.activity_virement_tharwa.*
import org.w3c.dom.Text


class VirementTharwaActivity : AppCompatActivity(),FormInterface,VirementTharwaContract.View, TakePictureFragment.OnFragmentInteractionListener
{


    override fun setTakePicturePresenter(picturePresenter: TakePicturePresenter)
    {
        presenter.takePicturePresenter = picturePresenter
    }

    override val destinationAccount: String
        get() = numero_compte.editText?.text.toString()
    override val montant: String
        get() = montant_virement.editText?.text.toString()

    val loadingFragment by lazy { LoadingFragment() }

    override val presenter: VirementTharwaPresenter by lazy { VirementTharwaPresenter(this, Injection.provideUserRepository()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virement_tharwa)
        setSupportActionBar(virement_tharwa_actionbar)

        supportActionBar?.apply {
            title = getString(R.string.virement_vers_tharwa)
            setDisplayHomeAsUpEnabled(true)
        }


        montant_virement.editText?.addTextChangedListener(object:TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
                return
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                presenter.montantVerification(s.toString())
            }

        })

        virer.setOnClickListener { presenter.onFinishedClicked() }

    }

    override fun showDialogMessage(title: String, message: String)
    {
        showDialogMessage(this,title,message)
    }


    override fun showConfirmationMethod(name:String, wilaya:String, commune:String)
    {
        val alertBuilder = AlertDialog.Builder(this)
        val layout = layoutInflater.inflate(R.layout.confirm_distination_dialog,null)
        alertBuilder.setNeutralButton("Annuler",null)
        alertBuilder.setTitle("Confirmez-vous le propriétaire du compte ?")

        layout.apply {
            findViewById<TextView>(R.id.nom_prenom).text = name
            findViewById<TextView>(R.id.wilaya).text = wilaya
            findViewById<TextView>(R.id.commune).text = commune
            findViewById<TextView>(R.id.montant).text = String.format("%,.2f", montant.toFloat()) + " DZD"

        }

        alertBuilder.setPositiveButton("Confirmer") { _, _ -> presenter.makeVirement()}
        alertBuilder.setView(layout)
        alertBuilder.create().show()
    }

    override fun isValidInputs(): Boolean
    {
        clearErrors(numero_compte,montant_virement)
        try {
            verifyField(destinationAccount, numero_compte, InputType.ACCOUNTNUMBER, true, this)
            verifyField(montant, montant_virement, InputType.MONTANT, true, this)
            return true
        } catch (e: InvalideInputException)
        {
            return false
        }
    }



    override fun showPicturePlace()
    {
        motif.visibility = View.VISIBLE
    }

    override fun hidePicturePlace() {
        motif.visibility = View.GONE
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

    override fun showSuccessDialog(title: String, message: String)
    {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setNeutralButton("Terminé",{_,_ -> finish()})
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setOnDismissListener({finish()})
        alertBuilder.create().show()
    }



}