package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.UniqueGroupTagList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson, .isSameGroup comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    private final UniqueGroupTagList groupTags;

    // @@author Derek-Hardy
    private final UniqueGroupList groups;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        groupTags = new UniqueGroupTagList();
        groups = new UniqueGroupList();
    }
    // @@author

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     *
     * NOTE: this class is created temporarily to create the initial working UI as {@code Group} functionality is
     * still being developed. In the meantime, this class will be used instead to showcase how the UI will look like,
     * and will be deprecated progressively when Group implementation is updated.
     */
    @Deprecated
    public void setGroupTags(List<Tag> groups) {
        this.groupTags.setGroups(groups);
    }

    // @@author Derek-Hardy
    /**
     * Replaces the contents of the group list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }
    // @@author

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setGroups(newData.getGroupList());
        setGroupTags(newData.getGroupTagList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    // @@author Derek-Hardy
    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }
    // @@author

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        if (p.getGroupTags() != null && !p.getGroupTags().isEmpty()) {
            for (Tag t : p.getGroupTags()) {
                groupTags.add(t);
            }
        }
    }

    // @@author Derek-Hardy
    /**
     * Adds a group to the address book.
     * The group must not already exist in the address book.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     *
     * The method is not yet stable. (under construction)
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        // clear membership of target & set up membership for editedPerson
        // TODO
        persons.setPerson(target, editedPerson);
    }

    /**
     * Replace the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the address book.
     *
     * The method is not yet stable. (under construction)
     */
    public void updateGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);
        // clear members of target & set up members for editedGroup
        // TODO
        groups.setGroup(target, editedGroup);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        requireNonNull(key);
        key.clearMembership();
        persons.remove(key);
    }

    /**
     * Removes {@code group} from this {@code AddressBook}.
     * {@code group} must exist in the address book.
     *
     */
    public void removeGroup(Group group) {
        requireNonNull(group);
        group.clearMembers();
        groups.remove(group);
    }
    // @@author

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons, "
                + groups.asUnmodifiableObservableList().size() + " groups";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    @Deprecated
    public ObservableList<Tag> getGroupTagList() {
        return groupTags.asUnmodifiableObservableList();
    }

    // @@author Derek-Hardy
    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }
    // @@author

    /**
     * Merge another MeetingBook into current MeetingBook.
     */
    public void merge(ReadOnlyAddressBook imported) {
        // If Both book contains same entries
        if (equals(imported)) {
            return;
        }
        if (imported instanceof AddressBook) {
            AddressBook importedBook = (AddressBook)imported;
            Iterator<Person> personItr =  importedBook.persons.iterator();
            while (personItr.hasNext()) {
                Person importPerson = personItr.next();
                if (!hasPerson(importPerson)) {
                    addPerson(importPerson);
                }
            }

            Iterator<Group> groupItr = importedBook.groups.iterator();
            while (groupItr.hasNext()) {
                Group importGroup = groupItr.next();
                if (!hasGroup(importGroup)) {
                    addGroup(importGroup);
                }
            }

        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons))
                && groups.equals(((AddressBook) other).groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, groups);
    }
}
