package org.niralmehta.camerauploadexample.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.bottom_app_bar.*
import org.niralmehta.camerauploadexample.R
import org.niralmehta.camerauploadexample.screens.ScreenCamera
import org.niralmehta.camerauploadexample.utils.fragment.addScreen
import org.niralmehta.camerauploadexample.utils.interfaces.OnBackPressedListener
import org.niralmehta.camerauploadexample.utils.view.snack
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var mainView: View

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()

        val permission = ContextCompat
            .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        mainView = (this.findViewById(R.id.content) as ViewGroup).getChildAt(0)

        if (permission != PackageManager.PERMISSION_GRANTED) requestPermissions() else setupScreen()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val fragments = supportFragmentManager.fragments
            fragments.filter { it is OnBackPressedListener }
                .map {
                    (it as OnBackPressedListener).onBackPressed()
                }
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        mainView.snack("정빈 씨발련ㄴ아")

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            ACCESS_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setupScreen()
                }
            }
        }
    }

    private fun requestPermissions() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                ACCESS_WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun setupToolbar() =
        setSupportActionBar(bottom_app_bar)


    private fun setupScreen() = addScreen(
        ScreenCamera(),
        ScreenCamera.SCREEN_TAG,
        true,
        true
    )


    companion object {
        const val ACCESS_WRITE_EXTERNAL_STORAGE = 1
    }
}
