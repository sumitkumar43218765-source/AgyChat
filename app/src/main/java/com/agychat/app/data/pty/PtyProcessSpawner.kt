package com.agychat.app.data.pty

import javax.inject.Inject

class PtyProcessSpawner @Inject constructor() {
    private var process: Process? = null

    fun spawn(command: String, args: List<String>, env: Map<String, String>, cwd: String): Process? {
        val commandList = mutableListOf(command)
        commandList.addAll(args)
        
        val builder = ProcessBuilder(commandList)
        builder.directory(java.io.File(cwd))
        builder.environment().putAll(env)
        
        process = builder.start()
        return process
    }

    fun destroy() {
        process?.destroy()
        process = null
    }
}
