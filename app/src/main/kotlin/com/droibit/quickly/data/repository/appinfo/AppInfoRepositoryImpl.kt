package com.droibit.quickly.data.repository.appinfo

import com.droibit.quickly.data.repository.source.AppInfoDataSource
import com.github.gfx.android.orma.annotation.OnConflict
import rx.Observable
import rx.Single

class AppInfoRepositoryImpl(
        private val orma: OrmaDatabase,
        private val appInfoSource: AppInfoDataSource) : AppInfoRepository {

    override fun loadAll(): Observable<List<AppInfo>> {
        // TODO: need review
        return orma.selectFromAppInfo()
                .executeAsObservable()
                .switchIfEmpty(storeAll())
                .toList()
    }

    override fun reload(): Observable<List<AppInfo>> {
        return orma.deleteFromAppInfo()
                .executeAsObservable()
                .toObservable()
                .flatMap {
                    appInfoSource.getAll()
                            .doOnNext { storeSync(appInfoList = it) }
                }
    }

    override fun addOrUpdate(appInfo: AppInfo): Single<Boolean> {
        return orma.prepareInsertIntoAppInfoAsObservable(OnConflict.REPLACE)
                .flatMap { it.executeAsObservable(appInfo) }
                .map { it > 0 }
    }

    override fun delete(packageName: String): Single<Boolean> {
        return orma.deleteFromAppInfo()
                .packageNameEq(packageName)
                .executeAsObservable()
                .map { it > 0 }
    }

    private fun storeAll(): Observable<AppInfo> {
        return appInfoSource.getAll()
                .doOnNext { storeSync(appInfoList = it) }
                .flatMap { Observable.from(it) }
    }

    private fun storeSync(appInfoList: List<AppInfo>) {
        orma.transactionSync {
            orma.prepareInsertIntoAppInfo()
                    .executeAll(appInfoList)
        }
    }
}