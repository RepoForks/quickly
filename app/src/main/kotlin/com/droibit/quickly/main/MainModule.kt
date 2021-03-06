package com.droibit.quickly.main

import com.droibit.quickly.data.provider.eventbus.BehaviorBus
import com.droibit.quickly.data.provider.eventbus.RxBus
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.jakewharton.rxrelay.BehaviorRelay

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

    bind<BehaviorRelay<Boolean>>() with provider { BehaviorRelay.create<Boolean>() }

    bind<RxBus>("localEventBus") with singleton { BehaviorBus() }
}
