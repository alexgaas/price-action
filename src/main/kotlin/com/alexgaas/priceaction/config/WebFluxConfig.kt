package com.alexgaas.priceaction.config

import com.alexgaas.priceaction.domain.models.notification.enums.NotificationType
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.format.FormatterRegistry
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebFluxConfig : WebFluxConfigurer {

    @Bean
    @Order
    fun nettyReactiveWebServerFactory() = NettyReactiveWebServerFactory()

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/swagger-ui.html**")
            .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)

        registry.addConverter(String::class.java, NotificationType::class.java) {
            NotificationType.valueOf(it.uppercase())
        }
    }
}
