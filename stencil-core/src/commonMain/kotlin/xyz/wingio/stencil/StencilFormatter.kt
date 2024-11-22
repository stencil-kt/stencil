package xyz.wingio.stencil

import xyz.wingio.stencil.parameter.ParametersHolder
import java.io.Closeable
import java.io.Flushable
import java.io.IOException
import java.util.Locale

/**
 * Formats strings that use Stencils named parameter syntax
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets.
 *
 * Eg. {{ name }}
 *
 * Ex.
 * ```kt
 * val formatter = StencilFormatter()
 * val parameters = ParametersHolder.fromList("name" eq "John")
 *
 * formatter.format("Hello, {{ name }}!", parameters)
 * println(formatter.toString()) // "Hello, John!"
 * ```
 *
 * It is generally recommended to use the [str] top-level functions
 * or the [String.fmt][xyz.wingio.stencil.fmt] extension function
 *
 * @see str
 * @see fmt
 *
 * @param appendable Destination for the formatted output
 */
public class StencilFormatter internal constructor(
    private var appendable: Appendable?,
    private val locale: Locale = Locale.getDefault(Locale.Category.FORMAT)
): Closeable, Flushable {

    /**
     * Creates an instance of [StencilFormatter] that appends to a StringBuilder.
     */
    public constructor(): this(appendable = StringBuilder())

    /**
     * Creates an instance of [StencilFormatter] that appends to a StringBuilder
     * with the desired [locale].
     */
    public constructor(locale: Locale): this(appendable = StringBuilder(), locale)

    /**
     * Creates an instance of [StencilFormatter] that appends to a [stringBuilder]
     * with the desired [locale].
     */
    public constructor(
        stringBuilder: StringBuilder,
        locale: Locale = Locale.getDefault(Locale.Category.FORMAT)
    ): this(appendable = stringBuilder, locale)

    /**
     * The last error encountered writing to the output
     */
    public var lastError: IOException? = null
        private set

    /**
     * Returns the formatted output as a string
     */
    override fun toString(): String {
        ensureOpen()
        return appendable.toString()
    }

    /**
     * Formats strings that use Stencils named parameter syntax
     *
     * Parameters are defined by enclosing an alphanumeric sequence
     * within double brackets.
     *
     * Eg. {{ name }}
     *
     * It is generally recommended to use the [str] top-level functions
     * or the [String.format][format] extension function
     *
     * @param format The string to format
     * @param parameters Collection of parameter values used to format the string
     *
     * @throws MissingParameterException When a parameter without a provided value is encountered
     * @throws ParseException When the formatter is unable to parse the formatted string
     */
    public fun format(format: String, parameters: ParametersHolder): StencilFormatter {
        return format(locale, format, parameters)
    }

    /**
     * Formats strings that use Stencils named parameter syntax
     *
     * Parameters are defined by enclosing an alphanumeric sequence
     * within double brackets. The only symbols allowed in the name are _, -, and .
     *
     * Eg. {{ name }}
     *
     * It is generally recommended to use the [str] top-level functions
     * or the [String.format][format] extension function
     *
     * @param locale The locale to use when formatting certain parameters
     * @param format The string to format
     * @param parameters Collection of parameter values used to format the string
     *
     * @throws MissingParameterException When a parameter without a provided value is encountered
     * @throws ParseException When the formatter is unable to parse the formatted string
     */
    public fun format(locale: Locale, format: String, parameters: ParametersHolder): StencilFormatter {
        val parsed = parse(format)

        for (i in parsed.indices) {
            parsed[i].print(parameters, locale)
        }

        return this
    }

    /**
     * Parses the input string in order to find named parameter.
     *
     * @return List of segments, representing either plain text or a parameter
     */
    private fun parse(str: String): List<Segment> {
        val segments = mutableListOf<Segment>()
        var cursor = 0
        val max = str.length

        while (cursor < max) {
            val nextParamIdx = str.indexOf(PARAM_START, cursor)

            if (nextParamIdx < 0) {
                // No more parameters found
                segments += StringSegment(str, cursor, max)
                break
            }

            if (cursor != nextParamIdx) {
                // Everything before the next parameter brackets is plain text
                segments += StringSegment(str, cursor, nextParamIdx)
            }

            cursor = nextParamIdx + PARAM_START.length // Move the cursor to the end of the opening brackets

            if (cursor >= max) throw ParseException("Encountered trailing opening brackets", str)

            val closingIdx = str.indexOf(PARAM_END, cursor)
            if (closingIdx < 0) throw ParseException("Encountered opening brackets ($PARAM_START) without corresponding closing brackets ($PARAM_END)", str)

            val res = syntaxRegex.find(str, nextParamIdx)

            if (res != null && res.range.first == nextParamIdx) {
                segments += ParameterSegment(res)
                cursor = res.range.last + 1 // Move cursor to the end of the parameter before the next loop
            } else {
                throw ParseException("Parameter located at index $nextParamIdx is invalid", str)
            }
        }

        return segments
    }

    private fun ensureOpen() {
        if (appendable == null) throw StencilFormatterClosedException()
    }

    override fun close() {
        if (appendable == null) return

        try {
            (appendable as? Closeable)?.close()
        } catch (e: IOException) {
            lastError = e
        } finally {
            appendable = null
        }
    }

    override fun flush() {
        ensureOpen()
        try {
            (appendable as? Flushable)?.flush()
        } catch (e: IOException) {
            lastError = e
        }
    }

    internal companion object {

        // {{ <name> }}
        private val syntaxRegex = "\\{\\{\\s*(?<paramName>[A-z_\\-.\\d]+)\\s*}}".toRegex(option = RegexOption.MULTILINE)
        private const val PARAM_START = "{{"
        private const val PARAM_END = "}}"

    }

    private interface Segment {

        fun print(parameters: ParametersHolder, locale: Locale)

        override fun toString(): String

    }

    private inner class StringSegment(
        private val str: String,
        private val start: Int,
        private val end: Int
    ): Segment {

        override fun print(parameters: ParametersHolder, locale: Locale) {
            appendable!!.append(str, start, end)
        }

        override fun toString(): String {
            return str.subSequence(start, end).toString()
        }

    }

    private inner class ParameterSegment(
        match: MatchResult
    ): Segment {

        val name = match.groups["paramName"]?.value ?: throw ParseException("Parameter name could not be found", "")

        override fun print(parameters: ParametersHolder, locale: Locale) {
            appendable!!.append(parameters[name]?.format(locale) ?: throw MissingParameterException(name))
        }

        override fun toString(): String {
            return "{{ $name }}"
        }

    }

}