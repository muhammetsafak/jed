import drivers.Base64Driver;
import drivers.EncoderDecoderInterface;

public class JEDFactory {

    public EncoderDecoderInterface createJED(String driver, String charset) throws Exception
    {
        EncoderDecoderInterface jed;

        switch (driver.toLowerCase()) {
            case "base64":
                jed = new Base64Driver();
                break;
            default:
                throw new Exception("Unsupported \"" + driver + "\" EncoderDecoder!");
        }

        if (charset != null) {
            jed.setEncoding(charset);
        }

        return jed;
    }

}
