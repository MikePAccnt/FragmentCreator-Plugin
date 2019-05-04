package mikep.fragmentcreator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.*
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class XmlTransformAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val document = e.getData(LangDataKeys.EDITOR)?.document
        val currentFile = FileDocumentManager.getInstance().getFile(document ?: return)

        if(currentFile?.extension == "xml") {
            val dialog = MyDialog(e.project)
            dialog.showAndGetOk().doWhenDone(Runnable {
                val outputPath = dialog.folderSelection.text
                val className = dialog.className.text
                val fragmentCreator = FragmentCreator(currentFile.path, className, outputPath)
                fragmentCreator.buildFragmentClass()
            })
        }

    }

    inner class MyDialog(private val project: Project?) : DialogWrapper(project, true, IdeModalityType.MODELESS) {

        lateinit var folderSelection: TextFieldWithBrowseButton
            private set
        lateinit var className: JTextField
            private set

        init {
            init()
            setResizable(false)
            title = "Fragment Creator"

        }

        override fun createCenterPanel(): JComponent? {
            val panel = JPanel(BorderLayout())
            folderSelection = TextFieldWithBrowseButton()
            folderSelection.addBrowseFolderListener("Title", "Descriptioin", project, FileChooserDescriptor(false, true, false, false, false, false))
            folderSelection.preferredSize = Dimension(500, 24)
            panel.add(folderSelection, BorderLayout.NORTH)

            className = JTextField()
            className.isEditable = true
            className.preferredSize = Dimension(250, 24)
            panel.add(className, BorderLayout.SOUTH)
            return panel
        }

    }
}