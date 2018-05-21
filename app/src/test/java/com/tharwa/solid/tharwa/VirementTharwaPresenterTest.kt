package com.tharwa.solid.tharwa

import com.tharwa.solid.tharwa.Contract.VirementTharwaContract
import com.tharwa.solid.tharwa.Model.AccesInfo
import com.tharwa.solid.tharwa.Model.DestinationAccoutInfo
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.Presenter.Virement.VirementTharwaPresenter
import com.tharwa.solid.tharwa.Repositories.UserRepository
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class VirementTharwaPresenterTest
{
    @Mock private lateinit var view: VirementTharwaContract.View

    @Mock private lateinit var userRepository: UserRepository


    private lateinit var virementTharwaPresenter: VirementTharwaPresenter

    @Captor private lateinit var virementWIthMotifCallBack: ArgumentCaptor<Callback<ResponseBody>>

    @Before fun setVirementTharwaPresenter()
    {
        MockitoAnnotations.initMocks(this)

        virementTharwaPresenter = VirementTharwaPresenter(view, userRepository)
        `when`(userRepository.accessInfos).thenReturn(AccesInfo("mock","mock",500,1))

    }


    @Test fun initialState()
    {
        assertTrue(!virementTharwaPresenter.needMotif)
        assertEquals(virementTharwaPresenter.MAXIMUM_SANS_MOTIF, 200000.0f)
    }

    @Test fun picturePlaceHidden()
    {
        var montant = "210000"

        virementTharwaPresenter.montantVerification(montant)

        verify(view,times(1)).showPicturePlace()

        assertTrue(virementTharwaPresenter.needMotif)

        montant = "2500"

        virementTharwaPresenter.montantVerification(montant)

        verify(view,times(1)).hidePicturePlace()

        assertFalse(virementTharwaPresenter.needMotif)

    }

    @Test fun onFinishedClicked_withoutMotifValidInputTest()
    {
        virementTharwaPresenter.needMotif = false

        `when`(view.isValidInputs()).thenReturn(true)
        `when`(view.destinationAccount).thenReturn("0")
        `when`(userRepository.accessInfos).thenReturn(AccesInfo("mock","mock",500,1))

        virementTharwaPresenter.onFinishedClicked()

        verify(view, times(1)).showProgressDialog()
        //verify(virementTharwaPresenter,times(1)).getDestinationInfos()

    }

    @Test fun onRequestFailedTest()
    {
        virementTharwaPresenter.onRequestFailed(mock<Throwable>(Throwable::class.java))
        verify(view, times(1)).hideProgressDialog()
        verify(view, times(1)).showDialogMessage("Oops..","Erreur, veuillez réessayer plus tard")

    }

    @Test fun onDestinationInfosResultTest()
    {
        val responseMock =mock(Response::class.java) as Response<DestinationAccoutInfo>

        `when`(responseMock.isSuccessful).thenReturn(true)
        val destinationAccount = DestinationAccoutInfo("personne","alger","alger")
         `when`(responseMock.body()).thenReturn(destinationAccount )

        virementTharwaPresenter.onDestinationInfosResult(responseMock)
        val inOrder = inOrder(view)
        inOrder.verify(view,times(1)).hideProgressDialog()
        inOrder.verify(view,times(1)).showConfirmationMethod(destinationAccount.name,destinationAccount.wilaya,destinationAccount.commune)

    }

    @Test fun onVirementSuccessTest()
    {
        val responseMock =mock(Response::class.java) as Response<ResponseBody>
        `when`(responseMock.isSuccessful).thenReturn(true)

        virementTharwaPresenter.onVirementSuccess(responseMock)
        verify(view,times(1)).hideProgressDialog()
        verify(view,times(1)).showSuccessDialog("Virement effectué","Le virement a été effectué avec success")

    }


    @Test fun sendVirementWithMotif()
    {
        val takePicturePresenter = mock(TakePicturePresenter::class.java)
        `when`(takePicturePresenter.getImage).thenReturn(File("path"))
        `when`(view.destinationAccount).thenReturn("")
        `when`(view.montant).thenReturn("2500")
        //<virementTharwaPresenter.makeVirementWithMotif()

    }

}