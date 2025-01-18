package es.ulpgc.dis.io;

import es.ulpgc.dis.model.Image;

import java.util.Map;

public interface ImageLoader {
    Map<String, Image> load();
}
