package xyz.wingio.stencil.parameter

import java.util.Locale

/**
 * Stores parameters used within a Stencil formatted string
 *
 * The recommended method to supply parameters is with the [str][xyz.wingio.stencil.str] DSL.
 * You can also use our custom [format]
 */
public class ParametersHolder {

    /**
     * All supplied parameters
     */
    private val parameters: MutableMap<String, Parameter<*>> = mutableMapOf()

    /**
     * Retrieves a parameter with the desired [name], if present
     */
    public operator fun get(name: String): Parameter<*>? = parameters[name]

    /**
     * Supplies the given parameter, can serve the same purpose as [custom]
     * but allow you to use your own class (e.g. to add configuration)
     *
     * @see [custom]
     * @see [Parameter]
     */
    public fun <T> param(parameter: Parameter<T>): Parameter<T> {
        parameters[parameter.name] = parameter
        return parameter
    }

    /**
     * Assigns this string to a parameter with the desired [value].
     * The parameter is represented by its `toString` method.
     *
     * Ex.
     * ```kt
     * val greeting = str("Hi {{ name }}") {
     *     "name" eq "John"
     * }
     *
     * println(greeting) // "Hi John"
     * ```
     *
     * @param value The value to assign to this parameter
     */
    public infix fun <T> String.eq(value: T): GenericParameter<T> {
        return param(GenericParameter(this, value)) as GenericParameter<T>
    }

    /**
     * Supplies a parameter with the given [name] to the [value].
     *
     * Ex.
     * ```kt
     * val greeting = str("Hi {{ name }}") {
     *     string("name", "John")
     * }
     *
     * println(greeting) // "Hi John"
     * ```
     *
     * @param name The name of the parameter
     * @param value The value to assign to this parameter
     * @param uppercase (Optional) Whether or not the [value] should be made all uppercase
     */
    public fun string(name: String, value: String, uppercase: Boolean = false) {
        param(StringParameter(name, value, uppercase))
    }

    /**
     * Supplies a parameter with the given [name] to the [value].
     * The parameter is represented as a whole decimal number.
     *
     * Ex.
     * ```kt
     * val folCount = str("You have {{ followerCount }} followers") {
     *     decimal("followerCount", 1234)
     * }
     *
     * println(folCount) // "You have 1234 followers"
     * ```
     *
     * @param name The name of the parameter
     * @param value The value to assign to this parameter
     */
    public fun decimal(name: String, value: Number) {
        param(DecimalParameter(name, value))
    }

    /**
     * Supplies a parameter with the given [name] to the [value].
     * The parameter is represented with the result of [format].
     *
     * This function is largely designed to make creating custom parameter functions
     * easier.
     *
     * Ex.
     * ```kt
     * fun ParametersHolder.social(name: String, value: Int)
     *     = custom(name, value) { value, locale ->
     *           formatSocial(value) // Only an example
     *       }
     *
     * val folCount = str("You have {{ followerCount }} followers") {
     *     social("followerCount", 1234)
     * }
     *
     * println(folCount) // "You have 1.2K followers"
     * ```
     *
     * @param name The name of the parameter
     * @param value The value to assign to this parameter
     */
    public fun <T> custom(name: String, value: T, format: (T, Locale) -> String) {
        param(object : Parameter<T> {
            override val name: String = name
            override val value: T = value

            override fun format(locale: Locale): String {
                return format(value, locale)
            }
        })
    }

    public companion object {

        /**
         * Creates an instance of [ParametersHolder] from a list of parameters
         */
        public fun fromList(params: List<Parameter<*>>): ParametersHolder {
            return ParametersHolder().apply {
                params.forEach {
                    param(it)
                }
            }
        }

    }

}