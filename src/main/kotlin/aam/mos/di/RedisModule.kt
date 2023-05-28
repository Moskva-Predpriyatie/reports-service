package aam.mos.di

import aam.mos.di.util.configString
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.eagerSingleton
import redis.clients.jedis.JedisPool

val redisModule = DI.Module(name = "redis") {
    bind<JedisPool>() with eagerSingleton {
        val host = configString("redis.host")
        val port = configString("redis.port").toInt()
        val timeout = configString("redis.timeout").toInt()
        val pass = configString("redis.password")
        JedisPool(GenericObjectPoolConfig(), host, port, timeout, pass)
    }
}
