package org.niralmehta.camerauploadexample.screens

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.screen_camera.*
import org.niralmehta.camerauploadexample.R
import org.niralmehta.camerauploadexample.di.Injectable
import org.niralmehta.camerauploadexample.modules.upload.fragment.ScreenUpload
import org.niralmehta.camerauploadexample.utils.interfaces.OnBackPressedListener
import org.niralmehta.camerauploadexample.utils.view.getAppBarFab
import org.niralmehta.camerauploadexample.utils.view.setFabIcon
import org.niralmehta.camerauploadexample.utils.view.snack


class ScreenCamera : Fragment(), Injectable, OnBackPressedListener {

    private lateinit var cameraView: View
    private var photo: Bitmap? = null
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        cameraView = inflater.inflate(R.layout.screen_camera, container, false)
        return cameraView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFabIcon(activity, R.drawable.ic_camera_dark, this)

        getAppBarFab(activity)?.setOnClickListener {
            launchCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                photo = MediaStore.Images.Media.getBitmap(context!!.contentResolver, uri)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, photo)
                roundedBitmapDrawable.cornerRadius = 6f

                if (photo != null) {
                    camera_image.visibility = View.VISIBLE
                    camera_container_information.visibility = View.VISIBLE
                }

                camera_image.setImageDrawable(roundedBitmapDrawable)
                camera_details_height.text = "Height: ${photo?.height.toString()}px"
                camera_details_width.text = "Width: ${photo?.width.toString()}px"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.action_camera)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_upload -> uploadImage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchCamera() {
        val contentValues = ContentValues()
        uri = context!!.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) as Uri

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, uri)

        this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    private fun uploadImage() {
        if (photo != null) {
            val uploadScreen = ScreenUpload()
            photo?.let { uploadScreen.photo = it }
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container_main, uploadScreen, ScreenUpload.SCREEN_TAG)
                ?.addToBackStack(null)
                ?.commit()
        } else {
            view?.snack("Please take a photo first")
        }
    }

    override fun onBackPressed() {
    }

    companion object {
        const val SCREEN_TAG = "screen_camera"
        const val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888
    }
}