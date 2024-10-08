package drivers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Driver implements EncoderDecoderInterface {

    private Charset encoding;

    @Override
    public Charset getEncoding() {
        if (this.encoding == null) {
            this.encoding = StandardCharsets.UTF_8;
        }

        return this.encoding;
    }

    @Override
    public EncoderDecoderInterface setEncoding(String encoding) {
        this.encoding = Charset.forName(encoding);

        return this;
    }

    @Override
    public byte[] encode(String data) {
        return this.encode(data.getBytes(this.getEncoding()));
    }

    @Override
    public byte[] encode(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    @Override
    public byte[] decode(String data) {
        return this.decode(data.getBytes(this.getEncoding()));
    }

    @Override
    public byte[] decode(byte[] data) {
        return Base64.getDecoder().decode(data);
    }

}
