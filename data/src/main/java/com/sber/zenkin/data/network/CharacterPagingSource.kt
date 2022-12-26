package com.sber.zenkin.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sber.zenkin.data.mappers.toDomainCharacter
import com.sber.zenkin.domain.model.Character
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val pageNumber = params.key ?: 1
        val response = starWarsApiService.getCharacters(query, pageNumber)

        return if (response.isSuccessful) {
            val characters = checkNotNull(response.body()).results.map { it.toDomainCharacter() }
            Timber.tag("PagingSource").d(characters.toString())
            val nextPageNumber = if (characters.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
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