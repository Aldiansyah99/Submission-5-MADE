package id.aldiansyah.moviecataloguesub5.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.setting.reminder.DailyReminder;
import id.aldiansyah.moviecataloguesub5.setting.reminder.ReleaseReminder;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSetting;
    Switch mSwitchDaily, mSwitchRelease;
    ReleaseReminder releaseReminder;
    DailyReminder dailyReminder;
    SharedPreferences sharedPreferences = null;
    public static final String NAME = "sharedpref_name";
    public static final String KEY_DAILY = "key_daily";
    public static final String KEY_RELEASE = "key_release";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.text_setting);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSetting = findViewById(R.id.tv_language_setting);
        mSwitchDaily = findViewById(R.id.switch_daily_reminder);
        mSwitchRelease = findViewById(R.id.switch_release_today_reminder);
        tvSetting.setOnClickListener(this);

        releaseReminder = new ReleaseReminder();
        dailyReminder = new DailyReminder();

        sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_DAILY, null) != null) {
            mSwitchDaily.setChecked(true);
        } else {
            mSwitchDaily.setChecked(false);
        }
        if (sharedPreferences.getString(KEY_RELEASE, null) != null) {
            mSwitchRelease.setChecked(true);
        } else {
            mSwitchRelease.setChecked(false);
        }

        mSwitchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    releaseReminder.setAlarmRelease(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_RELEASE, "Release");
                    editor.apply();
                } else {
                    releaseReminder.cancelAlarmRelease(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_RELEASE);
                    editor.apply();
                }
            }
        });

        mSwitchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    dailyReminder.setAlarmDaily(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_DAILY, "Daily");
                    editor.apply();
                } else {
                    dailyReminder.cancelAlarmDaily(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_DAILY);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_language_setting) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }
}
