package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Remark &&
                value.equals(((Remark) obj).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
