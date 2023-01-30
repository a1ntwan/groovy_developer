import groovy.xml.MarkupBuilder


class XMLConverter {

    static void convertToXML(json) {
        FileWriter fw = new FileWriter("homework.xml")
        def mb = new MarkupBuilder(fw)
        mb.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        mb.setDoubleQuotes(true)
        mb.bom(xmlns: "http://cyclonedx.org/schema/bom/${json.specVersion}", serialNumber: json.serialNumber, version: json.version) {
            header(mb, json)
            components(mb, json)
        }
    }

    static MarkupBuilder header(mb, json) {

        mb.metadata {
            timestamp(json.metadata.timestamp)
            tools {
                json.metadata.tools.each { t ->
                    tool {
                        vendor(t.vendor)
                        name(t.name)
                        version(t.version)
                    }
                }
            }
            docComponent(mb, json.metadata.component)
        }
        return mb
    }

    static MarkupBuilder components(mb, json) {

        mb.components {
            json.components.each { c ->
                docComponent(mb, c)
            }
        }
        return mb
    }

    static MarkupBuilder docComponent(mb, c) {
        mb.component(type: c.type, "bom-ref": c."bom-ref") {
            name(c.name)
            version(c.version)
            description(c.description)
            if (c.hashes != null) {
                hashes {
                    c.hashes.each { h ->
                        hash(alg: h.alg, h.content)
                    }
                }
            }
            licenses {
                c.licenses.each { l ->
                    license {
                        id(l.license.id)
                    }
                }
            }
            purl(c.purl)
            externalReferences {
                c.externalReferences.each { e ->
                    reference(type: e.type) {
                        url(e.url)
                    }
                }
            }
        }
        return mb
    }
}

