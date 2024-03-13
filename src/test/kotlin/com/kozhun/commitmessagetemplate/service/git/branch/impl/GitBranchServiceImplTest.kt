package com.kozhun.commitmessagetemplate.service.git.branch.impl

import com.intellij.openapi.project.Project
import git4idea.GitLocalBranch
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GitBranchServiceImplTest {
    private lateinit var projectMock: Project
    private lateinit var gitBranchMock: GitLocalBranch
    private lateinit var gitBranchService: GitBranchServiceImpl

    @BeforeEach
    fun setUp() {
        projectMock = mockk()
        gitBranchMock = mockk<GitLocalBranch>()
        gitBranchService = GitBranchServiceImpl(projectMock)
    }

    @Test
    fun `get current branch`() {
        mockGitRepositoryManager()
        val result = gitBranchService.getCurrentBranch()
        assertEquals(gitBranchMock, result)
    }

    @Test
    fun `should throws exception if empty branch list`() {
        mockGitRepositoryManager(isEmpty = true)
        assertThrows<IllegalStateException> {
            gitBranchService.getCurrentBranch()
        }
    }

    private fun mockGitRepositoryManager(isEmpty: Boolean = false) {
        mockkStatic(GitRepositoryManager::class)

        val gitRepositoryManagerMock = mockk<GitRepositoryManager>()
        val gitRepositoryMock = mockk<GitRepository>()

        every { gitRepositoryMock.currentBranch } returns gitBranchMock
        every { gitRepositoryManagerMock.repositories } returns if (isEmpty) listOf() else listOf(gitRepositoryMock)
        every { GitRepositoryManager.getInstance(projectMock) } returns gitRepositoryManagerMock
    }
}
