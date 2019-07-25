package com.example.myapplication.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;


public class Settings_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);

            //Define the  Preference, set its summary, and set onChangeListener
            Preference sortByPreference = findPreference(getString(R.string.sort_by_key));
            setUpPreference(sortByPreference);
        }
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            ListPreference listPreference = (ListPreference) preference;
            String sortBySetting = value.toString();

            int arrayValueIndex = listPreference.findIndexOfValue(sortBySetting);


            if (arrayValueIndex >= 0) {
                CharSequence[] entries = listPreference.getEntries();
                preference.setSummary(entries[arrayValueIndex]);
            }
            return true;
        }

        private void setUpPreference(Preference preference) {
            Context context = preference.getContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String selectedSort =
                    sharedPreferences.getString(preference.getKey(), getString(R.string.sort_by_pref));
            preference.setOnPreferenceChangeListener(this);

            onPreferenceChange(preference, selectedSort);
        }

    }

}

