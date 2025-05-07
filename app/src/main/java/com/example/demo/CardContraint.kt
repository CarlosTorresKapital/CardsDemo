package com.example.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs

@Composable
fun CardConstraint(
    items : List<String>,
) {

    //dynamic refs for every list item
    val dynamicRefs = items.indices.map { createRefs() }

}