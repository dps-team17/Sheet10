package team17.sheet10;

public class SuperSecret {

    public static byte[] encrypt(String plain, String key) {

        byte[] input = plain.getBytes();
        return xor(input, formatKey(key, input.length));
    }

    public static String decrypt(byte[] encrypted, String key) {

        byte[] decrypted = xor(encrypted, formatKey(key, encrypted.length));
        return new String(decrypted);
    }

    private static byte[] formatKey(String key, int length) {
        while (key.getBytes().length < length) {
            key += key;
        }

        return key.getBytes();
    }

    private static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) ((int) a[i] ^ (int) b[i]);
        }

        return result;
    }

    public static void main(String[] args) {

        String key = "JANET";
        String secretMessage = "Hello Crypto!";
        byte[] encrypted = encrypt(secretMessage, key);
        System.out.println(encrypted);
        System.out.println(decrypt(encrypted, key));
    }
}
