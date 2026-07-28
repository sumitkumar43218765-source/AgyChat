package com.agychat.app.presentation.chatlist

sealed class ChatListUiEvent {
    object CreateNewChat : ChatListUiEvent()
    data class DeleteChat(val id: String) : ChatListUiEvent()
    data class RenameChat(val id: String, val newTitle: String) : ChatListUiEvent()
    data class OpenChat(val id: String) : ChatListUiEvent()
    object OpenSettings : ChatListUiEvent()
    object OpenWorkspaces : ChatListUiEvent()
}
