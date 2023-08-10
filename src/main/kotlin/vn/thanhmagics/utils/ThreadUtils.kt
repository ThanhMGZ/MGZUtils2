package vn.thanhmagics.utils

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ThreadUtils {
    fun runAsync(runnable: Runnable) {
        val executor = Executors.newFixedThreadPool(1);
        CompletableFuture.runAsync(runnable,executor)
        executor.shutdown()
    }

}