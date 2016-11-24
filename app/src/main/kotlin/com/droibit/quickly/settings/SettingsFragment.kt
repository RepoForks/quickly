package com.droibit.quickly.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.droibit.quickly.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}