package ru.gr0946x.ui.animation;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoWriter implements AutoCloseable {

    private final SeekableByteChannel out;
    private final AWTSequenceEncoder encoder;

    public VideoWriter(File file, int fps) throws IOException {
        this.out = NIOUtils.writableChannel(file);
        this.encoder = new AWTSequenceEncoder(out, Rational.R(fps, 1));
    }

    public void addFrame(BufferedImage frame) throws IOException {
        encoder.encodeImage(frame);
    }

    public void finish() throws IOException {
        encoder.finish();
    }

    @Override
    public void close() {
        if (out != null) {
            NIOUtils.closeQuietly(out);
        }
    }
}