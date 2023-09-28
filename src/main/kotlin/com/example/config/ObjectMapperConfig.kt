package com.example.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.hubspot.jackson.datatype.protobuf.ProtobufModule
import kh.org.soramitsu.common.Constants.DATETIME_FORMAT
import kh.org.soramitsu.config.CommonUtilProperties
import kh.org.soramitsu.deserializer.AnyLocalDateDeserializer
import kh.org.soramitsu.deserializer.AnyLocalDateTimeDeserializer
import kh.org.soramitsu.serializer.AnyLocalDateSerializer
import kh.org.soramitsu.serializer.AnyLocalDateTimeSerializer
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun objectMapper(): ObjectMapper {
    return jacksonObjectMapper().apply {
//            registerModule(Jdk8Module())
            registerModule(javaTimeModule())
//            registerModule(ParameterNamesModule())
//            registerModule(CustomSerializableModule(ctx))
            registerModule(ProtobufModule())
        registerKotlinModule()
        configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
        configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        configure(DeserializationFeature.USE_LONG_FOR_INTS, true)
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS)
        setDateFormat(SimpleDateFormat(DATETIME_FORMAT))
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}

private fun javaTimeModule(): SimpleModule {
        return JavaTimeModule()
                .addDeserializer(LocalDateTime::class.java, AnyLocalDateTimeDeserializer())
                .addSerializer(LocalDateTime::class.java, AnyLocalDateTimeSerializer())
                .addSerializer(LocalDate::class.java, AnyLocalDateSerializer())
                .addDeserializer(LocalDate::class.java, AnyLocalDateDeserializer())
                .addDeserializer(LocalTime::class.java, LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addSerializer(LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
}
