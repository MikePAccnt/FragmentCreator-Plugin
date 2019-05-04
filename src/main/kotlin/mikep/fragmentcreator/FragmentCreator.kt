package mikep.fragmentcreator

import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class FragmentCreator(filePath: String, className: String, outputPath: String) {

    private val xmlDocument: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(File(filePath))
    private var clazz: String = "class $className : Fragment() {\n"
    private val regex = Regex("\\w+\\.xml")
    private val layoutName = "R.layout.${regex.find(filePath)?.value?.replace(".xml", "")}"
    private val file = File("$outputPath\\$className.kt")
    fun buildFragmentClass(){
        printChildNodes(xmlDocument.childNodes.item(0).childNodes)
        clazz = clazz.plus("\n\n\toverride fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {\n\t\treturn inflater.inflate($layoutName, container, false)\n\t}")
        clazz = clazz.plus("\n\n}")
        print(clazz)
        file.createNewFile()
        file.writeText(clazz)

    }

    private fun printChildNodes(nodeList: NodeList) {
        for(i in 1 until nodeList.length step 2) {
            val node = nodeList.item(i)
            node.attributes.androidIdOrNull?.let {
                val nodeName = if(node.nodeName == "include") "ViewGroup" else node.nodeName
                clazz = clazz.plus("\n\tprivate val $it: $nodeName by findViewById(${it.rId})")
            }
            if(node.hasChildNodes()) printChildNodes(node.childNodes)
        }
    }
}

val NamedNodeMap.androidIdOrNull: String?
    get() = this.getNamedItem("android:id")?.nodeValue?.replace("@+id/", "")

val String.rId : String
    get() = "R.id.$this"