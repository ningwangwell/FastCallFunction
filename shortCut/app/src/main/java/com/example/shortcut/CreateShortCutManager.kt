package com.example.shortcut

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log

class CreateShortCutManager {
    private var peopleName = ""
    private var peopleImage = ""
    fun createShortcut(activity: Activity, data: String?, image: Int, actionIntent: Intent): Boolean {
        if (data != null) {
            peopleName = data
        }
        peopleImage = image.toString()
        val iconRes = Intent.ShortcutIconResource.fromContext(activity, R.drawable.ic_meditation)
        val bitmap = BitmapFactory.decodeResource(Resources.getSystem(), image)
        Log.i("ChatActivity", "bitmap = $bitmap")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val shortcut = Intent("com.android.launcher.action.INSTALL_SHORTCUT")
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, data)
            shortcut.putExtra("duplicate", false)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconRes)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent)
            activity.sendBroadcast(shortcut)
            return true

        } else {
            val shortcutMsg: ShortcutManager = activity.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
            if (null == shortcutMsg) {
                Log.e("ChatActivity", "create shortcut failed")
                return false
            }
            val mShortcutInfoBuilder = ShortcutInfo.Builder(activity, data.toString())
            mShortcutInfoBuilder.setShortLabel(data.toString())
            mShortcutInfoBuilder.setLongLabel(data.toString())
            mShortcutInfoBuilder.setIcon(Icon.createWithResource(activity.applicationContext,image))
            val shortcutIntent = Intent(activity.application, CallActivity::class.java)
            shortcutIntent.putExtra("people_name", peopleName)
            shortcutIntent.putExtra("people_avatar",peopleImage)
            shortcutIntent.action = Intent.ACTION_CREATE_SHORTCUT
            mShortcutInfoBuilder.setIntent(shortcutIntent)
            val mShortcutInfo = mShortcutInfoBuilder.build()
            val mShortcutManager = activity.getSystemService(ShortcutManager::class.java)
            mShortcutManager.requestPinShortcut(mShortcutInfo, null)
            return true
        }
    }
}