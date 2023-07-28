package com.example.wishlist_android.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wishlist_android.api.classes.Wish
import com.example.wishlist_android.providers.WishFormProvider
import com.example.wishlist_android.utils.replaceCommaWithDot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URL

data class WishFormState(
    val name: String = "",
    val nameError: String? = null,

    val price: String = "0",
    val priceError: String? = null,

    val link: String? = null,
    val linkError: String? = null,
)


class WishFormModel : ViewModel() {
    private val _uiState = MutableStateFlow(WishFormState())
    val uiState: StateFlow<WishFormState> = _uiState.asStateFlow()
    var wishFormProvider: WishFormProvider? = null

    fun setWishValues(wish: Wish) {
        _uiState.value = _uiState.value.copy(
            name = wish.name,
            price = wish.price.toString(),
            link = wish.link
        )
        Log.d("wish", _uiState.value.toString())
    }

    fun checkFormValidity(): Boolean {
        val nameValid = checkNameValidity(_uiState.value.name) == null
        val priceValid = checkPriceValidity(_uiState.value.price) == null
        val linkValid = checkLinkValidity(_uiState.value.link ?: "") == null

        return nameValid && priceValid && linkValid

    }

    private fun checkNameValidity(name: String): String? {
        var error: String? = null

        if (name.isEmpty()) {
            error = wishFormProvider?.nameRequired
        }

        return error
    }

    fun updateName(name: String): Boolean {
        val error = checkNameValidity(name)

        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = error
        )

        return error == null
    }

    private fun checkLinkValidity(link: String): String? {
        if (link.isNotEmpty()) {

            try {
                val url = URL(link)
            } catch (e: Exception) {
                return wishFormProvider?.linkInvalid
            }
        }
        return null

    }

    fun updateLink(link: String): Boolean {
        val error = checkLinkValidity(link)

        _uiState.value = _uiState.value.copy(
            link = link,
            linkError = error
        )

        return error == null
    }


    private fun checkPriceValidity(price: String): String? {
        var error: String? = null



        try {
            price.toDouble()
        } catch (e: Exception) {
            val priceWithoutComma = replaceCommaWithDot(price)
            if (priceWithoutComma != price) {
                return checkPriceValidity(priceWithoutComma)
            }
            error = wishFormProvider?.priceInvalid
        }

        for (s in price) {
            if (!s.isDigit() && s != '.' && s != ',') {
                error = wishFormProvider?.priceInvalid
            }
        }

        return error
    }

    fun updatePrice(price: String): Boolean {
        val error = checkPriceValidity(price)

        _uiState.value = _uiState.value.copy(
            price = price,
            priceError = error
        )

        return error == null
    }


}