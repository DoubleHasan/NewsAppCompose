package com.example.newsapp.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.newsapp2.domain.model.News
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

val NewsNavType = object : NavType<News>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): News? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): News {
        val decoded = URLDecoder.decode(value, StandardCharsets.UTF_8.name())
        return Json.decodeFromString(decoded)
    }

    override fun put(bundle: Bundle, key: String, value: News) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: News): String {
        val json = Json.encodeToString(value)
        return URLEncoder.encode(json, StandardCharsets.UTF_8.name())
    }
}