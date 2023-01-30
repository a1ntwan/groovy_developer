import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import org.junit.jupiter.api.Test

class XMLConverterTest extends GroovyTestCase {

    @Test
    void testHeader() {

        String jsonTestString = """{ 
"metadata": {
    "timestamp": "2020-08-03T01:28:52.765Z",
    "tools": [
      {
        "vendor": "CycloneDX",
        "name": "Node.js module",
        "version": "2.0.0"
      }
    ],
    "component": {
      "type": "library",
      "bom-ref": "pkg:npm/lhc-vdm-editor@0.0.1",
      "name": "lhc-vdm-editor",
      "version": "0.0.1",
      "description": "A code and file editor for VdM scans.",
      "licenses": [
        {
          "license": {
            "id": "Apache-2.0"
          }
        }
      ],
      "purl": "pkg:npm/lhc-vdm-editor@0.0.1",
      "externalReferences": [
        {
          "type": "website",
          "url": "https://github.com/CERN/lhc-vdm-editor#readme"
        },
        {
          "type": "issue-tracker",
          "url": "https://github.com/CERN/lhc-vdm-editor/issues"
        },
        {
          "type": "vcs",
          "url": "git+https://github.com/CERN/lhc-vdm-editor.git"
        }
      ]
    }
    }
}"""

        String testStringHeader = """<metadata>
  <timestamp>2020-08-03T01:28:52.765Z</timestamp>
  <tools>
    <tool>
      <vendor>CycloneDX</vendor>
      <name>Node.js module</name>
      <version>2.0.0</version>
    </tool>
  </tools>
  <component type='library' bom-ref='pkg:npm/lhc-vdm-editor@0.0.1'>
    <name>lhc-vdm-editor</name>
    <version>0.0.1</version>
    <description>A code and file editor for VdM scans.</description>
    <licenses>
      <license>
        <id>Apache-2.0</id>
      </license>
    </licenses>
    <purl>pkg:npm/lhc-vdm-editor@0.0.1</purl>
    <externalReferences>
      <reference type='website'>
        <url>https://github.com/CERN/lhc-vdm-editor#readme</url>
      </reference>
      <reference type='issue-tracker'>
        <url>https://github.com/CERN/lhc-vdm-editor/issues</url>
      </reference>
      <reference type='vcs'>
        <url>git+https://github.com/CERN/lhc-vdm-editor.git</url>
      </reference>
    </externalReferences>
  </component>
</metadata>"""

        def json = new JsonSlurper().parseText(jsonTestString)
        StringWriter sw = new StringWriter()
        def mb = new MarkupBuilder(sw)
        def xmlTest = new XMLConverter()
        xmlTest.header(mb, json)
        assertEquals(testStringHeader, sw.toString())
    }

