package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class GetParseFile {
    private final File file;

    public GetParseFile(File file) {
        this.file = file;
    }

    private String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(
                new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char)data)) {
                    output.append(data);
                }
            }
        }
        return output.toString();
    }


    public String getContent() throws IOException {
        return getContent(a -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(a -> a < 0x80 );
    }

}
