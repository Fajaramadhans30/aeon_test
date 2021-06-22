package com.app.aeontest.data.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class DataRealm (
    @PrimaryKey
    var albumId: Int? = null,
    var id: String? = null,
    var title: String? = null,
    var url: String? = null,
    var thumbnailUrl: String? = null
) : RealmObject()