    @Test
    void testDocComponent() {

        String jsonWithNoHash = """{
  "type": "library",
  "bom-ref": "pkg:npm/lhc-vdm-editor@0.0.1",
  "name": "lhc-vdm-editor",
  "version": "0.0.1",
  "description": "A code and file editor for VdM scans.",
  "licenses": [
    {
      "license": {
        "id": "Apache-2.0"
      }
    }
  ],
  "purl": "pkg:npm/lhc-vdm-editor@0.0.1",
  "externalReferences": [
    {
      "type": "website",
      "url": "https://github.com/CERN/lhc-vdm-editor#readme"
    },
    {
      "type": "issue-tracker",
      "url": "https://github.com/CERN/lhc-vdm-editor/issues"
    },
    {
      "type": "vcs",
      "url": "git+https://github.com/CERN/lhc-vdm-editor.git"
    }
  ]
}"""

        String testStringComponentNoHash = """<component type='library' bom-ref='pkg:npm/lhc-vdm-editor@0.0.1'>
  <name>lhc-vdm-editor</name>
  <version>0.0.1</version>
  <description>A code and file editor for VdM scans.</description>
  <licenses>
    <license>
      <id>Apache-2.0</id>
    </license>
  </licenses>
  <purl>pkg:npm/lhc-vdm-editor@0.0.1</purl>
  <externalReferences>
    <reference type='website'>
      <url>https://github.com/CERN/lhc-vdm-editor#readme</url>
    </reference>
    <reference type='issue-tracker'>
      <url>https://github.com/CERN/lhc-vdm-editor/issues</url>
    </reference>
    <reference type='vcs'>
      <url>git+https://github.com/CERN/lhc-vdm-editor.git</url>
    </reference>
  </externalReferences>
</component>"""

        def json = new JsonSlurper().parseText(jsonWithNoHash)
        StringWriter sw = new StringWriter()
        def mb = new MarkupBuilder(sw)
        def xmlTest = new XMLConverter()
        xmlTest.docComponent(mb, json)
        assertEquals(testStringComponentNoHash, sw.toString())

        String jsonWithHash = """{
  "type": "library",
  "bom-ref": "pkg:npm/puppeteer@1.19.0",
  "name": "puppeteer",
  "version": "1.19.0",
  "description": "A high-level API to control headless Chrome over the DevTools Protocol",
  "hashes": [
    {
      "alg": "SHA-512",
      "content": "d92e84eb2829a2a70409a6a00db068a6848e3c3bf4a4066f4db9c181463eea1ab4fd70c50ce2c430d94717f48a265cdc699f5c9222a3283bae79623714dfd65f"
    }
  ],
  "licenses": [
    {
      "license": {
        "id": "Apache-2.0"
      }
    }
  ],
  "purl": "pkg:npm/puppeteer@1.19.0",
  "externalReferences": [
    {
      "type": "website",
      "url": "https://github.com/GoogleChrome/puppeteer#readme"
    },
    {
      "type": "issue-tracker",
      "url": "https://github.com/GoogleChrome/puppeteer/issues"
    },
    {
      "type": "vcs",
      "url": "git+https://github.com/GoogleChrome/puppeteer.git"
    }
  ]
},
{
  "type": "library",
  "bom-ref": "pkg:npm/debug@4.1.1",
  "name": "debug",
  "version": "4.1.1",
  "description": "small debugging utility",
  "hashes": [
    {
      "alg": "SHA-512",
      "content": "a58008cde468f09e8a3c4689d1558e8793f391bc3f45eb6ecde84633b411457e617b87cf1f1dab74a301db9e9e8490a45fe5d1426d7a7992ea2cd4bc45265767"
    }
  ],
  "licenses": [
    {
      "license": {
        "id": "MIT"
      }
    }
  ],
  "purl": "pkg:npm/debug@4.1.1",
  "externalReferences": [
    {
      "type": "website",
      "url": "https://github.com/visionmedia/debug#readme"
    },
    {
      "type": "issue-tracker",
      "url": "https://github.com/visionmedia/debug/issues"
    },
    {
      "type": "vcs",
      "url": "git://github.com/visionmedia/debug.git"
    }
  ]
}"""

        String testStringComponentWithHash = """<component type='library' bom-ref='pkg:npm/puppeteer@1.19.0'>
  <name>puppeteer</name>
  <version>1.19.0</version>
  <description>A high-level API to control headless Chrome over the DevTools Protocol</description>
  <hashes>
    <hash alg='SHA-512'>d92e84eb2829a2a70409a6a00db068a6848e3c3bf4a4066f4db9c181463eea1ab4fd70c50ce2c430d94717f48a265cdc699f5c9222a3283bae79623714dfd65f</hash>
  </hashes>
  <licenses>
    <license>
      <id>Apache-2.0</id>
    </license>
  </licenses>
  <purl>pkg:npm/puppeteer@1.19.0</purl>
  <externalReferences>
    <reference type='website'>
      <url>https://github.com/GoogleChrome/puppeteer#readme</url>
    </reference>
    <reference type='issue-tracker'>
      <url>https://github.com/GoogleChrome/puppeteer/issues</url>
    </reference>
    <reference type='vcs'>
      <url>git+https://github.com/GoogleChrome/puppeteer.git</url>
    </reference>
  </externalReferences>
</component>"""

        json = new JsonSlurper().parseText(jsonWithHash)
        StringWriter sw1 = new StringWriter()
        def mb1 = new MarkupBuilder(sw1)
        def xmlTest1 = new XMLConverter()
        xmlTest1.docComponent(mb1, json)
        assertEquals(testStringComponentWithHash, sw1.toString())
    }
}
