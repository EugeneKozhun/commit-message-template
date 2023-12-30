package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.settings.storage.SettingsState
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import git4idea.GitLocalBranch
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BranchTaskIdReplacerTest {
    private lateinit var project: Project
    private lateinit var settingsStorage: SettingsStorage
    private lateinit var gitRepositoryManager: GitRepositoryManager
    private lateinit var replacer: BranchTaskIdReplacer

    @BeforeEach
    fun setUp() {
        mockkStatic(SettingsStorage::class)
        mockkStatic(GitRepositoryManager::class)
        project = mockk()
        settingsStorage = mockk()
        gitRepositoryManager = mockk()
        every { SettingsStorage.getInstance(project) } returns settingsStorage

        replacer = BranchTaskIdReplacer(project)
    }

    @Test
    fun `replace empty template with default regex`() {
        mockSettingState()
        mockBranchName(BRANCH_WITHOUT_TASK_ID)
        assertEquals("", replacer.replace(""))
    }

    @Test
    fun `replace empty template with custom regex`() {
        mockSettingState(CUSTOM_REGEX)
        mockBranchName(BRANCH_WITHOUT_TASK_ID)
        assertEquals("", replacer.replace(""))
    }

    @Test
    fun `replace empty template with branch without task id`() {
        val template = "Some changes"

        mockSettingState(CUSTOM_REGEX)
        mockBranchName(BRANCH_WITHOUT_TASK_ID)
        assertEquals(template, replacer.replace(template))
    }

    @Test
    fun `replace empty template with branch with task id`() {
        val template = "Some changes"

        mockSettingState(CUSTOM_REGEX)
        mockBranchName(BRANCH_WITH_TASK_ID)
        assertEquals(template, replacer.replace(template))
    }

    @Test
    fun `replace template with branch without task id`() {
        mockSettingState()
        mockBranchName(BRANCH_WITHOUT_TASK_ID)
        assertEquals("[]: Some changes", replacer.replace("[${BranchTaskIdReplacer.TASK_ID_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace template with branch with task id`() {
        mockSettingState()
        mockBranchName(BRANCH_WITH_TASK_ID)
        assertEquals("[$TASK_ID]: Some changes", replacer.replace("[${BranchTaskIdReplacer.TASK_ID_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace template with branch with different task id`() {
        mockSettingState(CUSTOM_TASK_ID)
        mockBranchName(BRANCH_WITH_TASK_ID)
        assertEquals("[]: Some changes", replacer.replace("[${BranchTaskIdReplacer.TASK_ID_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace template with branch with custom task id`() {
        mockSettingState(CUSTOM_TASK_ID)
        mockBranchName(BRANCH_WITH_CUSTOM_TASK_ID)
        assertEquals("[$CUSTOM_TASK_ID]: Some changes", replacer.replace("[${BranchTaskIdReplacer.TASK_ID_ANCHOR}]: Some changes"))
    }

    private fun mockBranchName(branchName: String) {
        val gitRepository = mockk<GitRepository>()
        val gitLocalBranch = mockk<GitLocalBranch>()

        every { gitRepositoryManager.repositories } returns listOf(gitRepository)
        every { gitLocalBranch.name } returns branchName
        every { gitRepository.currentBranch } returns gitLocalBranch
        every { GitRepositoryManager.getInstance(project) } returns gitRepositoryManager
    }

    private fun mockSettingState(customTaskIdRegex: String = "") {
        val settingsState = SettingsState()
        settingsState.taskIdRegex = customTaskIdRegex
        every { settingsStorage.state } returns settingsState
    }

    private companion object {
        const val BRANCH_WITHOUT_TASK_ID = "master"

        const val TASK_ID = "CMT-123"
        const val BRANCH_WITH_TASK_ID = "feature/$TASK_ID-refactoring"

        const val CUSTOM_REGEX = "TEST-\\d+"
        const val CUSTOM_TASK_ID = "TEST-123"
        const val BRANCH_WITH_CUSTOM_TASK_ID = "feature/$CUSTOM_TASK_ID-refactoring"
    }
}
