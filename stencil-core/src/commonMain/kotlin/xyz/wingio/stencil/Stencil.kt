package xyz.wingio.stencil

import xyz.wingio.stencil.parameter.GenericParameter
import xyz.wingio.stencil.parameter.Parameter
import xyz.wingio.stencil.parameter.ParametersHolder
import java.util.Locale

/**
 * Format a [string] with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * str("{{ name }}", locale = Locale.ENGLISH) {
 *     "name" eq "John"
 * }
 * ```
 *
 * @param string The string to format
 * @param locale (Optional) The locale used to format certain parameters
 * @param paramBuilder (Optional) Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted [string]
 */
public fun str(
    string: String,
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String {
    if (paramBuilder == null) return string

    val params = ParametersHolder().apply(paramBuilder)

    return StencilFormatter(locale)
        .format(string, params)
        .toString()
}

/**
 * Format a [string] with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * str("{{ name }}", locale = Locale.ENGLISH, "name" eq "John")
 * ```
 *
 * @param string The string to format
 * @param locale (Optional) The locale used to format certain parameters
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted [string]
 */
public fun str(
    string: String,
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    vararg parameters: Parameter<*>
): String {
    if (parameters.isEmpty()) return string

    val params = ParametersHolder.fromList(parameters.toList())

    return StencilFormatter(locale)
        .format(string, params)
        .toString()
}

/**
 * Format a [string] with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * str("{{ name }}", "name" eq "John")
 * ```
 *
 * @param string The string to format
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted [string]
 */
public fun str(
    string: String,
    vararg parameters: Parameter<*>
): String {
    if (parameters.isEmpty()) return string

    val params = ParametersHolder.fromList(parameters.toList())

    return StencilFormatter()
        .format(string, params)
        .toString()
}

/**
 * Format this string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * "{{ name }}".fmt(locale = Locale.ENGLISH) {
 *     "name" eq "John"
 * }
 * ```
 *
 * @param locale (Optional) The locale used to format certain parameters
 * @param paramBuilder (Optional) Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.fmt(
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String
    = str(this, locale, paramBuilder = paramBuilder)

/**
 * Format this string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * "{{ name }}".fmt(locale = Locale.ENGLISH, "name" eq "John")
 * ```
 *
 * @param locale (Optional) The locale used to format certain parameters
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.fmt(
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    vararg parameters: Parameter<*>
): String
        = str(this, locale, *parameters)

/**
 * Format this string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * "{{ name }}".fmt("name" eq "John")
 * ```
 *
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.fmt(
    vararg parameters: Parameter<*>
): String
        = str(this, parameters = parameters)

/**
 * Format a string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * String.fmt(locale = Locale.ENGLISH, "{{ name }}") {
 *     "name" eq "John"
 * }
 * ```
 * @param locale (Optional) The locale used to format certain parameters
 * @param str The string to format
 * @param paramBuilder (Optional) Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.Companion.fmt(
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    str: String,
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String
        = str(str, locale, paramBuilder = paramBuilder)

/**
 * Format a string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * String.fmt("{{ name }}") {
 *     "name" eq "John"
 * }
 * ```
 *
 * @param str The string to format
 * @param paramBuilder (Optional) Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.Companion.fmt(
    str: String,
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String
        = str(str, paramBuilder = paramBuilder)

/**
 * Format a string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * String.fmt(locale = Locale.ENGLISH, "{{ name }}", "name" eq "John")
 * ```
 *
 * @param locale (Optional) The locale used to format certain parameters
 * @param str The string to format
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.Companion.fmt(
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT),
    str: String,
    vararg parameters: Parameter<*>
): String
    = str(str, locale, *parameters)

/**
 * Format a string with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * E.g. {{ name }}
 *
 * Ex.
 * ```kt
 * String.fmt("{{ name }}", "name" eq "John")
 * ```
 *
 * @param str The string to format
 * @param parameters Set the values for parameters defined in the string
 *
 * @throws MissingParameterException When a parameter without a provided value is encountered
 * @throws ParseException When the formatter is unable to parse the formatted string
 */
public fun String.Companion.fmt(
    str: String,
    vararg parameters: Parameter<*>
): String
        = str(str, parameters = parameters)

/**
 * Creates a parameter with this name set to [value]. Designed to be used
 * with the vararg variants of the [str] and [fmt] methods.
 *
 * Ex.
 * ```kt
 * "name" eq "John"
 * ```
 *
 * The parameter is represented by its `toString` method.
 */
public infix fun <T> String.eq(value: T): Parameter<T> = GenericParameter(this, value)