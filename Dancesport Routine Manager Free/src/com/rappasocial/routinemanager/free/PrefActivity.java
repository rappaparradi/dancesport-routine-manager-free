package com.rappasocial.routinemanager.free;

import com.rappasocial.routinemanager.free.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(
                SharedPrefs.PREFS_NAME);
        addPreferencesFromResource(R.xml.prefs);
        findViewById(android.R.id.list).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_debut_dark));

    }
}
