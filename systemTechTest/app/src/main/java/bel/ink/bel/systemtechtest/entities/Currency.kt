package bel.ink.bel.systemtechtest.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "Currency", strict = false)
data class Currency @JvmOverloads constructor(


    @field:Attribute(name = "Id", required = false)
    @param:Attribute(name = "Id", required = false)
    var id: Int = 0,

    @field:Element(name = "NumCode", required = false)
    @param:Element(name = "NumCode", required = false)
    var nubCode: String? = null,

    @field:Element(name = "CharCode", required = false)
    @param:Element(name = "CharCode", required = false)
    var charCode: String? = null,

    @field:Element(name = "Scale", required = false)
    @param:Element(name = "Scale", required = false)
    var scale: Int? = null,

    @field:Element(name = "Name", required = false)
    @param:Element(name = "Name", required = false)
    var name: String? = null,

    @field:Element(name = "Rate", required = false)
    @param:Element(name = "Rate", required = false)
    var rate: Double? = null
)
