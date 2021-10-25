package seedu.academydirectory.versioncontrol.objects;

import java.util.Objects;

public abstract class VcObject {
    private final String hash;

    protected VcObject(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VcObject vcObject = (VcObject) o;
        return hash.equals(vcObject.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}
