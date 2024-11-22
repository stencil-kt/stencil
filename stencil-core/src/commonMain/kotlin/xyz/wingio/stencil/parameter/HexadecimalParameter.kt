package xyz.wingio.stencil.parameter

import java.nio.ByteBuffer
import java.util.Locale

/**
 * A parameter that converts its [value] into a hexadecimal string.
 *
 * @param name The name of the parameter (case sensitive)
 * @param value The raw bytes to encode as a hexadecimal string
 */
public class HexadecimalParameter(
    override val name: String,
    override val value: ByteArray,
    uppercase: Boolean = false
): Parameter<ByteArray> {

    /**
     * Whether or not the value of this parameter will be formatted in all caps.
     */
    public var uppercase: Boolean = uppercase
        private set

    /**
     * Whether or not the hex string should be prefixed with `0x`.
     * Enabling this also forces the hex string to be entirely uppercased.
     */
    public var prefixed: Boolean = false
        private set

    @OptIn(ExperimentalStdlibApi::class)
    override fun format(locale: Locale): String {
        return buildString {
            if (prefixed) append("0x")
            append(value.toHexString(if (uppercase || prefixed) HexFormat.UpperCase else HexFormat.Default))
        }
    }

    /**
     * Forces the output to be in all caps.
     *
     * @return The modified parameter, for chaining
     */
    public fun uppercase(uppercase: Boolean = true): HexadecimalParameter {
        this.uppercase = uppercase
        return this
    }

    /**
     * Whether or not the hex string should be prefixed with `0x`.
     * Enabling this also forces the hex string to be entirely uppercased.
     *
     * @return The modified parameter, for chaining
     */
    public fun prefixed(prefixed: Boolean = true): HexadecimalParameter {
        this.prefixed = prefixed
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HexadecimalParameter

        if (name != other.name) return false
        if (!value.contentEquals(other.value)) return false
        if (uppercase != other.uppercase) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + value.contentHashCode()
        result = 31 * result + uppercase.hashCode()
        return result
    }

}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("21 in hex is {{ hex }}") {
 *     hex("hex", 21)
 * }
 *
 * println(hex) // "21 in hex is 00000015"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: Int): HexadecimalParameter {
    return hex(name, ByteBuffer.allocate(Int.SIZE_BYTES).putInt(value).array())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("21 in hex is {{ hex }}") {
 *     hex("hex", 21L)
 * }
 *
 * println(hex) // "21 in hex is 0000000000000015"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: Long): HexadecimalParameter {
    return hex(name, ByteBuffer.allocate(Long.SIZE_BYTES).putLong(value).array())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("21 in hex is {{ hex }}") {
 *     hex("hex", 21.toShort())
 * }
 *
 * println(hex) // "21 in hex is 0015"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: Short): HexadecimalParameter {
    return hex(name, ByteBuffer.allocate(Short.SIZE_BYTES).putShort(value).array())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("21 in hex is {{ hex }}") {
 *     hex("hex", 21f)
 * }
 *
 * println(hex) // "21 in hex is 41a80000"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: Float): HexadecimalParameter {
    return hex(name, ByteBuffer.allocate(Float.SIZE_BYTES).putFloat(value).array())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("21 in hex is {{ hex }}") {
 *     hex("hex", 21.0)
 * }
 *
 * println(hex) // "21 in hex is 4035000000000000"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: Double): HexadecimalParameter {
    return hex(name, ByteBuffer.allocate(Double.SIZE_BYTES).putDouble(value).array())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("'Hi' in hex is {{ hex }}") {
 *     hex("hex", "Hi")
 * }
 *
 * println(hex) // "21 in hex is 4869"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: String): HexadecimalParameter {
    return hex(name, value.toByteArray())
}

/**
 * Sets the parameter with the given [name] to the provided [value] represented as hexadecimal.
 *
 * Ex.
 * ```kt
 * val hex = str("One empty byte in hex is {{ hex }}") {
 *     hex("hex", ByteArray(1))
 * }
 *
 * println(hex) // "One empty byte in hex is 00"
 * ```
 *
 * @param name The name of the parameter
 * @param value The value to assign to this parameter
 */
public fun ParametersHolder.hex(name: String, value: ByteArray): HexadecimalParameter {
    return param(HexadecimalParameter(name, value)) as HexadecimalParameter
}