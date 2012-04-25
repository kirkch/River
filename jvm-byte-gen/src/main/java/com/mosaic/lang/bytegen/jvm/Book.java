package com.mosaic.lang.bytegen.jvm;

/**
 *
 */
public class Book {
    private String name;

    public Book() {
        name = "LOTR";
    }

    public class Chapter {

        public String f() {
            return "10";
        }
    }
}


class Author {
    private String title;


}
