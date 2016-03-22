/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.documentsui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Toolbar;

/*
 * Convenience class for getting display related attributes
 */
public final class Display {
    /*
     * Returns the screen width in raw pixels.
     */
    public static float screenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    /*
     * Returns logical density of the display.
     */
    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /*
     * Returns action bar height in raw pixels.
     */
    public static float actionBarHeight(Context context) {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return height;
    }

    /*
     * Returns status bar height in raw pixels.
     */
    private static int statusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /*
     * Adjusts toolbar for the layout with translucent status bar. Increases the
     * height of the toolbar and adds padding at the top to accommodate status bar visible above
     * toolbar.
     */
    public static void adjustToolbar(Toolbar toolbar, Activity activity) {
        if ((activity.getWindow().getAttributes().flags
                & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0) {
            int statusBarHeight = Display.statusBarHeight(activity);
            toolbar.getLayoutParams().height = (int) (Display.actionBarHeight(activity)
                    + statusBarHeight);
            toolbar.setPadding(toolbar.getPaddingLeft(), statusBarHeight, toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }
    }
}
