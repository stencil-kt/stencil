import org.junit.Test
import xyz.wingio.stencil.ParseException
import xyz.wingio.stencil.eq
import xyz.wingio.stencil.fmt

/**
 * These tests are meant to validate the parsing logic
 * and ensure edge cases are covered.
 *
 * More tests should be added here as issues get brought up.
 */
class InputTests {

    @Test
    fun `Invalid name - Whitespace`() {
        val res = runCatching {
            "some idiot named {{ some name }} was here".fmt("some name" eq "name")
        }

        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Invalid name - Symbol`() {
        val res = runCatching {
            "{{ someName+ }}".fmt("someName+" eq "name")
        }

        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        // Make sure we're getting the right ParseException
        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Trailing brackets`() {
        val res = runCatching {
            "{{ someName }} {{".fmt("someName" eq "name")
        }

        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        // Make sure we're getting the right ParseException
        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Missing closing brackets`() {
        val res = runCatching {
            "{{ someName ".fmt("someName+" eq "name")
        }

        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        // Make sure we're getting the right ParseException
        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Nesting brackets`() {
        val res = runCatching {
            "{{ someName {{ other }} }}".fmt("someName+" eq "name")
        }

        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        // Make sure we're getting the right ParseException
        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Extra bracket at start`() {
        val res = runCatching {
            "{{{ someName }}".fmt("someName" eq "John")
        }
        if (res.isFailure) { println(res.exceptionOrNull()?.message) }

        // Make sure we're getting the right ParseException
        assert(res.isFailure && res.exceptionOrNull()!! is ParseException)
    }

    @Test
    fun `Extra bracket at end`() {
        val res = "{{ someName }}}".fmt("someName" eq "John")

        assertExpected(res, "John}")
    }

    @Test
    fun `Multiline parameter`() {
        val res = """
            {{
                someName
            }} hi
        """.trimIndent().fmt("someName" eq "John")

        assertExpected(res, "John hi")
    }


}