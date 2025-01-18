package es.ulpgc.dis.model;

import javax.swing.*;

public interface Image  {
    String name();
    Icon content();
    Format format();

    enum Format {
        jpg, Jpeg, Png, Gif
    }
}
