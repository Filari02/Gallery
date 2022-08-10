package org.example.gallery.utils;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImageUtils {

    private final static int THUMBNAIL_SIZE = 200;

    public static byte[] createThumbnail(byte[] bytes) throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(is);

        BufferedImage thumbnailBufferedImage =  Scalr.resize(bufferedImage, THUMBNAIL_SIZE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(thumbnailBufferedImage, "png", byteArrayOutputStream);
        byte[] thumbnailBytes = byteArrayOutputStream.toByteArray();

        return thumbnailBytes;
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
