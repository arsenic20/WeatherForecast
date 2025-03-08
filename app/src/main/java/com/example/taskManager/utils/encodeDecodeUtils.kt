package com.example.taskManager.utils

import android.util.Base64
import com.example.taskManager.model.TaskEntity
import com.google.gson.Gson
import okhttp3.internal.concurrent.Task

fun serializeTaskBase64(task: TaskEntity): String {
    val json = Gson().toJson(task)
    return Base64.encodeToString(json.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

fun deserializeTaskBase64(base64: String): TaskEntity {
    val json = String(Base64.decode(base64, Base64.NO_WRAP), Charsets.UTF_8)
    return Gson().fromJson(json, TaskEntity::class.java)
}