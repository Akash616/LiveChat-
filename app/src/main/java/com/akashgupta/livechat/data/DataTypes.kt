package com.akashgupta.livechat.data

data class UserData(
    var userId: String?="",
    var name: String?="",
    var number: String?="",
    var imgUrl: String?=""
) {
    //firebase mai by default Data class use nahi kar sakta we hve to map
    fun toMap() = mapOf( //key value
        "userId" to userId,
        "name" to name,
        "number" to number,
        "imageUrl" to imgUrl
    )
}