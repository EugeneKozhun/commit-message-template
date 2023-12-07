package com.kozhun.commitmessagetemplate.settings.enums

enum class BranchTypePostprocessor(val label: String) {
    NONE("None"),
    CAPITALIZE("Capitalize"),
    UPPERCASE("UPPERCASE"),
    LOWERCASE("lowercase");

    companion object {

        /**
         * Finds the enum constant with the specified label, or null if no such constant exists.
         *
         * @param label the label of the enum constant to find
         * @return the enum constant with the specified label, or null if no such constant exists
         */
        fun labelValueOf(label: String) = values().find { it.label == label }
    }
}
