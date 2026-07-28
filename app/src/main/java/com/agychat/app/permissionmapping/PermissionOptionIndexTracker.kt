package com.agychat.app.permissionmapping

import javax.inject.Inject

class PermissionOptionIndexTracker @Inject constructor() {
    var currentIndex: Int = DefaultPermissionCursorAssumption.getDefaultIndex()
        private set

    fun update(index: Int) {
        currentIndex = index
    }

    fun reset() {
        currentIndex = DefaultPermissionCursorAssumption.getDefaultIndex()
    }
}
