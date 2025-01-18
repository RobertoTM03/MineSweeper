package es.ulpgc.dis.arquitecture.io;

import es.ulpgc.dis.arquitecture.model.Image;

import java.util.Map;

public interface ImageLoader {
    Map<String, Image> load();
}
