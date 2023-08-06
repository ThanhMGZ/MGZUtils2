package vn.thanhmagics.craftUtils

abstract class Timer(private val stage : Int) : Runnable {

    private var ct = System.currentTimeMillis()

    private var cancel = false

    fun start() {
        while (!cancel) {
            if (System.currentTimeMillis() > ct) {
                ct = System.currentTimeMillis() + stage
                run()
            }
        }
    }

    fun cancel() : Timer {
        cancel = true
        return this
    }

}