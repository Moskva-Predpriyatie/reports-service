package aam.mos.util

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.Transaction
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <R> JedisPool.withJedis(block: (Jedis) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return resource.use(block)
}

@OptIn(ExperimentalContracts::class)
inline fun Jedis.multi(transaction: Transaction.() -> Unit) {
    contract {
        callsInPlace(transaction, InvocationKind.EXACTLY_ONCE)
    }
    multi().apply(transaction).exec()
}
