# How to communicate with the System-Service
This tutorial will use java reflection.


````kotlin
val cls = Class.forName("android.os.GethProxy")
val obj = context.getSystemService("geth");
if (it) {
    // Turn on light client
    val startGeth = cls.getMethod("startGeth")
    startGeth.invoke(obj);
} else {
    // Turn off light client
    val shutdownGeth = cls.getMethod("shutdownGeth")
    shutdownGeth.invoke(obj)
}
```
