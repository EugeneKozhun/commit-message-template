import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.kozhun.commitmessagetemplate.ui.dto.SynonymPair
import javax.swing.JComponent
import javax.swing.JTextField

class SynonymDialog(
    private val keys: Set<String>, private val initialValue: SynonymPair? = null
) : DialogWrapper(true) {

    var value: String = initialValue?.key.orEmpty()
    var synonym: String = initialValue?.value.orEmpty()

    private lateinit var valueTextField: JTextField
    private lateinit var synonymTextField: JTextField

    init {
        init()
        title = if (initialValue == null) "Add Synonym" else "Edit Synonym"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("Value:") {
                valueTextField = textField().bindText(::value).focused().component
            }
            row("Synonym:") {
                synonymTextField = textField().bindText(::synonym).component
            }
        }
    }

    override fun doOKAction() {
        value = valueTextField.text.trim()
        synonym = synonymTextField.text.trim()

        val validationError = validateInput()
        if (validationError != null) {
            setErrorText(validationError.message, validationError.component)
            return
        }

        super.doOKAction()
    }

    private fun validateInput(): ValidationInfo? {
        return when {
            value.isEmpty() -> ValidationInfo("Value cannot be empty", valueTextField)
            value != initialValue?.key && keys.contains(value)-> ValidationInfo("Value already exists in synonyms table", valueTextField)
            synonym.isEmpty() -> ValidationInfo("Synonym cannot be empty", synonymTextField)
            else -> null
        }
    }
}

