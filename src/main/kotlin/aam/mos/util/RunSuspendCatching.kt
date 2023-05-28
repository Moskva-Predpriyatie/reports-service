package aam.mos.util

import kotlin.coroutines.cancellation.CancellationException

inline fun <R> runSuspendCatching(block: () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
