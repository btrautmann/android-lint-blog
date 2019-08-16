package com.brandontrautmann.myapplication

@Deprecated("This class is deprecated because it is bad!")
class BadClass {
    // ...
}

class AnotherClass {
    init {
        val badClass: BadClass = BadClass()
    }
}


