package drivers;

import java.nio.charset.Charset;

public interface EncoderDecoderInterface {

    public Charset getEncoding();

    public EncoderDecoderInterface setEncoding(String encoding);

    public byte[] encode(String data);

    public byte[] encode(byte[] data);

    public byte[] decode(String data);

    public byte[] decode(byte[] data);

}
