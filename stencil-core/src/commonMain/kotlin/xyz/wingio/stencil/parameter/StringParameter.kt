package xyz.wingio.stencil.parameter

import java.util.Locale

/**
 * Formats the provided [string][value]
 *
 * @param name The name of this parameter (case sensitive)
 * @param value The value to format
 * @param uppercase Whether or not the string will be formatted in all caps
 */
public class StringParameter(
    override val name: String,
    override val value: String,
    uppercase: Boolean = false
): Parameter<String> {

    /**
     * Whether or not the value of this parameter will be formatted in all caps
     */
    public var uppercase: Boolean = uppercase
        private set

    override fun format(locale: Locale): String {
        return value.run { if (uppercase) uppercase(locale) else this }
    }

    /**
     * Forces the output to be in all caps.
     * Identical to using `string("name", "John".uppercase())`
     *
     * @return The modified parameter, for chaining
     */
    // Not technically needed for strings but kept to make switching parameter types less annoying
    // also may look nicer to some people
    public fun uppercase(uppercase: Boolean = true): StringParameter {
        this.uppercase = uppercase
        return this
    }

}