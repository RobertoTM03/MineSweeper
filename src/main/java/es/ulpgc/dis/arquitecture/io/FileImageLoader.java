package es.ulpgc.dis.arquitecture.io;

import es.ulpgc.dis.arquitecture.model.Image;

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
            images.put(removeExtension(file.getName()), () -> {
                try {
                    return new SwingImageDeserializer().deserialize(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return images;
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

    public static Map<String, Image> loadIcons() {
        return new FileImageLoader(new File("./src/main/resources/")).load();
    }
}
