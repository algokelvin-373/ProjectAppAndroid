package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.data.repository.movies.datasourceImpl.FakeMovieRepositoryTest
import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import algokelvin.app.movietvclient.getOrAwaitValue
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        val fakeMovieRepositoryTest = FakeMovieRepositoryTest()
        val getMoviesUseCase = GetMoviesUseCase(fakeMovieRepositoryTest)
        val updateMoviesUseCase = UpdateMoviesUseCase(fakeMovieRepositoryTest)
        movieViewModel = MovieViewModel(getMoviesUseCase, updateMoviesUseCase)
    }

    @Test
    fun getMovies_returnCurrentList() {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "overview1", "posterPath1", "releaseDate1", "title1"))
        movies.add(Movie(2, "overview2", "posterPath2", "releaseDate2", "title2"))
        movies.add(Movie(3, "overview3", "posterPath3", "releaseDate3", "title3"))

        val currentList = movieViewModel.getMovies().getOrAwaitValue()
        assertThat(currentList).isEqualTo(movies)
    }

    @Test
    fun updateMovies_returnUpdateList() {
        val movies = mutableListOf<Movie>()
        movies.add(Movie(1, "overview4", "posterPath4", "releaseDate4", "title4"))
        movies.add(Movie(2, "overview5", "posterPath5", "releaseDate5", "title5"))
        movies.add(Movie(3, "overview6", "posterPath6", "releaseDate6", "title6"))

        val currentList = movieViewModel.updateMovies().getOrAwaitValue()
        assertThat(currentList).isEqualTo(movies)
    }

}