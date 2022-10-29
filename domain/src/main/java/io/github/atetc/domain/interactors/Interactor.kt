package io.github.atetc.domain.interactors

interface Interactor<INPUT, OUTPUT> {
    suspend fun execute(input: INPUT): OUTPUT
}
