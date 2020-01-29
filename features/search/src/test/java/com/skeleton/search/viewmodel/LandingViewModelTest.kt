package com.skeleton.search.viewmodel
import com.skeleton.core.model.Brand
import com.skeleton.search.repository.LandingRepository
import com.skeleton.util.TestUtils
import com.skeleton.util.extension.fromJson
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class LandingViewModelTest {

    @Mock
    private lateinit var repository: LandingRepository

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var brandCall: Call<List<Brand>>

    private lateinit var viewModel: LandingViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = LandingViewModel(repository)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(repository, brandCall)
        Mockito.validateMockitoUsage()
    }

    @Test
    fun shouldFetchProvidersIfProvidersListIsEmpty() {
        val brands = getData("JSON_response_goes_here")
        Mockito.`when`(repository.getPopularBrands()).thenReturn(brandCall)
        Mockito.`when`(brandCall.enqueue(ArgumentMatchers.any()))
            .then { invocation ->
                val callback = invocation.arguments[0] as Callback<List<Brand>>
                callback.onResponse(brandCall, Response.success(brands))
            }
        viewModel.getPopularBrands()
        Mockito.verify(repository).getPopularBrands()
        Mockito.verify(brandCall).enqueue(ArgumentMatchers.any())
        assertEquals(brands, viewModel.brands.value)
    }

    private fun getData(fileName : String) = Gson().fromJson<List<Brand>>(TestUtils.readFile(fileName))
}
