import org.junit.Test
import xyz.wingio.stencil.eq
import xyz.wingio.stencil.str
import xyz.wingio.stencil.fmt

private const val targetString = "Hello, my name is Stencil."
private const val testString = "Hello, my name is {{ name }}."

private const val targetStringDupe = "Stencil: My name is Stencil."
private const val testStringDupe = "{{ name }}: My name is {{ name }}."

class SingleParamTests {

    // ============================ Main 'str' API ==============================

    @Test
    fun `'str' - Single parameter (DSL)`() {
        val formattedString = str(testString) {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'str' - Single parameter (VarArg)`() {
        val formattedString = str(testString, "name" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'str' - Duplicate parameter (DSL)`() {
        val formattedString = str(testStringDupe) {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetStringDupe)
    }

    // ==========================================================================



    // ========================= Extension function API =========================

    @Test
    fun `'fmt' - Single parameter (DSL)`() {
        val formattedString = "Hello, my name is {{ name }}.".fmt {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'fmt' - Single parameter (VarArg)`() {
        val formattedString = testString.fmt("name" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'fmt' - Duplicate parameter (DSL)`() {
        val formattedString = testStringDupe.fmt {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetStringDupe)
    }


    // ========================= Companion function API =========================

    @Test
    fun `'String#fmt' - Single parameter (DSL)`() {
        val formattedString = String.fmt(testString) {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'String#fmt' - Single parameter (VarArg)`() {
        val formattedString = String.fmt(testString, "name" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'String#fmt' - Duplicate parameter (DSL)`() {
        val formattedString = String.fmt(testStringDupe) {
            "name" eq "Stencil"
        }

        assertExpected(formattedString, targetStringDupe)
    }

    // ==========================================================================

}