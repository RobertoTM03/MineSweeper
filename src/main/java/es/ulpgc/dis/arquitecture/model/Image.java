package es.ulpgc.dis.arquitecture.model;

import javax.swing.*;

public interface Image  {
    Icon content();

    enum Format {
        jpg, Jpeg, Png, Gif
    }
}
