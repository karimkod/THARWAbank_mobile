package com.tharwa.solid.tharwa.Presenter
import android.content.Context
import com.tharwa.solid.tharwa.Base.BasePresenter
import com.tharwa.solid.tharwa.Model.Avatar
import com.tharwa.solid.tharwa.Model.UserCreate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import com.tharwa.solid.tharwa.View.SignUpActivity
import java.io.File

/**
 * Created by LE on 12/03/2018.
 */
class SignUp:BasePresenter
{
    var disposable: Disposable? = null

    private val Service =Config.newService()
    private var user_id:Text?=null
    private val _method:String="PUT"
    private var mView: SignUpActivity? = null

    constructor(view: SignUpActivity) {
        mView = view
    }

    fun createCustomer(usercr: UserCreate, photo: File)
    {
        disposable = Service.createCustomer(usercr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                                usercr ->
                                val message=Error.codeMessage(usercr.code()) +"  " + usercr.body()?.message
                                if (usercr.isSuccessful)
                                {
                                    mView?.showMessage(mView as Context,message )

                                    //Get the user ID
                                    user_id=usercr.body()?.user_id as Text
                                    //Create the Avatar if the user set it
                                    var userAvatar:Avatar?=null
                                    if (photo!=null)
                                    {
                                       userAvatar=Avatar(user_id as Text,photo,_method)
                                        createAvatar(userAvatar)
                                    }
                                }
                                else //error 400-500
                                {
                                    mView?.showMessage(mView as Context,message )
                                }
                        },
                        {
                            error -> //other error
                            mView?.showError(mView as Context,error.message.toString())
                        }
                )
    }
    fun createAvatar(userAvatar: Avatar)
    {
        disposable = Service.sendAvatar(userAvatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            userAvatar ->
                            val message=Error.codeMessage(userAvatar.code())+" " + userAvatar.body()?.message
                            if (userAvatar.isSuccessful)
                            {
                                TODO()
                                TODO("open the right activity ")
                                mView?.showMessage(mView as Context,message )
                            }
                            else
                            {
                                TODO()
                                TODO("tzwa9 ")
                                mView?.showMessage(mView as Context,message)
                            }
                        },
                        {
                            error ->
                            mView?.showError(mView as Context,error.message.toString())
                        }
                )
    }
}