import groovy.json.JsonSlurper

class Converter {
  static void main(String[] args) {

    File file = new File("bom-examples")
    if (file.exists()) {
      println("Project has already been cloned")
    } else {

      if (Loader.run("git clone https://github.com/CycloneDX/bom-examples.git") == 0) {
        println("The repository is cloned")
      } else {
        throw new UnsupportedOperationException("Something went wrong !")
      }

    }

    def json = new JsonSlurper().parse(new File('bom-examples/SBOM/cern-lhc-vdm-editor-e564943/bom.json'))

    def xml = new XMLConverter()
    xml.convertToXML(json)

    def html = new HTMLConverter()
    html.convertToHTML(json)

    println("HTML VERSION:")

    new File("homework.html").eachLine { line ->
      println line
    }

    println()

    println("XML VERSION:")

    new File("homework.xml").eachLine { line ->
      println line
    }
  }
}