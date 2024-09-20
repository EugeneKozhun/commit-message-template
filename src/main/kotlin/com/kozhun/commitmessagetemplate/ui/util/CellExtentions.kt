package com.kozhun.commitmessagetemplate.ui.util

import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.MutableProperty
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.toMutableProperty
import javax.swing.text.JTextComponent
import kotlin.reflect.KMutableProperty0

fun <T : JTextComponent> Cell<T>.bindNullableText(prop: KMutableProperty0<String?>): Cell<T> {
    return bindNullableText(prop.toMutableProperty())
}

fun <T : JTextComponent> Cell<T>.bindNullableText(prop: MutableProperty<String?>): Cell<T> {
    return bindText({ prop.get().orEmpty() }, { prop.set(it) })
}
