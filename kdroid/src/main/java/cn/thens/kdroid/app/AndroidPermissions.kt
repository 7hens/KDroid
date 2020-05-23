package cn.thens.kdroid.app

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object AndroidPermissions {
    suspend fun requestAll(context: Context, vararg permissions: String): Map<String, Boolean> {
        val surePermissions = mutableMapOf<String, Boolean>()
        val notSurePermissions = mutableListOf<String>()
        permissions.forEach { permission ->
            if (isGranted(context, permission)) {
                surePermissions[permission] = true
            } else {
                notSurePermissions.add(permission)
            }
        }
        ActivityRequest(context).permissions(*notSurePermissions.toTypedArray())
                .toMap(surePermissions)
        return surePermissions
    }

    suspend fun request(context: Context, permission: String): Boolean {
        if (isGranted(context, permission)) return true
        return ActivityRequest(context).permissions(permission)[permission] == true
    }

    fun shouldShowRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun isGranted(context: Context, vararg permissions: String): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}