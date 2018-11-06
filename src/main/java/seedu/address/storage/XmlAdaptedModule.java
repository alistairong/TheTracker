package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Module.
 *
 * @author alistair
 */
public class XmlAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    @XmlElement(required = true)
    private String moduleCode;
    @XmlElement(required = true)
    private String moduleTitle;
    @XmlElement(required = true)
    private String academicYear;
    @XmlElement(required = true)
    private String semester;
    @XmlElement(required = true)
    private List<XmlAdaptedPerson> students;
    @XmlElement
    private List<XmlAdaptedTag> tagged;

    /**
     * Constructs an XmlAdaptedModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() {}

    /**
     * Constructs an {@code XmlAdaptedModule} with the given person details.
     */
    public XmlAdaptedModule(String moduleCode, String moduleTitle, String academicYear,
                            String semester, List<XmlAdaptedPerson> students,
                            List<XmlAdaptedTag> tagged) {
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.academicYear = academicYear;
        this.semester = semester;
        this.students = new ArrayList<>(students);
        this.tagged = tagged;

    }

    /**
     * Converts a given Module into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedModule
     */
    public XmlAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().toString();
        moduleTitle = source.getModuleTitle().toString();
        academicYear = source.getAcademicYear().toString();
        semester = source.getSemester().toString();
        students = new ArrayList<>();
        for (Person person : source.getStudents()) {
            students.add(new XmlAdaptedPerson(person));
        }
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Module object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module
     */
    public Module toModelType() throws IllegalValueException {
        final UniquePersonList loadedStudentsList = new UniquePersonList(new ArrayList<>());

        if (students != null) {
            for (XmlAdaptedPerson person : students) {
                loadedStudentsList.add(person.toModelType());
            }
        }

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);
        }
        final ModuleCode modelCode = new ModuleCode(moduleCode);

        ModuleTitle modelTitle;
        if (this.moduleTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleTitle.class.getSimpleName()));
        }
        if (this.moduleTitle.equals("")) {
            modelTitle = new ModuleTitle();
        } else {
            if (!ModuleTitle.isValidTitle(this.moduleTitle)) {
                throw new IllegalValueException(ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);
            }
            modelTitle = new ModuleTitle(this.moduleTitle);
        }

        AcademicYear modelAcademicYear;
        if (academicYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AcademicYear.class.getSimpleName()));
        }
        if (academicYear.equals("")) {
            modelAcademicYear = new AcademicYear();
        } else {
            if (!AcademicYear.isValidYear(academicYear)) {
                throw new IllegalValueException(AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS);
            }
            modelAcademicYear = new AcademicYear(academicYear);
        }

        Semester modelSemester;
        if (semester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Semester.class.getSimpleName()));
        }
        if (semester.equals("")) {
            modelSemester = new Semester();
        } else {
            if (!Semester.isValidSemester(semester)) {
                throw new IllegalValueException(Semester.MESSAGE_SEMESTER_CONSTRAINTS);
            }
            modelSemester = new Semester(semester);
        }

        Set<Tag> tagMap = new HashSet<>();

        if (tagged != null) {

            for (XmlAdaptedTag t : tagged) {
                tagMap.add(t.toModelType());
            }
        }


        return new Module(modelCode, modelTitle, modelAcademicYear, modelSemester,
                loadedStudentsList, tagMap);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedModule)) {
            return false;
        }

        XmlAdaptedModule otherModule = (XmlAdaptedModule) other;
        return Objects.equals(moduleCode, otherModule.moduleCode)
                && Objects.equals(moduleTitle, otherModule.moduleTitle)
                && Objects.equals(academicYear, otherModule.academicYear)
                && Objects.equals(semester, otherModule.semester)
                && Objects.equals(students, otherModule.students)
                && tagged.equals(otherModule.tagged);
    }
}
