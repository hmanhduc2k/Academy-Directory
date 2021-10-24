package seedu.academydirectory.logic.commands;

import static java.util.Objects.requireNonNull;

public class AdditionalInformation<T> {
    private final T info;

    /**
     * Constructor for Additional Information
     * @param info information to be passed
     */
    private AdditionalInformation(T info) {
        this.info = info;
    }

    /**
     * Static factory method to create an Additional Information field
     * @param info to be passed
     * @return new Additional Information object
     */
    @SuppressWarnings("unchecked")
    public AdditionalInformation<T> of(T info) {
        if (info == null) {
            None none = new None();
            return (AdditionalInformation<T>) none;
        } else {
            return new AdditionalInformation<>(info);
        }
    }

    public T get() {
        requireNonNull(info);
        return info;
    }

    /**
     * A class that represents None
     */
    private static class None extends AdditionalInformation<Object> {
        private static final String noInformationMessage = "No information is associated";
        private None() {
            super(noInformationMessage);
        }
    }
}
