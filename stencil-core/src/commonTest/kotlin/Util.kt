fun assertExpected(actual: String, expected: String) {
    assert(actual == expected) {
        "Expected: \"$expected\" but got \"${actual}\""
    }
}