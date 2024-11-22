package xyz.wingio.stencil

/**
 * An exception thrown by Stencil while parsing or formatting
 */
public open class StencilException(override val message: String?): Exception()

/**
 * Thrown when a parameter was specified in the string but no value
 * was provided at runtime.
 *
 * @param missingParamName The name of the missing parameter
 */
public class MissingParameterException(
    public val missingParamName: String
): StencilException("Parameter $missingParamName was required but no value was provided")

/**
 * An exception thrown while parsing a formatted string
 *
 * @param message A message describing the error that occurred
 * @param input The original string that was being parsed
 */
public class ParseException(
    override val message: String?,
    public val input: String
): StencilException(
    buildString {
        appendLine(message)
        if (input.isNotBlank()) {
            appendLine()
            append("Input: ")
            append(input)
        }
    }
)

/**
 * An exception thrown when attempting to use a [StencilFormatter]
 * after its closed
 */
public class StencilFormatterClosedException(): StencilException("This StencilFormatter instance was closed")