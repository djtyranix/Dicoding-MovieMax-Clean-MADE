package com.nixstudio.moviemax.views.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.nixstudio.moviemax.R

class SettingsFragment : PreferenceFragmentCompat() {

    private var languageChange: Preference? = null
    private var darkModeSwitch: ListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val preference = activity?.getSharedPreferences("moviemax-prefs", Context.MODE_PRIVATE)
        val editor = preference?.edit()

        languageChange = findPreference("select_language")

        val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        languageChange?.intent = languageIntent

        darkModeSwitch = findPreference("mode_switch")
        when (preference?.getString("darkMode", "system")) {
            "dark" -> {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
            }

            "light" -> {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
            }

            else -> {
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    )
            }
        }

        darkModeSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    "light" -> {
                        AppCompatDelegate
                            .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_NO
                            )

                        editor?.putString("darkMode", "light")
                        editor?.apply()
                    }

                    "dark" -> {
                        AppCompatDelegate
                            .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_YES
                            )

                        editor?.putString("darkMode", "dark")
                        editor?.apply()
                    }

                    else -> {
                        AppCompatDelegate
                            .setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                            )

                        editor?.putString("darkMode", "system")
                        editor?.apply()
                    }
                }

                return@OnPreferenceChangeListener true
            }
    }
}