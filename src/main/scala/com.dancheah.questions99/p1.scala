package com.dancheah.questions99

object P1 {
    def last(l: List[Int]): Int = {
        if (l.tail == List()) {
            l.head
        } else {
            last(l.tail)
        }
    }
}

// vim: set ts=4 sw=4 et:
