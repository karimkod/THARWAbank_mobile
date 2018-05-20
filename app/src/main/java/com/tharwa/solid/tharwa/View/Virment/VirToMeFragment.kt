package com.tharwa.solid.tharwa.View.Virment
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.tharwa.solid.tharwa.R
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.tharwa.solid.tharwa.Contract.VirToMeContract
import com.tharwa.solid.tharwa.Presenter.Virement.VirToMePresenter
import com.tharwa.solid.tharwa.View.LoadingFragment
import com.tharwa.solid.tharwa.util.BaseActivity
import kotlinx.android.synthetic.main.vrintern_to_me_fragment.*


/**
 * Created by LE on 23/04/2018.
 */

class VirToMeFragment :DialogFragment (),VirToMeContract.View,BaseActivity<VirToMePresenter>{


    override val presenter: VirToMePresenter by lazy { VirToMePresenter(this) }

    var type_acc_sender:Int=0
    var type_acc_receiver:Int=0
    var montant_virement:Double=0.0

    val loadingFragment by lazy { LoadingFragment() }


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
       montant_virement=  montant_virme.text.toString().toDouble()
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
        val money_txt=view.findViewById<View>(R.id.money_txt) as TextView
        when(item)
        {
            resources.getString(R.string.current),resources.getString(R.string.saving)-> money_txt.text=resources.getString(R.string.dzd)
            resources.getString(R.string.usd)->money_txt.text= resources.getString(R.string.usd)
            resources.getString(R.string.euro)->money_txt.text=resources.getString(R.string.euro)
        }
    }
    fun setViremntData(item:String):Int
    {
        when(item)
        {
            resources.getString(R.string.current)-> return 1
            resources.getString(R.string.saving)->  return 2
            resources.getString(R.string.euro)-> return 3
            resources.getString(R.string.usd)-> return 4
            else-> return 1
        }
    }

   override fun onResume() {

        super.onResume()
        this.onCreate(null)
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
    override fun showResultDialog(code:Int,message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(resources.getString(R.string.transactoin))
        when(code )
        {
            200,201-> builder.setMessage(resources.getString(R.string.virme_201)
                    +message+ " " +resources.getString(R.string.virme_201_suite))
            400->builder.setMessage(resources.getString(R.string.virme_400))
            404->builder.setMessage(resources.getString(R.string.virme_400))
            500->builder.setMessage(resources.getString(R.string.err_500))
        }
        builder.setNeutralButton(resources.getString(R.string.terminer), DialogInterface.OnClickListener {
            _,_ ->
            presenter.onResultDialogEnded()
            dismiss()
        })
        builder.setOnCancelListener{presenter.onResultDialogEnded()
             }
       val  dialogResult = builder.create()
        dialogResult.show()
        presenter.onResultDialogEnded()
    }

    interface InterfaceDataVirMe
    {
        fun CountVirMe(item:String):ArrayList<String>
    }
    override fun showProgressDialog() {
       loadingFragment.show((context as AppCompatActivity).supportFragmentManager.beginTransaction(), "loadingFrag")
        (context as AppCompatActivity).window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun hideProgressDialog() {
        (context as AppCompatActivity).supportFragmentManager.beginTransaction().remove(loadingFragment).commit()
        (context as AppCompatActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}

