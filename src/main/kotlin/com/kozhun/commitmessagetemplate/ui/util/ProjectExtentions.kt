package com.kozhun.commitmessagetemplate.ui.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

fun Project.showCommittleNotification(content: String, type: NotificationType) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup("Committle Notifications")
        .createNotification("Committle", content, type)
        .notify(this)
}
