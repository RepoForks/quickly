package com.droibit.quickly.main

import android.support.annotation.IdRes
import com.droibit.quickly.R
import com.droibit.quickly.data.repository.appinfo.AppInfo
import rx.Observable

interface MainContract {

    enum class MenuItem(@IdRes val id: Int) {
        REFRESH(R.id.refresh),
        SHOW_SYSTEM(R.id.show_system),
        SETTINGS(R.id.settings);

        companion object {
            @JvmStatic
            fun from(@IdRes id: Int) = values().first { it.id == id }
        }
    }

    interface View {

        fun showAppInfoList(appInfos: List<AppInfo>)

        fun showNoAppInfo()

        fun setLoadingIndicator(active: Boolean)
    }

    interface Navigator {

        fun navigateSettings()
    }

    interface Presenter {

        fun onCreate(shouldLoad: Boolean)

        fun onResume()

        fun onPause()

        fun onOptionsItemClicked(menuItem: MenuItem)
    }

    interface LoadAppInfoTask {

        sealed class LoadEvent {
            class OnResult(val appInfos: List<AppInfo>) : LoadEvent()
            object Nothing : LoadEvent()
        }

        fun asObservable(): Observable<LoadEvent>

        fun isRunning(): Observable<Boolean>

        fun load(forceReload: Boolean)
    }
}