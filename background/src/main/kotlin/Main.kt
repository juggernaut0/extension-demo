fun main() {
    js("chrome").runtime.onInstalled.addListener {
        console.log("hello chrome")
    }
}