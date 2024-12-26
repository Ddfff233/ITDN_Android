package com.example.fragment.project.ui.share

import androidx.lifecycle.viewModelScope
import com.example.miaow.base.http.HttpResponse
import com.example.miaow.base.http.post
import com.example.miaow.base.vm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShareArticleUiState(
    var isLoading: Boolean = false,
    var success: Boolean = false,
    var message: String = "",
)

class ShareArticleViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(ShareArticleUiState())

    val uiState: StateFlow<ShareArticleUiState> = _uiState.asStateFlow()

    fun resetMessage() {
        _uiState.update {
            it.copy(message = "")
        }
    }

    fun share(title: String, link: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        //通过viewModelScope创建一个协程
        viewModelScope.launch {
            //以get方式发起网络请求
            val response = post<HttpResponse> {
                setUrl("lg/user_article/add/json")
                putParam("title", title)
                putParam("link", link)
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    success = response.errorCode == "0",
                    message = response.errorMsg
                )
            }
        }
    }
}