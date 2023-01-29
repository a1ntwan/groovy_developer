import groovy.xml.MarkupBuilder

class HTMLConverter {

    static void convertToHTML(json) {
        FileWriter fw = new FileWriter("homework.html")
        def mb = new MarkupBuilder(fw)
        mb.setDoubleQuotes(true)
        mb.setOmitEmptyAttributes(true)
        mb.html {
            mb.head {
                title("${json.bomFormat}: ${json.specVersion}")
            }
            mb.body {
                header(mb, json)
                component(mb, json.metadata.component)
                body(mb, json)
            }
        }
    }

    static MarkupBuilder header(mb, json) {
        mb.h1(json.bomFormat)
        mb.h2(json.specVersion)
        mb.h2(json.serialNumber)
        mb.h2(json.version)
        mb.ul(id: "tools") {
            json.metadata.tools.each { t ->
                li { h3(t.vendor) }
                li { h3(t.name) }
                li { h3(t.version) }
            }
        }
        mb.p(json.metadata.timestamp)
        return mb
    }

    static MarkupBuilder body(mb, json) {

        mb.div(id: "components") {
            json.components.each { c ->
                component(mb, c)
            }
        }
        return mb
    }

    static MarkupBuilder component(mb, c) {
        mb.div(id: c."bom-ref") {
            h4(c."bom-ref")
            p(c.type)
            p(c.name)
            p(c.version)
            p(c.description)
            if (c.hashes != null) {
                ul {
                    c.hashes.each { h ->
                        li(h.alg)
                        li(h.content)
                    }
                }
            }
            ul {
                c.licenses.each { l ->
                    li(l.license.id)
                }
            }
            p(c.purl)
            ul {
                c.externalReferences.each { e ->
                    li {
                        a(href: e.url) {
                            span(e.type)
                        }
                    }
                }
            }
        }
        return mb
    }
}

