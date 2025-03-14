package com.kozhun.commitmessagetemplate.service.settings

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.storage.toExportableSettings
import com.kozhun.commitmessagetemplate.util.storage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.awt.Frame
import java.io.FilenameFilter

@Service(Service.Level.PROJECT)
class SettingsExporter(
    private val project: Project
) {
    init {
        if (System.getProperty("os.name").contains("Mac", ignoreCase = true)) {
            System.setProperty("apple.awt.fileDialogForDirectories", "false")
        }
    }

    fun export() {
        val fileDialog = FileDialog(null as Frame?, "Select Export Location", FileDialog.SAVE).apply {
            file = "committle-config.json"
            isVisible = true
        }

        val selectedFile = fileDialog.files.firstOrNull() ?: return
        val exportableSettings = project.storage().state.toExportableSettings()

        try {
            val json = Json.encodeToString(exportableSettings)
            selectedFile.writeText(json)
            showNotification("Settings exported successfully to ${selectedFile.absolutePath}.", "Export Successful", NotificationType.INFORMATION)
        } catch (exception: IllegalArgumentException) {
            showNotification("Failed to export settings: ${exception.message}", "Export Failed", NotificationType.ERROR)
        }
    }

    fun import() {
        val fileDialog = FileDialog(null as Frame?, "Select Import File", FileDialog.LOAD).apply {
            filenameFilter = FilenameFilter { _, name -> name.endsWith(".json") }
            isVisible = true
        }

        val selectedFile = fileDialog.files.firstOrNull() ?: return
        val importableSettings: ExportableSettings = try {
            Json.decodeFromString(selectedFile.readText())
        } catch (exception: IllegalArgumentException) {
            showNotification("Failed to import settings: ${exception.message}", "Import Failed", NotificationType.ERROR)
            return
        }

        project.storage().loadState(importableSettings.toSettingsState())

        showNotification("Settings imported successfully from ${selectedFile.absolutePath}.", "Import Successful", NotificationType.INFORMATION)
    }

    private fun showNotification(content: String, title: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Committle Notifications")
            .createNotification(title, content, type)
            .notify(project)
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): SettingsExporter = project.service<SettingsExporter>()
    }
}
