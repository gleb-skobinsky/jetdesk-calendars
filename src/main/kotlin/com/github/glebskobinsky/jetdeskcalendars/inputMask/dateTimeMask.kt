package com.github.glebskobinsky.jetdeskcalendars.inputMask

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTimeMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateTimeMaskFilter(text)
    }
}


fun dateTimeMaskFilter(text: AnnotatedString): TransformedText {
    // dd.mm.yyyy --:--
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        when (i) {
            1 -> out += "." // 1
            3 -> out += "." // 2
            7 -> out += " " // 5
            9 -> out += ":" // 6
        }
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 7) return offset + 2
            if (offset <= 9) return offset + 3
            if (offset <= 11) return offset + 4
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            if (offset <= 13) return offset - 3
            if (offset <= 15) return offset - 4
            return 11
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}