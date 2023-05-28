package aam.mos.di

import aam.mos.controller.AuthController
import aam.mos.controller.ReportController
import aam.mos.controller.StaticController
import aam.mos.controller.UserController
import aam.mos.controller.core.Controller
import org.kodein.di.*

val controllerModule = DI.Module(name = "controller") {
    bindSet<Controller> {
        add { eagerSingleton { new(::AuthController) } }
        add { eagerSingleton { new(::UserController) } }
        add { eagerSingleton { new(::StaticController) } }
        add { eagerSingleton { new(::ReportController) } }
    }
}
