package aam.mos.di

import aam.mos.di.util.new
import aam.mos.repository.PdfService
import aam.mos.repository.PdfServiceImpl
import aam.mos.service.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.eagerSingleton

val serviceModule = DI.Module(name = "service") {
    bind<AuthService>() with eagerSingleton { new(::AuthServiceImpl) }
    bind<UserService>() with eagerSingleton { new(::UserServiceImpl) }
    bind<StaticService>() with eagerSingleton { new(::StaticServiceImpl) }
    bind<NeuralService>() with eagerSingleton { new(::NeuralServiceImpl) }
    bind<ReportService>() with eagerSingleton { new(::ReportServiceImpl) }
    bind<PdfService>() with eagerSingleton { new(::PdfServiceImpl) }
}
