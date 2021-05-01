package com.mahmoudelshahat.forecastapp.data.dp.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class IconsConverter {

    @TypeConverter
    fun fromIconsList(icons: List<String?>?): String? {
        if (icons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.getType()
        return gson.toJson(icons, type)
    }

    @TypeConverter
    fun toCountryLangList(icons: String?): List<String>? {
        if (icons == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.getType()
        return gson.fromJson<List<String>>(icons, type)
    }

}