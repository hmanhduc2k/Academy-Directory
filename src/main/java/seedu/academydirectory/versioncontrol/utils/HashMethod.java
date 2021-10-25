package seedu.academydirectory.versioncontrol.utils;

public enum HashMethod {
    SHA1 ("SHA-1", 40),
    SHA256 ("SHA-256", 64),
    MD5 ("MD5", 32);

    private final String value;
    private final int numHex;

    HashMethod(String s, int numHex) {
        this.value = s;
        this.numHex = numHex;
    }

    public String getValue() {
        return value;
    }

    public int getNumHex() {
        return numHex;
    }
}
