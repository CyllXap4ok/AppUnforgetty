package com.helpyapps.unforgetty.views.infinite_vertical_pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun InfiniteVerticalPager(
    infiniteVerticalPagerState: InfiniteVerticalPagerState,
    beyondViewportItemCount: Int = 3,
    pageHeight: Dp = 40.dp,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {

    val fling = PagerDefaults.flingBehavior(
        state = infiniteVerticalPagerState.value,
        pagerSnapDistance = PagerSnapDistance.atMost(100)
    )

    VerticalPager(
        modifier = Modifier.height(pageHeight * beyondViewportItemCount),
        state = infiniteVerticalPagerState.value,
        pageSize = PageSize.Fixed(pageHeight),
        beyondViewportPageCount = beyondViewportItemCount,
        flingBehavior = fling
    ) { page ->

        val maxPageOffset = (beyondViewportItemCount / 2).toFloat()
        val pageOffset = ((maxPageOffset + infiniteVerticalPagerState.currentPage - page)
                + infiniteVerticalPagerState.currentPageOffsetFraction).absoluteValue

        Box(
            modifier = Modifier.fillMaxHeight().graphicsLayer {

                lerp(
                    start = 0.7f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, maxPageOffset)
                ).also {
                    scaleX = it
                    scaleY = it
                }

                alpha = lerp(
                    start = 0.3f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, maxPageOffset) / maxPageOffset
                )

            },
            contentAlignment = Alignment.Center
        ) {
            pageContent((page - maxPageOffset.toInt()) % infiniteVerticalPagerState.itemCount)
        }

    }

}