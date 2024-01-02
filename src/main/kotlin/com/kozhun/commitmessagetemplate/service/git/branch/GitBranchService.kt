package com.kozhun.commitmessagetemplate.service.git.branch

import git4idea.GitBranch

fun interface GitBranchService {

    fun getCurrentBranch(): GitBranch
}
