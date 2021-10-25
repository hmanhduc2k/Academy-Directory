package seedu.academydirectory.versioncontrol.objects;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;

public class Tree extends VcObject {
    public static final Tree NULL = new Tree("NULL", "null", "null");

    private final HashMap<String, String> hashMap;

    private Tree(String hash) {
        super(hash);
        hashMap = new HashMap<>();
    }

    /**
     * Creates a Tree object to work with programmatically. A Tree object SHOULD NOT be instantiated directly.
     * Instead, use a BlobFactory object to ensure all Tree objects follow the established contract
     * @param fileName name of file to be version controlled
     * @param vcName version controlled name of the file
     */
    public Tree(String hash, String fileName, String vcName) {
        this(hash, List.of(fileName), List.of(vcName));
    }

    /**
     * Creates a Tree object to work with programmatically. A Tree object SHOULD NOT be instantiated directly.
     * Instead, use a BlobFactory object to ensure all Tree objects follow the established contract
     * @param hash hash of the resultant tree object
     */
    public Tree(String hash, List<String> fileNames, List<String> vcNames) {
        this(hash);
        requireNonNull(fileNames);
        requireNonNull(vcNames);

        if (fileNames.size() != vcNames.size()) {
            throw new IllegalArgumentException("Different list sizes");
        }

        for (int i = 0; i < fileNames.size(); i++) {
            hashMap.putIfAbsent(vcNames.get(i), fileNames.get(i));
        }
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    @Override
    public String toString() {
        return getHash();
    }
}
