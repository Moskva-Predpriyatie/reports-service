package aam.mos.di

import aam.mos.di.util.configString
import aam.mos.model.dbo.FullUserDbo
import aam.mos.model.dbo.IndustryDbo
import com.mongodb.reactivestreams.client.gridfs.GridFSBucket
import com.mongodb.reactivestreams.client.gridfs.GridFSBuckets
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.eagerSingleton
import org.kodein.di.instance
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mongoModule = DI.Module(name = "mongo") {
    bind<CoroutineClient>() with eagerSingleton {
        KMongo.createClient(configString("mongo.connection")).coroutine
    }
    bind<CoroutineDatabase>() with eagerSingleton {
        instance<CoroutineClient>().getDatabase(configString("mongo.db"))
    }
    bind<GridFSBucket>() with eagerSingleton {
        val db = instance<CoroutineClient>().client.getDatabase(configString("mongo.filesdb"))
        GridFSBuckets.create(db)
    }

    bind<CoroutineCollection<FullUserDbo>>() with eagerSingleton {
        instance<CoroutineDatabase>().getCollection("users")
    }

    bind<CoroutineCollection<IndustryDbo>>() with eagerSingleton {
        instance<CoroutineDatabase>().getCollection("static-industries")
    }
}
