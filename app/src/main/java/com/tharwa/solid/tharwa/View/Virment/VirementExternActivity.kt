package com.tharwa.solid.tharwa.View.Virment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tharwa.solid.tharwa.Contract.VirementExterneContract
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.Presenter.Virement.VirementExternePresenter
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.View.LoadingFragment
import com.tharwa.solid.tharwa.View.TakePictureFragment
import com.tharwa.solid.tharwa.enumration.InputType
import kotlinx.android.synthetic.main.activity_virement_extern.*
import org.w3c.dom.Text

class VirementExternActivity : AppCompatActivity(),
        TakePictureFragment.OnFragmentInteractionListener,
        VirementExterneContract.View, FormInterface, AdapterView.OnItemSelectedListener {


    override val nameReceiver: String
        get() = nom_destinataire.editText?.text.toString()
    override val montant: String
        get() = montant_virement.editText?.text.toString()
    override val numAccountReceiver: String
        get() = account_number.editText?.text.toString()
    override val codeCurrencyReceiver: String
        get() = currency_spinner.selectedItem.toString()
    override val bankReceiver: String
        get() = bank_spinner.selectedItem.toString()


    val loadingFragment by lazy { LoadingFragment() }

    override val presenter: VirementExternePresenter by lazy {
        VirementExternePresenter(this,
                Injection.provideUserRepository())
    }


    override fun setTakePicturePresenter(picturePresenter: TakePicturePresenter) {
        presenter.takePicturePresenter = picturePresenter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virement_extern)

        setSupportActionBar(virement_extern_actionbar)
        supportActionBar?.apply {
            title = getString(R.string.virement_externe)
            setDisplayHomeAsUpEnabled(true)
        }

        setUpCurrencySpinner()
        presenter.start()

        montant_virement.editText?.addTextChangedListener(object: TextWatcher
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

    override fun loadBankSpinner(banks: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, banks)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bank_spinner.adapter = adapter

    }

    fun setUpCurrencySpinner() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.currency_array, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currency_spinner.apply {
            this.adapter = adapter
            onItemSelectedListener = this@VirementExternActivity
        }
    }


    override fun showDialogMessage(title: String, message: String) {
        showDialogMessage(this, title, message)
    }


    override fun showConfirmationMethod() {
        val alertBuilder = AlertDialog.Builder(this)
        val layout = layoutInflater.inflate(R.layout.confirm_extern_dialog, null)
        alertBuilder.setNeutralButton("Annuler", null)
        alertBuilder.setTitle("Confirmez-vous l'envoi vers ce compte ?")

        layout.apply {
            findViewById<TextView>(R.id.numero_compte).text = """$bankReceiver$numAccountReceiver$codeCurrencyReceiver"""
            findViewById<TextView>(R.id.montant).text = """${String.format("%,.2f", montant.toFloat())} $codeCurrencyReceiver"""
        }

        alertBuilder.setPositiveButton("Confirmer") { _, _ ->  presenter.makeVirement()}
        alertBuilder.setView(layout)
        alertBuilder.create().show()
    }

    override fun isValidInputs(): Boolean {
        clearErrors(account_number, montant_virement, nom_destinataire)
        try {
            verifyField(nameReceiver, nom_destinataire, InputType.NAME, true, this)
            verifyField(numAccountReceiver, account_number, InputType.ACCOUNTNUMBEREXTERNE, true, this)
            verifyField(montant, montant_virement, InputType.MONTANT, true, this)
            return true
        } catch (e: InvalideInputException) {
            return false
        }
    }


    override fun showPicturePlace() {
        motif.visibility = View.VISIBLE
    }

    override fun hidePicturePlace() {
        motif.visibility = View.GONE
    }

    override fun showProgressDialog() {
        loadingFragment.show(supportFragmentManager.beginTransaction(), "loadingFrag")
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }

    override fun hideProgressDialog() {
        supportFragmentManager.beginTransaction().remove(loadingFragment).commit()
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    override fun showSuccessDialog(title: String, message: String) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setNeutralButton("Terminé") { _, _ -> finish() }
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setOnDismissListener { finish() }
        alertBuilder.create().show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        return
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        selected_currency.text = currency_spinner.selectedItem.toString()

    }

}
