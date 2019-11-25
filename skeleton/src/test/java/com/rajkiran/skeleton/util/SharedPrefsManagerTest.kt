package com.rajkiran.skeleton.util

import android.content.Context
import android.content.SharedPreferences
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SharedPrefsManagerTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    @Mock
    private lateinit var sharedPrefs: SharedPreferences

    private val key = "Key"
    private val booleanValue = true
    private val intValue = 1234
    private val longValue = 1234L
    private val stringValue = "1234"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.edit()).thenReturn(editor)
    }

    @Test
    fun shouldPutInt() {
        SharedPrefsManager.putInt(key, intValue, context)
        Mockito.verify(editor).putInt(key, intValue)
        Mockito.verify(editor).apply()
    }

    @Test
    fun shouldGetInt() {
        SharedPrefsManager.getInt(key, intValue, context)
        Mockito.verify(sharedPrefs).getInt(key, intValue)
    }

    @Test
    fun shouldPutLong() {
        SharedPrefsManager.putLong(key, longValue, context)
        Mockito.verify(editor).putLong(key, longValue)
        Mockito.verify(editor).apply()
    }

    @Test
    fun shouldGetLong() {
        SharedPrefsManager.getLong(key, longValue, context)
        Mockito.verify(sharedPrefs).getLong(key, longValue)
    }

    @Test
    fun shouldPutBoolean() {
        SharedPrefsManager.putBoolean(key, booleanValue, context)
        Mockito.verify(editor).putBoolean(key, booleanValue)
        Mockito.verify(editor).apply()
    }

    @Test
    fun shouldGetBoolean() {
        SharedPrefsManager.getBoolean(key, booleanValue, context)
        Mockito.verify(sharedPrefs).getBoolean(key, booleanValue)
    }

    @Test
    fun shouldPutString() {
        SharedPrefsManager.putString(key, stringValue, context)
        Mockito.verify(editor).putString(key, stringValue)
        Mockito.verify(editor).apply()
    }

    @Test
    fun shouldGetString() {
        SharedPrefsManager.getString(key, stringValue, context)
        Mockito.verify(sharedPrefs).getString(key, stringValue)
    }


    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

}