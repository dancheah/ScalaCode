package com.dancheah.questions99

object P4 {
    // This is a very lispish style
    def length(l: List[Any]): Int = {
        if (l == List()) {
            0 
        } else {
            1 + length(l.tail)
        }
    }
}


// vim: set ts=4 sw=4 et:
