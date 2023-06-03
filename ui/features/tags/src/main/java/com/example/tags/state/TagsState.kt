package com.example.tags.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tags.viewmodel.TagViewModel

@Stable
class TagsState(
    private val tagViewModel: TagViewModel
) {

    internal val rememberAllTagsState
        @Composable get() =
            remember(tagViewModel, tagViewModel::getAllLTags)
                .collectAsStateWithLifecycle()
                .value

}
