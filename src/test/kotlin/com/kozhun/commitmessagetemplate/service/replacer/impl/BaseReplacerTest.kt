package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.git.branch.GitBranchService
import com.kozhun.commitmessagetemplate.service.git.branch.impl.GitBranchServiceImpl
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.settings.storage.SettingsState
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import git4idea.GitLocalBranch
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic

abstract class BaseReplacerTest {
    protected lateinit var projectMock: Project

    protected fun mockBranchName(branchName: String) {
        mockkStatic(GitBranchServiceImpl::class)

        val gitBranchServiceMock = mockk<GitBranchService>()
        val gitLocalBranchMock = mockk<GitLocalBranch>()

        every { gitLocalBranchMock.name } returns branchName
        every { gitBranchServiceMock.getCurrentBranch() } returns gitLocalBranchMock
        every { GitBranchServiceImpl.getInstance(projectMock) } returns gitBranchServiceMock
    }

    protected fun mockSettingState(
        customTaskIdRegex: String = "",
        customTypeRegex: String = "",
        typePostprocessor: StringCase = StringCase.NONE
    ) {
        mockkStatic(SettingsStorage::class)

        val settingsStorageMock = mockk<SettingsStorage>()
        val settingsStateMock = mockk<SettingsState>()

        every { settingsStateMock.taskIdRegex } returns customTaskIdRegex
        every { settingsStateMock.typeRegex } returns customTypeRegex
        every { settingsStateMock.typePostprocessor } returns typePostprocessor.label

        every { settingsStorageMock.state } returns settingsStateMock
        every { SettingsStorage.getInstance(projectMock) } returns settingsStorageMock
    }
}
