package seedu.academydirectory.model.student;

import static seedu.academydirectory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.academydirectory.model.tag.Tag;

/**
 * Represents a Student in the academy directory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Telegram telegram;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private StudioRecord studioRecord;
    private Assessment assessment;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Telegram telegram, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, telegram, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.tags.addAll(tags);
        this.studioRecord = new StudioRecord(12);
        this.assessment = new Assessment();
    }

    /**
     * Constructor for Student with Attendance and Assessment.
     */

    public Student(Name name, Phone phone, Email email, Telegram telegram, StudioRecord studioRecord,
                   Assessment assessment, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, telegram, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.tags.addAll(tags);
        this.studioRecord = studioRecord;
        this.assessment = assessment;
    }

    public void setAttendance(Attendance attendance) {
        this.studioRecord = new StudioRecord(attendance, studioRecord.getParticipation());
    }

    public void setParticipation(Participation participation) {
        this.studioRecord = new StudioRecord(studioRecord.getAttendance(), participation);
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Attendance getAttendance() {
        return studioRecord.getAttendance();
    }

    public Participation getParticipation() {
        return studioRecord.getParticipation();
    }

    public StudioRecord getStudioRecord() {
        return studioRecord;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Student)) {
            return false;
        }
        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getTelegram().equals(getTelegram())
                && otherStudent.getTags().equals(getTags())
                && otherStudent.getStudioRecord().equals(getStudioRecord())
                && otherStudent.getAssessment().equals(getAssessment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, telegram, tags, studioRecord);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Telegram: ")
                .append(getTelegram());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
