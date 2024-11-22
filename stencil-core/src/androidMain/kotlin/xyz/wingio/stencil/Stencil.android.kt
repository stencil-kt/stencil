package xyz.wingio.stencil

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import xyz.wingio.stencil.parameter.Parameter
import xyz.wingio.stencil.parameter.ParametersHolder

/**
 * Retrieve a string and format it with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * Eg. {{ name }}
 *
 * ```kt
 * context.str(R.string.greet) {
 *     "name" eq "John"
 * }
 * ```
 *
 * @param resId The string resource id
 * @param formatArgs Arguments to pass through to [Context.getString]
 * @param paramBuilder DSL for providing named parameter values
 *
 * @return The formatted string
 */
public fun Context.str(
    @StringRes resId: Int,
    vararg formatArgs: Any,
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String {
    return str(getString(resId, *formatArgs), paramBuilder = paramBuilder)
}

/**
 * Retrieve a string and format it with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * Eg. {{ name }}
 *
 * ```kt
 * resources.str(R.string.greet) {
 *     "name" eq "John"
 * }
 * ```
 *
 * @param resId The string resource id
 * @param formatArgs Arguments to pass through to [Resources.getString]
 * @param paramBuilder DSL for providing named parameter values
 *
 * @return The formatted string
 */
public fun Resources.str(
    @StringRes resId: Int,
    vararg formatArgs: Any,
    paramBuilder: (ParametersHolder.() -> Unit)? = null
): String {
    return str(getString(resId, *formatArgs), paramBuilder = paramBuilder)
}

/**
 * Retrieve a string and format it with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * Eg. {{ name }}
 *
 * ```kt
 * context.str(R.string.greet, "name" eq "John")
 * ```
 *
 * @param resId The string resource id
 * @param parameters Named parameter values, see [eq]
 *
 * @return The formatted string
 */
public fun Context.str(
    @StringRes resId: Int,
    vararg parameters: Parameter<*>,
): String {
    return str(getString(resId), *parameters)
}

/**
 * Retrieve a string and format it with Stencil.
 *
 * Parameters are defined by enclosing an alphanumeric sequence
 * within double brackets. The only symbols allowed in the name are _, -, and .
 *
 * Eg. {{ name }}
 *
 * ```kt
 * resources.str(R.string.greet, "name" eq "John")
 * ```
 *
 * @param resId The string resource id
 * @param parameters Named parameter values, see [eq]
 *
 * @return The formatted string
 */
public fun Resources.str(
    @StringRes resId: Int,
    vararg parameters: Parameter<*>,
): String {
    return str(getString(resId), *parameters)
}