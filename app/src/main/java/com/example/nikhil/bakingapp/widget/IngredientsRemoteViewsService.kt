package com.example.nikhil.bakingapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by NIKHIL on 24-12-2017.
 */
class IngredientsRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return IngredientsRemoteViewsFactory(baseContext)
    }
}
