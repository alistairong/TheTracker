package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.TypeUtil;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * JAXB-friendly version of the Occasion.
 *
 * @author alistair
 */
public class XmlAdaptedOccasion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Occasion's %s field is missing!";

    @XmlElement(required = true)
    private String occasionName;
    @XmlElement(required = true)
    private String occasionDateTime;
    @XmlElement(required = true)
    private String location;
    @XmlElement
    private List<XmlAdaptedPerson> attendanceList = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagMap;
    /**
     * Constructs an XmlAdaptedOccasion.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOccasion() {}

    /**
     * Constructs an {@code XmlAdaptedOccasion} with the given occasion details.
     */
    public XmlAdaptedOccasion(String occasionName, String occasionDate,
                              String location, List<XmlAdaptedTag> tagged, List<XmlAdaptedPerson> attendanceList) {
        requireAllNonNull(occasionName, occasionDate, location, tagged, attendanceList);
        this.occasionName = occasionName;
        this.occasionDateTime = occasionDate;
        this.location = location;
        this.attendanceList = attendanceList;
        this.tagMap = tagged;
    }

    /**
     * Converts a given Occasion into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOccasion
     */
    public XmlAdaptedOccasion(Occasion source) {
        requireNonNull(source);
        tagMap = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        occasionName = source.getOccasionName().toString();
        occasionDateTime = source.getOccasionDate().toString();
        location = source.getOccasionLocation().toString();
        attendanceList = source.getAttendanceList().asNormalList().stream()
                            .map(XmlAdaptedPerson::new)
                            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted occasion object into the model's Occasion object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted occasion
     */
    public Occasion toModelType() throws IllegalValueException {
        requireAllNonNull(this.occasionName, this.occasionDateTime, this.attendanceList);
        OccasionName occasionName = new OccasionName(this.occasionName);
        OccasionDate occasionDate;
        OccasionLocation location;
        if (!this.occasionDateTime.equals("")) {
            occasionDate = new OccasionDate(this.occasionDateTime);
        } else {
            occasionDate = new OccasionDate();
        }
        List<Person> attendanceList = new ArrayList<>();
        for (XmlAdaptedPerson person : this.attendanceList) {
            attendanceList.add(person.toModelType());
        }

        Set<Tag> tags = new HashSet<>();
        if (!this.location.equals("")) {
            location = new OccasionLocation(this.location);
        } else {
            location = new OccasionLocation();
        }

        if (tagMap != null && tagMap.size() > 0) {
            for (XmlAdaptedTag t : tagMap) {
                tags.add(t.toModelType());
            }
        }

        return new Occasion(occasionName, occasionDate, location, tags, TypeUtil.OCCASION);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOccasion)) {
            return false;
        }

        XmlAdaptedOccasion otherOccasion = (XmlAdaptedOccasion) other;
        return Objects.equals(occasionName, otherOccasion.occasionName)
                && Objects.equals(occasionDateTime, otherOccasion.occasionDateTime)
                && Objects.equals(location, otherOccasion.location)
                && tagMap.equals(otherOccasion.tagMap);
    }
}
