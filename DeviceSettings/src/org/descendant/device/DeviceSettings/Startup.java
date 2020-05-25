/*
* Copyright (C) 2013 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.descendant.device.DeviceSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.preference.PreferenceManager;

public class Startup extends BroadcastReceiver {

    private boolean mHBM = false;
    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            Utils.writeValue(file, mHBM ? "5" : "1");
        }
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
        boolean enabled = false;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);

        if (enabled) {
        mHBM = false;
        Utils.setDisplayMode(16, 0);
        Utils.setDisplayMode(17, 0);
        Utils.setDisplayMode(18, 0);
        Utils.setDisplayMode(20, 0);
        Utils.setDisplayMode(21, 0);
        Utils.setDisplayMode(20, 1);
		}
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_HBM_SWITCH, false);
        if (enabled) {
        mHBM = true;
        restore(HBMModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DC_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCI_SWITCH, false);
        if (enabled) {
        mHBM = false;
        Utils.setDisplayMode(16, 0);
        Utils.setDisplayMode(17, 0);
        Utils.setDisplayMode(18, 0);
        Utils.setDisplayMode(20, 0);
        Utils.setDisplayMode(21, 0);
        Utils.setDisplayMode(21, 1);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_NIGHT_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(NightModeSwitch.getFile(), enabled);
        }
        VibratorStrengthPreference.restore(context);
    }
}
