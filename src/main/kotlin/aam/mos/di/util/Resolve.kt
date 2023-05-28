package aam.mos.di.util

import org.kodein.di.DirectDIAware
import org.kodein.di.instance

inline fun <reified R> DirectDIAware.new(
    constructor: () -> R,
): R = constructor()

inline fun <reified R, reified T1> DirectDIAware.new(
    constructor: (T1) -> R,
): R = constructor(instance())

inline fun <reified R, reified T1, reified T2> DirectDIAware.new(
    constructor: (T1, T2) -> R,
): R = constructor(instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3> DirectDIAware.new(
    constructor: (T1, T2, T3) -> R,
): R = constructor(instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> DirectDIAware.new(
    constructor: (T1, T2, T3, T4) -> R,
): R = constructor(instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(),instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(),instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(),instance(), instance(), instance())

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> DirectDIAware.new(
    constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
): R = constructor(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(),instance(), instance(), instance(), instance())
