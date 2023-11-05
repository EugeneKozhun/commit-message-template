package com.kozhun.commitmessagetemplate.service.impl

import com.intellij.openapi.components.Service
import com.kozhun.commitmessagetemplate.service.CommitMessageFormatter
import kotlin.random.Random

@Service(Service.Level.PROJECT)
class CommitMessagePatternFormatter : CommitMessageFormatter {
    override fun getCommitMessageTemplate(): String {
        return "Hello + ${random.nextInt()}"
    }

    companion object {
        // TODO: remove it. Needed for testing.
        private val random = Random(3)
    }
}
