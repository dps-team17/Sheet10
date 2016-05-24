package team17.sheet10;

public class VigenereCipher {

    private String key;

    VigenereCipher(String key) {
        this.key = key;
    }

    public static void main(String[] args) {

        String key = "Super Secret Super Long Keyword";
        VigenereCipher cipher = new VigenereCipher(key);
        String secretMessage = "Hello Crypto!";

        byte[] encrypted = cipher.encrypt(secretMessage);

        System.out.println(new String(encrypted));
        System.out.println(cipher.decrypt(encrypted));
    }

    byte[] encrypt(String plain) {

        byte[] input = plain.getBytes();
        return xor(input, resizeKey(key, input.length));
    }

    String decrypt(byte[] encrypted) {

        byte[] decrypted = xor(encrypted, resizeKey(key, encrypted.length));
        return new String(decrypted);
    }

    private byte[] resizeKey(String key, int length) {
        while (key.getBytes().length < length) {
            key += key;
        }

        return key.getBytes();
    }

    private byte[] xor(byte[] a, byte[] b) {

        byte[] longer = a.length >= b.length ? a : b;
        byte[] shorter = a.length < b.length ? a : b;
        byte[] result = new byte[shorter.length];

        for (int i = 0; i < shorter.length; i++) {
            result[i] = (byte) ((int) longer[i] ^ (int) shorter[i]);
        }

        return result;
    }
}
