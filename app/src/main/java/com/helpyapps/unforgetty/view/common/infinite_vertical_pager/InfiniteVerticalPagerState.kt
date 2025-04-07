package com.helpyapps.unforgetty.view.common.infinite_vertical_pager

import androidx.annotation.FloatRange
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberInfiniteVerticalPagerState(
    initialItem: Int,
    @FloatRange(from = -0.5, to = 0.5) initialPageOffsetFraction: Float = 0f,
    itemCount: Int
): InfiniteVerticalPagerState {
    return rememberSaveable(saver = InfiniteVerticalPagerState.Saver) {
        InfiniteVerticalPagerState(
            initialItem,
            initialPageOffsetFraction,
            itemCount
        )
    }
}

class InfiniteVerticalPagerState(
    initialItem: Int,
    @FloatRange(from = -0.5, to = 0.5) initialPageOffsetFraction: Float = 0f,
    val itemCount: Int
) {

    private val pagerSize = itemCount * 1000
    val value = PagerState(
        currentPage = pagerSize / 2 + initialItem,
        currentPageOffsetFraction = initialPageOffsetFraction,
        pageCount = { pagerSize }
    )

    val currentItem
        get() = value.currentPage % itemCount

    val currentPage
        get() = value.currentPage

    val currentPageOffsetFraction
        get() = value.currentPageOffsetFraction

    companion object {
        val Saver: Saver<InfiniteVerticalPagerState, *> = listSaver(
            save = {
                listOf(
                    it.currentItem,
                    it.currentPageOffsetFraction,
                    it.itemCount
                )
            },
            restore = {
                InfiniteVerticalPagerState(
                    it[0] as Int,
                    it[1] as Float,
                    it[2] as Int
                )
            }
        )
    }

}