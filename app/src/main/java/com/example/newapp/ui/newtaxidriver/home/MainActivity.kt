package common.home

import android.os.Bundle
import com.example.newapp.ui.newtaxidriver.common.views.CommonActivity
import com.example.newapp.ui.newtaxidriver.home.interfaces.ServiceListener
import javax.inject.Inject

class MainActivity : CommonActivity(), ServiceListener {
    @Inject
    lateinit var local: SessionManager
}

