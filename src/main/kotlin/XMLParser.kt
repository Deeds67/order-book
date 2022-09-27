import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


class XMLParser {
    var docBuilderFactory = DocumentBuilderFactory.newInstance()
    var docBuilder = docBuilderFactory.newDocumentBuilder()

    fun parse(fileName: String) {
        var doc = docBuilder.parse(File("src/main/resources/$fileName"))

    }
}