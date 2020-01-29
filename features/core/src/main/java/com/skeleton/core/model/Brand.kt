package com.skeleton.core.model

import com.skeleton.core.BR
import com.skeleton.core.R
import com.skeleton.presentation.callback.IDataBindingModel
import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) : IDataBindingModel {
    override fun layoutResId(): Int = R.layout.brand_item

    override fun dataBindingVariable() = BR.brand
}