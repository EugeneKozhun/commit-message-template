package com.kozhun.commitmessagetemplate.listener

import com.intellij.ide.util.RunOnceUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.kozhun.commitmessagetemplate.util.storage

class SettingsMigrator : ProjectActivity {

    override suspend fun execute(project: Project) {
        RunOnceUtil.runOnceForProject(project, "CMTMigration_001") {
            val storageState = project.storage().state
            if (!storageState.pattern.isNullOrEmpty()) {
                storageState.pattern = storageState.pattern?.replace("\$PROJECT_NAME", "\$SCOPE")
                project.storage().loadState(storageState)
            }
        }
    }
}
