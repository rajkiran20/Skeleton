package com.skeleton.presentation.callback

interface ListClickCallback : ItemClickCallback {
    fun performHeaderClickAction(viewModel: IDataBindingModelHeader)
}
