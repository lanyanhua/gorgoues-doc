package com.lancabbage.lancodeapi;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author: lanyanhua
 * @date: 2020/12/24 4:02 下午
 * @Description:
 */
public class ImageTest {

    @Test
    public void test() throws IOException {

        Thumbnails.of(new File("/Users/lanyanhua/Desktop/10021608790036_.pic_hd.jpg"))
                .size(500,500)
                .outputQuality(0.8f)
                .toFile(new File("/Users/lanyanhua/Desktop/10021608790036_.pic_hd.jpg"));

    }
}
