package io.github.atetc.domain.mappers

interface Mapper<INPUT, OUTPUT> {
    fun map(input: INPUT): OUTPUT
}
