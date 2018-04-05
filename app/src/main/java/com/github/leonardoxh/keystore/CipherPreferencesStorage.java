/*
 * Copyright 2018 Leonardo Rossetto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.leonardoxh.keystore;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Base64;

import javax.annotation.Nullable;

final class CipherPreferencesStorage {
    private CipherPreferencesStorage() {
        throw new AssertionError();
    }

    private static void saveKeyString(Context context, String alias, String value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(alias, value)
                .apply();
    }

    static void remove(Context context, String alias) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(alias)
                .apply();
    }

    static boolean containsAlias(Context context, String alias) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .contains(alias);
    }

    @Nullable
    private static String getKeyString(Context context, String alias) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(alias, null);
    }

    @Nullable
    static byte[] getKeyBytes(Context context, String alias) {
        String value = getKeyString(context, alias);
        if (value != null) {
            return Base64.decode(value, Base64.DEFAULT);
        }
        return null;
    }

    static void saveKeyBytes(Context context, String alias, byte[] value) {
        saveKeyString(context, alias, Base64.encodeToString(value, Base64.DEFAULT));
    }
}
