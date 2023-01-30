import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import org.junit.jupiter.api.Test


class HTMLConverterTest extends GroovyTestCase {

    @Test
    void testHeader() {

        String jsonTestString = """{
"bomFormat": "CycloneDX",
"specVersion": "1.2",
"serialNumber": "urn:uuid:699b6458-60da-4f52-b1b3-34915dc01eb6",
"version": 1,
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

        String testStringHeader = """<h1>CycloneDX</h1>
<h2>1.2</h2>
<h2>urn:uuid:699b6458-60da-4f52-b1b3-34915dc01eb6</h2>
<h2>1</h2>
<ul id='tools'>
  <li>
    <h3>CycloneDX</h3>
  </li>
  <li>
    <h3>Node.js module</h3>
  </li>
  <li>
    <h3>2.0.0</h3>
  </li>
</ul>
<p>2020-08-03T01:28:52.765Z</p>"""

        def json = new JsonSlurper().parseText(jsonTestString)
        StringWriter sw = new StringWriter()
        def mb = new MarkupBuilder(sw)
        def htmlTest = new HTMLConverter()
        htmlTest.header(mb, json)
        assertEquals(testStringHeader, sw.toString())
    }

    @Test
    void testComponent() {

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

        String testStringComponentNoHash = """<div id='pkg:npm/lhc-vdm-editor@0.0.1'>
  <h4>pkg:npm/lhc-vdm-editor@0.0.1</h4>
  <p>library</p>
  <p>lhc-vdm-editor</p>
  <p>0.0.1</p>
  <p>A code and file editor for VdM scans.</p>
  <ul>
    <li>Apache-2.0</li>
  </ul>
  <p>pkg:npm/lhc-vdm-editor@0.0.1</p>
  <ul>
    <li>
      <a href='https://github.com/CERN/lhc-vdm-editor#readme'>
        <span>website</span>
      </a>
    </li>
    <li>
      <a href='https://github.com/CERN/lhc-vdm-editor/issues'>
        <span>issue-tracker</span>
      </a>
    </li>
    <li>
      <a href='git+https://github.com/CERN/lhc-vdm-editor.git'>
        <span>vcs</span>
      </a>
    </li>
  </ul>
</div>"""


        def json = new JsonSlurper().parseText(jsonWithNoHash)
        StringWriter sw = new StringWriter()
        def mb = new MarkupBuilder(sw)
        def htmlTest = new HTMLConverter()
        htmlTest.component(mb, json)
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

        String testStringComponentWithHash = """<div id='pkg:npm/puppeteer@1.19.0'>
  <h4>pkg:npm/puppeteer@1.19.0</h4>
  <p>library</p>
  <p>puppeteer</p>
  <p>1.19.0</p>
  <p>A high-level API to control headless Chrome over the DevTools Protocol</p>
  <ul>
    <li>SHA-512</li>
    <li>d92e84eb2829a2a70409a6a00db068a6848e3c3bf4a4066f4db9c181463eea1ab4fd70c50ce2c430d94717f48a265cdc699f5c9222a3283bae79623714dfd65f</li>
  </ul>
  <ul>
    <li>Apache-2.0</li>
  </ul>
  <p>pkg:npm/puppeteer@1.19.0</p>
  <ul>
    <li>
      <a href='https://github.com/GoogleChrome/puppeteer#readme'>
        <span>website</span>
      </a>
    </li>
    <li>
      <a href='https://github.com/GoogleChrome/puppeteer/issues'>
        <span>issue-tracker</span>
      </a>
    </li>
    <li>
      <a href='git+https://github.com/GoogleChrome/puppeteer.git'>
        <span>vcs</span>
      </a>
    </li>
  </ul>
</div>"""

        json = new JsonSlurper().parseText(jsonWithHash)
        StringWriter sw1 = new StringWriter()
        def mb1 = new MarkupBuilder(sw1)
        def htmlTest1 = new HTMLConverter()
        htmlTest1.component(mb1, json)
        assertEquals(testStringComponentWithHash, sw1.toString())
    }
}
