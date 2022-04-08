package com.alexgaas.priceaction.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

// from: https://www.baeldung.com/kotlin-logging
fun logger(forClass: KClass<*>): Lazy<Logger> = lazy { DelegatingLogger(getLogger(forClass)) }

fun getLogger(forClass: KClass<*>): Logger = LoggerFactory.getLogger(getClassForLogging(forClass.java))

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
}

/**
 * We're wrapping all of our logger instances with this delegate class. This allows us to
 * provide specialization of the [error] method to include additional, rich information about the reactive stack trace
 * to ApplicationInsights by way of the MDC context.
 */
class DelegatingLogger(val wrapped: Logger) : Logger by wrapped {
    override fun error(msg: String?, t: Throwable?) {
        t?.suppressed?.find { it::class.java.name.contains("OnAssemblyException") }?.let {
            MDC.putCloseable("reactor-stack", it.toString()).use {
                wrapped.error(msg, t)
            }
        } ?: wrapped.error(msg, t)
    }
}
