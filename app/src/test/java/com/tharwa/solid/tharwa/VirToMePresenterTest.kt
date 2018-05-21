package com.tharwa.solid.tharwa

import com.tharwa.solid.tharwa.Contract.VirToMeContract
import com.tharwa.solid.tharwa.Model.DestinationAccoutInfo
import com.tharwa.solid.tharwa.Model.ResponseVirme
import com.tharwa.solid.tharwa.Model.VirToMe
import com.tharwa.solid.tharwa.Presenter.Virement.VirToMePresenter
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.AccountRepository
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.Repositories.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response


/**
 * Created by LE on 07/05/2018.
 */
class VirToMePresenterTest
{


    @Mock private lateinit var view: VirToMeContract.View
    @Mock private lateinit var userRepo: UserRepository
    @Mock private lateinit var accountRepository: AccountRepository

    private lateinit var presenter:VirToMePresenter


    @Before fun init()
    {
        MockitoAnnotations.initMocks(this)

        presenter = VirToMePresenter(view)

    }


    @Test fun transferTest()
    {
        val virement = VirToMe(0,1,1.0,1)
        val token = ""

        val apiService = UserApiService.create()
        UserApiService.instance =  spy(apiService)

        presenter.transfer(token,virement)

        verify(view, times(1)).showProgressDialog()
        verify(UserApiService.instance!!,times(1)).virToMe(token,virement)
    }


    @Test fun onCreateVirementSuccessTestSuccess()
    {
        val responseMock = mock(Response::class.java) as Response<ResponseVirme>
        `when`(responseMock.isSuccessful).thenReturn(true)
        `when`(responseMock.code()).thenReturn(200)
        val body = ResponseVirme("Effectue","2500")
        `when`(responseMock.body()).thenReturn(body)
        presenter.onCreateVirementSuccess(responseMock)

        verify(view, times(1)).hideProgressDialog()
        verify(view, times(1)).showResultDialog(responseMock.code(),body.balance)
    }

    @Test fun onCreateVirementSuccessTestFail()
    {
        val responseMock = mock(Response::class.java) as Response<ResponseVirme>
        `when`(responseMock.isSuccessful).thenReturn(false)
        `when`(responseMock.code()).thenReturn(400)
        `when`(responseMock.message()).thenReturn("Erreur")
        presenter.onCreateVirementSuccess(responseMock)

        verify(view, times(1)).hideProgressDialog()
        verify(view, times(1)).showResultDialog(responseMock.code(),responseMock.message())
    }

    @Test fun onCreateVirementFailTest()
    {
        val erroMock = mock(Throwable::class.java)

        presenter.onCreateVirementFail(erroMock)
        verify(view, times(1)).hideProgressDialog()
    }

    @Test fun onResultDialogEndedTest()
    {
        presenter.onResultDialogEnded()
        verify(view,times(1)).closeDialog()
    }

    @Test fun listDispTest()
    {
        val repositoryMock = mock(AccountRepository::class.java)
        var accountTypes = arrayOf(1,2,3,4)
        AccountRepository.INSTANCE = repositoryMock
        `when`(repositoryMock.availableAccountsType)
                .thenReturn(accountTypes)

        var listDisp = presenter.listDisp()

        assertEquals(listDisp.size,4)
        assertTrue(listDisp.contains("COURANT"))
        assertTrue(listDisp.contains("EPARGNE"))
        assertTrue(listDisp.contains("EURO"))
        assertTrue(listDisp.contains("USD"))

        accountTypes = arrayOf(1)
        `when`(repositoryMock.availableAccountsType)
                .thenReturn(accountTypes)

        listDisp = presenter.listDisp()

        assertEquals(listDisp.size,1)
        assertTrue(listDisp.contains("COURANT"))

        accountTypes = arrayOf(2)
        `when`(repositoryMock.availableAccountsType)
                .thenReturn(accountTypes)

        listDisp = presenter.listDisp()

        assertEquals(listDisp.size,1)
        assertTrue(listDisp.contains("EPARGNE"))

        accountTypes = arrayOf(3)
        `when`(repositoryMock.availableAccountsType)
                .thenReturn(accountTypes)


        listDisp = presenter.listDisp()

        assertEquals(listDisp.size,1)
        assertTrue(listDisp.contains("EURO"))

        accountTypes = arrayOf(4)
        `when`(repositoryMock.availableAccountsType)
                .thenReturn(accountTypes)

        listDisp = presenter.listDisp()

        assertEquals(listDisp.size,1)
        assertTrue(listDisp.contains("USD"))
    }






}