package com.flip.warranty.retailer.utility

import android.widget.EditText

object Validation {
    fun validateName(textBox: EditText): Boolean {
        return if (textBox.text.toString().isEmpty()) {
            textBox.error = "Email cannot be blank"
            false
        } else {
            true
        }
    }

    fun validateDescription(textBox: EditText): Boolean {
        return if (textBox.text.toString().isEmpty()) {
            textBox.error = "Email cannot be blank"
            false
        } else {
            true
        }
    }

    fun validatePrice(textBox: EditText): Boolean {
        return if (textBox.text.toString().isEmpty()) {
            textBox.error = "Email cannot be blank"
            false
        } else {
            true
        }
    }
}