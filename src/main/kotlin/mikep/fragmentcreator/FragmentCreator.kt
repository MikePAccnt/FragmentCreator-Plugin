package mikep.fragmentcreator

import com.squareup.kotlinpoet.*
import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class FragmentCreator(filePath: String, val className: String, outputPath: String) {

    private val xmlDocument: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(File(filePath))
    private val regex = Regex("\\w+\\.xml")
    private val layoutName = "R.layout.${regex.find(filePath)?.value?.replace(".xml", "")}"
    private var clazz = AndroidClasses.createFragmentClass(className, layoutName)
    private val file = File(outputPath)
    fun buildFragmentClass(){

        createViewValues(xmlDocument.childNodes.item(0).childNodes)

        print(clazz.toString())
        file.createNewFile()
        FileSpec.builder("", className).addType(clazz.build()).build().writeTo(file)
    }

    private fun createViewValues(nodeList: NodeList) {
        for(i in 1 until nodeList.length step 2) {
            val node = nodeList.item(i)
            node.attributes.androidIdOrNull?.let {
                createValue(it, node.nodeName)
            }
            if(node.hasChildNodes()) createViewValues(node.childNodes)
        }
    }

    private fun createValue(valueName: String, nodeName: String) {
        val valueNameParts = valueName.split("_")
        var propertyName = ""
        valueNameParts.forEachIndexed { index, s ->
            if(index > 0) {
                val string = s.replace(s.first(),s.first().toUpperCase())
                propertyName = propertyName.plus(string)
            } else {
                propertyName = propertyName.plus(s)
            }
        }
        clazz.addProperty(
            PropertySpec.builder(
                propertyName,
                AndroidClasses.classNameFromNodeName(nodeName)
            ).delegate(
                CodeBlock.builder()
                    .addStatement("findViewById(${valueName.rId})")
                    .build())
            .build())
    }

}

val NamedNodeMap.androidIdOrNull: String?
    get() = this.getNamedItem("android:id")?.nodeValue?.replace("@+id/", "")

val String.rId : String
    get() = "R.id.$this"