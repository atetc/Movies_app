package io.github.atetc.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseInteractor<INPUT, OUTPUT>(private val dispatcher: CoroutineDispatcher) :
    Interactor<INPUT, OUTPUT> {

    override suspend fun execute(input: INPUT) =
        withContext(dispatcher) {
            action(input)
        }

    abstract suspend fun action(input: INPUT): OUTPUT
}