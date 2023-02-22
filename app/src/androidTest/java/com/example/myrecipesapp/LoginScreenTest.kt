package com.example.myrecipesapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.myrecipesapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()
    private val auth: FirebaseAuth = Firebase.auth

    @Test
    fun onRegister_showRecipesSearchScreen() {

        // Create example user account
        rule.onNodeWithText("Sign up").performClick()
        rule.onNodeWithText("Email").performTextInput("user123@gmail.com")
        rule.onNodeWithText("Password").performTextInput("abcd1234")
        rule.onNodeWithText("Create Account").performClick()

        Thread.sleep(1000)

        // Check if Recipes Search Screen is displayed
        rule.onNodeWithContentDescription("Search Icon").assertExists()

        auth.currentUser?.delete()
    }

    @Test
    fun onLoginCorrectly_showRecipesSearchScreen() {

        // Login with correct details
        rule.onNodeWithText("Email").performTextInput("azim123@gmail.com")
        rule.onNodeWithText("Password").performTextInput("12345678")
        rule.onNodeWithText("Login").performClick()

        Thread.sleep(1000)

        // Check if Recipes Search Screen is displayed
        rule.onNodeWithContentDescription("Search Icon").assertExists()
    }

    @Test
    fun onLoginIncorrectly_hideRecipesSearchScreen() {

        // Login with incorrect details
        rule.onNodeWithText("Email").performTextInput("azim12@gmail.com")
        rule.onNodeWithText("Password").performTextInput("1234567")
        rule.onNodeWithText("Login").performClick()

        Thread.sleep(1000)

        // Check if Recipes Search Screen is not displayed
        rule.onNodeWithContentDescription("Search Icon").assertDoesNotExist()
    }
}