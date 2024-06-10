package com.buran.core.storage.images.models

import com.buran.core.config.ExtendedUUIDIdTable

object ImagesTable: ExtendedUUIDIdTable(name = "linked_image") {
    val parentKey = uuid("parentKey")
}
