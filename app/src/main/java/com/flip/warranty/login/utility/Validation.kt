package com.flip.warranty.login.utility

import android.widget.EditText

object Validation {

    fun validateUsername(username_register: EditText): Boolean {
        return if (username_register.text.toString().isEmpty()) {
            username_register.error = "Email cannot be blank"
            false
        } else {
            true
        }

    }

    fun validatePassword(password_register: EditText): Boolean {
        return if (password_register.text.toString().isEmpty()) {
            password_register.error = "Please enter your password"
            false
        } else {
            true
        }

    }
}