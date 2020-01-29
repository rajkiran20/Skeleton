package com.skeleton.search.repository

import com.skeleton.search.remote.LandingApis
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LandingRepositoryImplTest {

    @Mock
    lateinit var landingApis : LandingApis

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldCallGetProviderApi() {
        LandingRepositoryImpl(landingApis).getPopularBrands()
        verify(landingApis).getPopularBrands()
    }
}
