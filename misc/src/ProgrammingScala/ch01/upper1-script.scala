class Upper {
    def upper(strings: String*): Seq[String] = {
        strings.map((s:String) => s.toUpperCase())
    }
}

val up = new Upper
Console.println(up.upper("A", "First", "Scala", "Program"))

// vim: set ts=4 sw=4 et
