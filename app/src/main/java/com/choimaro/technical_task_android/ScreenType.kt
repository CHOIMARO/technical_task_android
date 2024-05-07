package com.choimaro.technical_task_android

sealed class ScreenType(val route: String, val title: Int, val icon: Int) {
    data object SearchScreen: ScreenType(route = "search_screen", title = R.string.search, icon = R.drawable.ic_search)
    data object BookMarkScreen: ScreenType(route = "book_mark_screen", title = R.string.book_mark, icon = R.drawable.ic_book_mark)
}