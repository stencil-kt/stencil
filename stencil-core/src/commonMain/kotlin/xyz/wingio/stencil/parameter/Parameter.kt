package xyz.wingio.stencil.parameter

import java.util.Locale

/**
 * A parameter to be supplied to a Stencil-formatted string
 */
public interface Parameter<T> {

    public val name: String
    public val value: T

    /**
     * Formats the value held by this parameter into a [String]
     * according to the optionally provided [locale]
     *
     * @param locale Locale used to assist with language-specific formatting
     */
    public fun format(locale: Locale = Locale.getDefault(Locale.Category.FORMAT)): String

}

/**
 * Formats the provided [value] by calling its `toString` method
 *
 * @param name The name of this parameter
 * @param value The value to format
 * @param uppercase Whether or not the value of this parameter will be formatted in all caps
 */
public class GenericParameter<T>(
    override val name: String,
    override val value: T,
    uppercase: Boolean = false
): Parameter<T> {

    /**
     * Whether or not the value of this parameter will be formatted in all caps
     */
    public var uppercase: Boolean = uppercase
        private set

    override fun format(locale: Locale): String {
        return value.toString().run { if (uppercase) uppercase(locale) else this }
    }

    /**
     * Forces the output to be in all caps.
     *
     * @return The modified parameter, for chaining
     */
    public fun uppercase(uppercase: Boolean = true): GenericParameter<T> {
        this.uppercase = uppercase
        return this
    }

}

