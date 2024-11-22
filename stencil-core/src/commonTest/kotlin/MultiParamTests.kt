import org.junit.Test
import xyz.wingio.stencil.eq
import xyz.wingio.stencil.fmt
import xyz.wingio.stencil.str

private const val targetString = "Hi John, my name is Stencil!"
private const val testString = "Hi {{ name }}, my name is {{ selfName }}!"

class MultiParamTests {

    // ============================ Main 'str' API ==============================

    @Test
    fun `'str' - Multiple parameters (DSL)`() {
        val formattedString = str(testString) {
            "name" eq "John"
            "selfName" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'str' - Multiple parameters (VarArg)`() {
        val formattedString = str(testString, "name" eq "John", "selfName" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    // ==========================================================================



    // ========================= Extension function API =========================

    @Test
    fun `'format' - Multiple parameters (DSL)`() {
        val formattedString = testString.fmt {
            "name" eq "John"
            "selfName" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'format' - Multiple parameters (VarArg)`() {
        val formattedString = testString.fmt("name" eq "John", "selfName" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    // ========================= Companion function API =========================

    @Test
    fun `'String#fmt' - Multiple parameter (DSL)`() {
        val formattedString = String.fmt(testString) {
            "name" eq "John"
            "selfName" eq "Stencil"
        }

        assertExpected(formattedString, targetString)
    }

    @Test
    fun `'String#fmt' - Multiple parameter (VarArg)`() {
        val formattedString = String.fmt(testString, "name" eq "John", "selfName" eq "Stencil")

        assertExpected(formattedString, targetString)
    }

    // ==========================================================================

}