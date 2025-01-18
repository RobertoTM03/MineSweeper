package es.ulpgc.dis.io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SwingImageDeserializer implements ImageDeserializer {
    @Override
    public Icon deserialize(byte[] bytes) {
        try {
            java.awt.Image awtImage = ImageIO.read(new ByteArrayInputStream(bytes));
            return new ImageIcon(awtImage);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing image", e);
        }
    }
}
