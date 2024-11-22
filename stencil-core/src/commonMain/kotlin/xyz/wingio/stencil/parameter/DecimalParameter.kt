package xyz.wingio.stencil.parameter

import java.util.Locale

/**
 * Formats the provided [number][value]
 *
 * @param name The name of this parameter (case sensitive)
 * @param value The value to format
 */
// TODO: Add methods for setting things like decimal precision or digit grouping
public class DecimalParameter(
    override val name: String,
    override val value: Number
): Parameter<Number> {

    override fun format(locale: Locale): String {
        return value.toLong().toString()
    }

}