package com.neojou.currencyviewer.domain.model

import kotlin.jvm.JvmInline

/**
* 代表一個貨幣對。
* v1.6 固定使用 [USD_JPY]，透過 companion object 強制，避免散落的字串字面量。
*
* 注意：KMP commonMain 不使用 @JvmInline；value class 在 Desktop / Wasm 皆直接支援。
*/
@JvmInline
value class FxPair(val symbol: String) {
    companion object {
        /** v1.6 唯一支援的貨幣對 */
        val USD_JPY = FxPair("USD/JPY")
    }
}