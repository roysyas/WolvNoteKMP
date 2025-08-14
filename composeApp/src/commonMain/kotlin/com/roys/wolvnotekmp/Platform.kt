package com.roys.wolvnotekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform