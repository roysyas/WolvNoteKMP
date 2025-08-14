package com.roys.wolvnotekmp.common

import androidx.compose.ui.unit.IntSize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

object IntSizeSerializer : KSerializer<IntSize> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("IntSize") {
        element<Int>("width")
        element<Int>("height")
    }

    override fun serialize(encoder: Encoder, value: IntSize) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeIntElement(descriptor, 0, value.width)
        composite.encodeIntElement(descriptor, 1, value.height)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): IntSize {
        val dec = decoder.beginStructure(descriptor)
        var width = 0
        var height = 0

        loop@ while (true) {
            when (val index = dec.decodeElementIndex(descriptor)) {
                0 -> width = dec.decodeIntElement(descriptor, 0)
                1 -> height = dec.decodeIntElement(descriptor, 1)
                CompositeDecoder.DECODE_DONE -> break@loop
                else -> throw SerializationException("Unexpected index: $index")
            }
        }

        dec.endStructure(descriptor)
        return IntSize(width, height)
    }
}