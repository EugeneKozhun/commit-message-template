package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.kozhun.commitmessagetemplate.settings.enums.BranchTypePostprocessor
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BranchTypeReplacerTest : BaseReplacerTest() {
    private lateinit var replacer: BranchTypeReplacer

    @BeforeEach
    fun setUp() {
        projectMock = mockk()
        replacer = BranchTypeReplacer(projectMock)
    }

    @Test
    fun `replace empty template with default regex`() {
        mockSettingState()
        mockBranchName(BRANCH_WITHOUT_TYPE_ID)
        assertEquals("", replacer.replace(""))
    }

    @Test
    fun `replace empty template with custom regex`() {
        mockSettingState(customTypeRegex = CUSTOM_TYPE_REGEX)
        mockBranchName(BRANCH_WITHOUT_TYPE_ID)
        assertEquals("", replacer.replace(""))
    }

    @Test
    fun `replace non-type branch with empty template`() {
        val template = "Some changes"
        mockSettingState()
        mockBranchName(BRANCH_WITHOUT_TYPE_ID)
        assertEquals(template, replacer.replace(template))
    }

    @Test
    fun `replace non-type branch with template`() {
        mockSettingState()
        mockBranchName(BRANCH_WITHOUT_TYPE_ID)
        assertEquals("[]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace with type in branch`() {
        mockSettingState()
        mockBranchName(BRANCH_WITH_TYPE)
        assertEquals("[${TYPE}]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }


    @Test
    fun `replace with mismatched type`() {
        mockSettingState(customTypeRegex = CUSTOM_TYPE_REGEX)
        mockBranchName(BRANCH_WITH_TYPE)
        assertEquals("[]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace with custom type in branch`() {
        mockSettingState(customTypeRegex = CUSTOM_TYPE_REGEX)
        mockBranchName(BRANCH_WITH_CUSTOM_TYPE)
        assertEquals("[${CUSTOM_TYPE}]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace with lowercase postprocessor`() {
        mockSettingState(typePostprocessor = BranchTypePostprocessor.LOWERCASE)
        mockBranchName("Feature/CMT-123-refactoring")
        assertEquals("[feature]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace with UPPERCASE postprocessor`() {
        mockSettingState(typePostprocessor = BranchTypePostprocessor.UPPERCASE)
        mockBranchName(BRANCH_WITH_TYPE)
        assertEquals("[FEATURE]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    @Test
    fun `replace with CAPITALIZE postprocessor`() {
        mockSettingState(typePostprocessor = BranchTypePostprocessor.CAPITALIZE)
        mockBranchName(BRANCH_WITH_TYPE)
        assertEquals("[Feature]: Some changes", replacer.replace("[${BranchTypeReplacer.TYPE_ANCHOR}]: Some changes"))
    }

    private companion object {
        const val BRANCH_WITHOUT_TYPE_ID = "master"

        const val TYPE = "feature"
        const val BRANCH_WITH_TYPE = "$TYPE/CMT-123-refactoring"

        const val CUSTOM_TYPE = "test"
        const val CUSTOM_TYPE_REGEX = "test"
        const val BRANCH_WITH_CUSTOM_TYPE = "$CUSTOM_TYPE/CMT-123-refactoring"
    }
}