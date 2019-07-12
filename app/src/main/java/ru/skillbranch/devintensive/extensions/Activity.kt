package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    //Based on https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/17789187#17789187

    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus

    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rect = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(rect)
    val frameHeight = rect.height()
    val screenHeight = this.windowManager.defaultDisplay.height

    val heightDiff = screenHeight - frameHeight
    return heightDiff > 100
}

fun Activity.isKeyboardClosed(): Boolean =
    !this.isKeyboardOpen()
