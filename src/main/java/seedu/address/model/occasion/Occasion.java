package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Represents an Occasion within the address book.
 * @author KongZijin
 */
public class Occasion {

    // Identity fields
    private final OccasionName occasionName;
    private final OccasionDate occasionDate;
    private final OccasionLocation location;
    private final UniquePersonList attendanceList;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Occasion(OccasionName occasionName, OccasionDate occasionDate, OccasionLocation location,
                    Set<Tag> tags, TypeUtil type) {
        requireAllNonNull(occasionName, occasionDate, tags, type);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = new UniquePersonList(new ArrayList<>());
        this.tags.addAll(tags);
    }

    public Occasion(OccasionDescriptor toCopy) {
        requireNonNull(toCopy);
        this.occasionName = toCopy.getOccasionName().orElse(new OccasionName());
        this.occasionDate = toCopy.getOccasionDate().orElse(new OccasionDate());
        this.location = toCopy.getOccasionLocation().orElse(new OccasionLocation());
        this.attendanceList = new UniquePersonList(new ArrayList<>());
        this.tags.addAll(toCopy.getTags().orElse(new HashSet<Tag>()));
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
                    Set<Tag> tags) {
        this(occasionName, occasionDate, null, tags, TypeUtil.OCCASION);
    }

    public OccasionName getOccasionName() {
        return occasionName;
    }

    public OccasionDate getOccasionDate() {
        return occasionDate;
    }

    public UniquePersonList getAttendanceList() {
        return attendanceList == null ? new UniquePersonList(new ArrayList<>()) : attendanceList;
    }

    public OccasionLocation getOccasionLocation() {
        return location;
    }

    /**
     * Get the set of Tags.
     *
     * @return An immutable tag set, which throws {@code
     * UnsupportedOperationException} if modification is attempted.
     */
    // TODO change the implementation of all the places that use this method.
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both occasion have the same name and date.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameOccasion (Occasion otherOccasion) {
        if (otherOccasion == this) {
            return true;
        }

        return otherOccasion != null
                && otherOccasion.getOccasionName().equals(this.getOccasionName())
                && otherOccasion.getOccasionDate().equals(this.getOccasionDate());
    }

    /**
     * Check the equality of two occasions. This defines a stronger notion of
     * equality between two occasions.
     *
     * @return Return true if both occasion have the same identity and data
     * fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Occasion)) {
            return false;
        }

        Occasion otherOccasion = (Occasion) other;
        return otherOccasion.getOccasionName().equals(this.getOccasionName())
                && otherOccasion.getOccasionDate().equals(this.getOccasionDate())
                && otherOccasion.getOccasionLocation().equals(this.getOccasionLocation())
                && otherOccasion.getAttendanceList().equals(this.getAttendanceList())
                && otherOccasion.getTags().equals(this.getTags());
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing one.
        return Objects.hash(occasionName, occasionDate, attendanceList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getOccasionName())
                .append(" ")
                .append(getOccasionDate())
                // object.
                .append(" Tags: ");
        getTags().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
