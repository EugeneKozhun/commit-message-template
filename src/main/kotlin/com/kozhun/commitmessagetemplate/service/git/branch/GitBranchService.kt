package com.kozhun.commitmessagetemplate.service.git.branch

import git4idea.GitBranch

/**
 * Represents a service for retrieving the current Git branch.
 */
fun interface GitBranchService {

    /**
     * Returns the current Git branch.
     *
     * @return the current Git branch
     */
    fun getCurrentBranch(): GitBranch
}
