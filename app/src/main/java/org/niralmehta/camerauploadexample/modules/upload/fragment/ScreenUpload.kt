package org.niralmehta.camerauploadexample.modules.upload.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.screen_upload.*
import org.niralmehta.camerauploadexample.BuildConfig
import org.niralmehta.camerauploadexample.R
import org.niralmehta.camerauploadexample.datakt.Data
import org.niralmehta.camerauploadexample.di.Injectable
import org.niralmehta.camerauploadexample.modules.upload.datakt.ImagePayload
import org.niralmehta.camerauploadexample.modules.upload.viewmodel.UploadViewModel
import org.niralmehta.camerauploadexample.screens.ScreenCamera
import org.niralmehta.camerauploadexample.utils.api.Status
import org.niralmehta.camerauploadexample.utils.fragment.addScreen
import org.niralmehta.camerauploadexample.utils.interfaces.OnBackPressedListener
import org.niralmehta.camerauploadexample.utils.view.getAppBarFab
import org.niralmehta.camerauploadexample.utils.view.setFabIcon
import org.niralmehta.camerauploadexample.utils.view.snack
import javax.inject.Inject

class ScreenUpload : Fragment(), Injectable, OnBackPressedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var uploadView: View
    lateinit var photo: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        uploadView = inflater.inflate(R.layout.screen_upload, container, false)
        return uploadView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uploadViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UploadViewModel::class.java)

        upload_image_preview.setImageBitmap(photo)

        setFabIcon(activity, R.drawable.ic_upload_dark, this)

        getAppBarFab(activity)?.setOnClickListener {
            uploadImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.action_upload)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_camera -> addScreen(ScreenCamera(), ScreenCamera.SCREEN_TAG, true, true)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun uploadImage() {
        val title = upload_image_title_field.text.toString()
        val description = upload_image_description_field.text.toString()
        val image = uploadViewModel.get64BaseImage(photo)

        val payload = ImagePayload(
            image = image,
            title = title,
            description = description,
            name = ""
        )

        val clientId = BuildConfig.CLIENTID
        uploadViewModel.uploadImage(clientId, payload).observe(this, Observer { resource ->
            if (resource != null) when (resource.status) {
                Status.ERROR -> view?.snack("Upload was unsuccessful")

                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    if (resource.data != null) {
                        view?.snack("Upload was successful")
                        setupSuccessCard(resource.data.data)
                    }
                }
            }
        })
    }

    private fun setupSuccessCard(data: Data) {
        upload_image_preview.visibility = View.GONE
        upload_container_information_update.visibility = View.GONE
        upload_container_information_success.visibility = View.VISIBLE

        upload_details_id.text = "Id: ${data.id}"
        upload_details_title.text = "Title: ${data.title}"
        upload_details_description.text = "Description: ${data.description}"
        upload_details_dimensions.text = "Dimensions: ${data.height}px x ${data.width}px"
        upload_details_link.text = "Link: ${data.link}"
    }

    override fun onBackPressed() {

    }

    companion object {
        const val SCREEN_TAG = "screen_upload"
    }
}