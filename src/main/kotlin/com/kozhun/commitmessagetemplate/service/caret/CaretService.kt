package com.kozhun.commitmessagetemplate.service.caret

/**
 * Represents a service for manipulating caret positions in a text message.
 */
fun interface CaretService {

    /**
     * Returns the caret offset by anchor in the given message.
     *
     * @param message The string message to calculate the caret offset.
     * @return A Pair object containing the modified string message and the caret offset.
     */
    fun getCaretOffsetByAnchor(message: String): Pair<String, Int>
}
