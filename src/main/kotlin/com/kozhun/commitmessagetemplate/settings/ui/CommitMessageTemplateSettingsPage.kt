package com.kozhun.commitmessagetemplate.settings.ui

import javax.swing.JPanel
import javax.swing.JTextField


class CommitMessageTemplateSettingsPage {
    private lateinit var rootPanel: JPanel

    private lateinit var pattern: JTextField

    fun getRootPanel(): JPanel {
        return rootPanel
    }

    fun initUI() {
        println("init UI")
    }
}
