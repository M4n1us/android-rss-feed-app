package dev.kisser.rssfeed.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Feed(
    @PrimaryKey val feedUrl: String,
    @ColumnInfo(name = "lastBuildDate") val lastBuildDate: Date,
    @ColumnInfo(name = "feedTitle") val feedTitle: String?
)
