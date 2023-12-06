package com.kozhun.commitmessagetemplate.settings.enums

enum class BranchTypePostprocessor(val label: String) {
    NONE("None"),
    CAPITALIZE("Capitalize"),
    UPPERCASE("UPPERCASE"),
    LOWERCASE("lowercase");

    companion object {
        fun labelValueOf(label: String) = values().find { it.label == label }
    }
}
