package xyz.wingio.stencil.parameter

import java.util.Locale

/**
 * Formats the provided [character][value]
 *
 * @param name The name of this parameter (case sensitive)
 * @param value The value to format
 * @param uppercase Whether or not this character should be capitalized
 */
public class CharacterParameter(
    override val name: String,
    override val value: Char,
    uppercase: Boolean = false
): Parameter<Char> {

    /**
     * Whether or not this [character][value] should be capitalized
     */
    public var uppercase: Boolean = uppercase
        private set

    override fun format(locale: Locale): String {
        println(uppercase)
        return value.run { if (uppercase) uppercase(locale) else this }.toString()
    }

    /**
     * Forces the output to be in all caps.
     *
     * @return The modified parameter, for chaining
     */
    public fun uppercase(uppercase: Boolean = true): CharacterParameter {
        this.uppercase = uppercase
        return this
    }

}

/**
 * Supplies a parameter with the given [name] to the [value] represented as a Unicode character.
 *
 * Ex.
 * ```kt
 * val char = str("{{ char }}") {
 *     char("char", 'a')
 * }
 *
 * println(char) // "a"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.char(name: String, value: Char): CharacterParameter {
    return param(CharacterParameter(name, value)) as CharacterParameter
}

/**
 * Supplies a parameter with the given [name] to the [value] represented as a Unicode character.
 *
 * Ex.
 * ```kt
 * val char = str("{{ char }}") {
 *     char("char", 97)
 * }
 *
 * println(char) // "a"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.char(name: String, value: Int): CharacterParameter {
    return char(name, Char(value))
}

/**
 * Supplies a parameter with the given [name] to the [value] represented as a Unicode character.
 *
 * Ex.
 * ```kt
 * val char = str("{{ char }}") {
 *     char("char", 97.toUShort())
 * }
 *
 * println(char) // "a"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.char(name: String, value: UShort): CharacterParameter {
    return char(name, Char(value))
}