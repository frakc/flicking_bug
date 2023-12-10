package com.compose.flickerstub.ui.fragment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCityCompose(
    state: State<SearchCityState>, onTextChanged: (String) -> Unit, onItemSelected: (String) -> Unit
) {
    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {

        Column(
            Modifier
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .widthIn(max = 640.dp)
        ) {


            Row(
                Modifier
                    .height(46.dp)
                    .background(
                        color = Color.White, shape = RoundedCornerShape(4.dp)
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = (Modifier.width(8.dp)))
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Search city input box",
                    Modifier
                        .size(40.dp)
                        .alpha(if (state.value.query.isEmpty()) 0.5f else 1f),
                    tint = Color.Black
                )
                Spacer(modifier = (Modifier.width(8.dp)))
                val textStyle = TextStyle(
                    color = Color.Black, textAlign = TextAlign.Start
                )
                BasicTextField(
                    value = state.value.query,
                    decorationBox = { innerTextField ->
                        if (state.value.query.isEmpty()) {
                            Text(
                                text = "Search",
                                style = textStyle,
                                modifier = Modifier.alpha(0.5f),
                                maxLines = 1,

                                )
                        }
                        innerTextField()
                    },
                    textStyle = textStyle,
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = {

                        onTextChanged(it)
                    },
                    modifier = Modifier.weight(1f),

                    )
            }


            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {

                itemsIndexed(
                    state.value.searchCities.fromServer,
                ) { index, city ->
                    val bgType by remember {
                        derivedStateOf {
                            getBgType(index, state.value.searchCities.fromServer.size)
                        }
                    }
                    CityItem(modifier = Modifier.animateItemPlacement(),
                        value = city,
                        bgType = bgType,
                        onClick = { onItemSelected(it) })
                }


            }
        }
    }
}

private fun getBgType(index: Int, size: Int): BgType {
    return when {
        size == 1 -> BgType.SINGLE
        index == 0 && size > 1 -> BgType.TOP
        index == size - 1 && size > 0 -> BgType.BOTTOM
        else -> BgType.MIDDLE
    }
}

enum class BgType { TOP, MIDDLE, BOTTOM, SINGLE }


@Composable
private fun CityItem(
    modifier: Modifier, value: String, bgType: BgType, onClick: (String) -> Unit
) {
    val textStyle = TextStyle(
        color = Color.Black, textAlign = TextAlign.Start
    )
    Box(
        modifier
            .height(46.dp)
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick(value) }, contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = value, style = textStyle, modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

    }

}