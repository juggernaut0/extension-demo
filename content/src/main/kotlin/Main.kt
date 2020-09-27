import kotlinx.browser.document
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.Range
import org.w3c.dom.events.MouseEvent

fun main() {
    document.body?.let { body ->
        body.addEventListener("mousedown", { e ->
            e as MouseEvent
            getWordAt(e.clientX, e.clientY)?.also { console.log(it) }
        })
    }
}

fun getWordAt(x: Int, y: Int): String? {
    val range = document.caretRangeFromPoint(x, y)
    val node = range.startContainer
    val offset = range.startOffset

    if (node.nodeType != Node.TEXT_NODE) {
        return null
    }

    val rect = document.createRange().let {
        it.selectNode(node)
        it.getBoundingClientRect()
    }
    if (x.toDouble() !in rect.left..rect.right || y.toDouble() !in rect.top..rect.bottom) {
        return null
    }

    val text = node.textContent!!

    var i = offset
    while (i > 0 && !text[i-1].isBoundary()) {
        i--
    }
    val start = i

    i = offset
    while (i < text.length && !text[i + 1].isBoundary()) {
        i++
    }
    val end = i + 1

    return text.substring(start, end).takeIf { it.isNotBlank() }
}

inline fun Document.caretRangeFromPoint(x: Int, y: Int): Range = asDynamic().caretRangeFromPoint(x, y).unsafeCast<Range>()

const val punct = "():;[]{}/\\,!@#$%^&*~`-_=+"
fun Char.isBoundary() = isWhitespace() || this in punct
