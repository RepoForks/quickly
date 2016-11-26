package com.droibit.quickly.main

import com.droibit.quickly.data.provider.eventbus.RxBus
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.jakewharton.rxrelay.PublishRelay

fun mainModule() = Kodein.Module {

    bind<MainContract.ShowSettingsTask>() with provider {
        ShowSettingsTask(showSettingsRepository = instance())
    }

    bind<MainContract.LoadAppInfoTask>() with provider {
        LoadAppInfoTask(
                appInfoRepository = instance(),
                showSettingsRepository = instance(),
                runningRelay = instance()
        )
    }

    bind<PublishRelay<Boolean>>() with provider { PublishRelay.create<Boolean>() }

    bind<RxBus>() with singleton { RxBus() }
}