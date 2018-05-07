package com.tharwa.solid.tharwa.View.Virment
import android.content.Intent
import android.content.Intent.getIntent
import android.nfc.Tag
import android.os.Bundle
import android.view.View
import com.tharwa.solid.tharwa.R
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import com.tharwa.solid.tharwa.Contract.VirToMeContract
import com.tharwa.solid.tharwa.Presenter.Virement.VirToMePresenter
import com.tharwa.solid.tharwa.View.ClientAcountActivity
import com.tharwa.solid.tharwa.util.BaseActivity
import kotlinx.android.synthetic.main.vrintern_to_me_fragment.*


/**
 * Created by LE on 23/04/2018.
 */

class VirToMeFragment :DialogFragment (),VirToMeContract.View,BaseActivity<VirToMePresenter>{


    override val presenter: VirToMePresenter by lazy { VirToMePresenter(this) }

    var type_acc_sender:Int=0
    var type_acc_receiver:Int=0
    var montant_virement=0




    val TAG = "FullScreenDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, parent: ViewGroup?, state: Bundle?): View? {
        super.onCreateView(inflater, parent, state)
        val view = activity.layoutInflater.inflate(R.layout.vrintern_to_me_fragment, parent, false)
        view.findViewById<View>(R.id.button_close)?.setOnClickListener(View.OnClickListener { dismiss() })
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillSpiner(view,R.id.spinner_compte_from,"",1)
        confirmer_virme.setOnClickListener({on_confirmClick()})


    }

    fun  on_confirmClick()
    {
       montant_virement=  montant_virme.text.toString().toInt()
        presenter.on_confirmClick(type_acc_sender,type_acc_receiver,montant_virement)
    }



    fun fillSpiner(view : View?, id_:Int, item:String,typeSpinner:Int) {
        val spinner = view!!.findViewById<View>(id_) as Spinner

        val lfrom = presenter.CountVirMe(item)
        val dataAdapter= ArrayAdapter(context, android.R.layout.simple_spinner_item, lfrom)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view1: View, position: Int, id: Long) {
                if (typeSpinner==1)
              {
                  type_acc_sender=setViremntData((spinner.selectedItem.toString()))
                  showMoneyText(spinner.selectedItem.toString(),view)
                  fillSpiner(view,R.id.spinner_compte_to,spinner.selectedItem.toString(),2)
              }
                else
                    type_acc_receiver =setViremntData((spinner.selectedItem.toString()))
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                if (typeSpinner==1)
                    type_acc_sender=setViremntData((spinner.selectedItem.toString()))
                else
                    type_acc_receiver =setViremntData((spinner.selectedItem.toString()))
            }
        }
    }

    fun showMoneyText(item:String,view:View)
    {
        val money_txt=view!!.findViewById<View>(R.id.money_txt) as TextView
        when(item)
        {
            "COURANT","EPARGNE"-> money_txt.text="DZD"
            "USD"->money_txt.text="USD"
            "EURO"->money_txt.text="EURO"
        }
    }
    fun setViremntData(item:String):Int
    {
        when(item)
        {
            "COURANT"-> return 1
            "EPARGNE"->  return 2
            "EURO"-> return 3
            "USD"-> return 4
            else-> return 1
        }
    }

   override fun onResume() {

        super.onResume()
        this.onCreate(null)
    }
    override fun relodeActivity()
    {

    }
    override fun closeDialog()
    {
        dismiss()
    }
    override fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    override fun showTag(tag:String, message: String) {
        Log.e(tag,message)
    }

    interface InterfaceDataVirMe
    {
        fun dataVirMe(item:String):ArrayList<String>
        fun CountVirMe(item:String):ArrayList<String>
    }

}

