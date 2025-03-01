package com.kozhun.commitmessagetemplate.util

import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.git.branch.impl.GitBranchServiceImpl
import com.kozhun.commitmessagetemplate.service.settings.SettingsExporter
import com.kozhun.commitmessagetemplate.storage.SettingsStorage

fun Project.storage() = SettingsStorage.getInstance(this)

fun Project.branches() = GitBranchServiceImpl.getInstance(this)

fun Project.settingExporter() = SettingsExporter.getInstance(this)
