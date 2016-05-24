package team17.sheet10;

public class SuperSecretEncryptor {

    private String key;

    public SuperSecretEncryptor(String key) {
        this.key = key;
    }

    public static void main(String[] args) {

        String key = "Super Secret Super Long Keyword";
        SuperSecretEncryptor encryptor = new SuperSecretEncryptor(key);
        String secretMessage = "Hello Crypto!";

        String encrypted = encryptor.encrypt(secretMessage);

        System.out.println(encrypted);
        System.out.println(encryptor.decrypt(encrypted));
    }

    public String encrypt(String plain) {

        byte[] input = plain.getBytes();
        byte[] encrypted = xor(input, resizeKey(key, input.length));

        return new String(encrypted);
    }

    public String decrypt(String encrypted) {

        byte[] input = encrypted.getBytes();
        byte[] decrypted = xor(input, resizeKey(key, input.length));
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
