package es.ulpgc.dis.io;

import es.ulpgc.dis.model.Image;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class FileImageLoader implements ImageLoader {
    private final File[] files;

    public FileImageLoader(File folder) {
        this.files = requireNonNull(folder.listFiles(ofTypeImage()));
    }

    @Override
    public Map<String, Image> load() {
        HashMap<String, Image> images = new HashMap<>();

        for (File file : files) {
            images.put(removeExtension(file.getName()), new Image() {
                @Override
                public String name() {
                    return file.getName();
                }

                @Override
                public Icon content() {
                    try {
                        return new SwingImageDeserializer().deserialize(Files.readAllBytes(file.toPath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public Format format() {
                    return Format.valueOf(camelCase(extensionOf(file.getName())));
                }
            });
        }

        return images;
    }

    private static String camelCase(String text) {
        return text.toUpperCase().charAt(0) +
                text.toLowerCase().substring(1);
    }

    private String extensionOf(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private FileFilter ofTypeImage() {
        return f -> validImageExtensions()
                .anyMatch(e-> fileHasValidExtension(f, e));
    }

    private Stream<String> validImageExtensions() {
        return Arrays.stream(Image.Format.values())
                .map(s->s.name().toLowerCase());

    }

    private String removeExtension(String name) {
        int lastDotIndex = name.lastIndexOf(".");

        return (lastDotIndex < 0) ? name : name.substring(0, lastDotIndex);
    }

    private static boolean fileHasValidExtension(File f, String e) {
        return f.getName().toLowerCase().endsWith(e);
    }
}
