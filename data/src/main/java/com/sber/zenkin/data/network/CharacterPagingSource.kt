package com.sber.zenkin.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sber.zenkin.data.mappers.toDomainCharacter
import com.sber.zenkin.domain.model.Character
import retrofit2.HttpException
import timber.log.Timber

//PagingSource<Int - номер страницы, Character - класс>()
@Suppress("UnnecessaryVariable")
class CharacterPagingSource constructor(
    private val starWarsApiService: StarWarsApiService,
    private val query: String
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        Timber.tag("PAGING").d(
            "[params] key = %s, loadSize = %s, placeHolder = %b",
            params.key,
            params.loadSize,
            params.placeholdersEnabled
        )
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val pageNumber = params.key ?: 1
        Timber.tag("PAGING").d("pageNumber = %s", pageNumber)
        val response = starWarsApiService.getCharacters(query, pageNumber)

        return if (response.isSuccessful) {
            val responseBody = checkNotNull(response.body())
            Timber.tag("PAGING").d("responseBody = %s", responseBody.toString())

            val characters = responseBody.results.map { it.toDomainCharacter() }
            val nextPageNumber = if (responseBody.next != null) pageNumber + 1 else null
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            Timber.tag("PAGING").d(
                "next = %d, prev = %d, character = %s",
                nextPageNumber,
                prevPageNumber,
                characters.toString()
            )
            LoadResult.Page(
                data = characters,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } else {
            LoadResult.Error(throwable = HttpException(response))
        }
    }
}