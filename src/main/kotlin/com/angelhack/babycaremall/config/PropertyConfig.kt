package com.angelhack.babycaremall.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources


@Configuration
@PropertySources(PropertySource("classpath:env.yml"))
class PropertyConfig