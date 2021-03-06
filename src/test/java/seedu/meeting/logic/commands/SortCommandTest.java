package seedu.meeting.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.meeting.commons.core.Messages.MESSAGE_PERSONS_SORTED_OVERVIEW;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalPersons.ALICE;
import static seedu.meeting.testutil.TypicalPersons.BENSON;
import static seedu.meeting.testutil.TypicalPersons.CARL;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.model.person.util.PersonPropertyComparator;
import seedu.meeting.testutil.MeetingBookBuilder;

// @@author betakuwe
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
class SortCommandTest {
    private Model model = new ModelManager(new MeetingBookBuilder().withPerson(CARL).withPerson(BENSON)
            .withPerson(ALICE).build(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private PersonPropertyComparator nameComparator = PersonPropertyComparator.NAME;
    private PersonPropertyComparator phoneComparator = PersonPropertyComparator.PHONE;
    private PersonPropertyComparator emailComparator = PersonPropertyComparator.EMAIL;
    private PersonPropertyComparator addressComparator = PersonPropertyComparator.ADDRESS;

    private SortCommand sortByNameCommand = new SortCommand(nameComparator);
    private SortCommand sortByPhoneCommand = new SortCommand(phoneComparator);
    private SortCommand sortByEmailCommand = new SortCommand(emailComparator);
    private SortCommand sortByAddressCommand = new SortCommand(addressComparator);

    @Test
    public void equals() {
        assertEquals(sortByNameCommand, new SortCommand(PersonPropertyComparator.NAME));
        assertEquals(sortByPhoneCommand, new SortCommand(PersonPropertyComparator.PHONE));
        assertEquals(sortByEmailCommand, new SortCommand(PersonPropertyComparator.EMAIL));
        assertEquals(sortByAddressCommand, new SortCommand(PersonPropertyComparator.ADDRESS));

        assertNotEquals(sortByNameCommand, sortByPhoneCommand);
        assertNotEquals(sortByNameCommand, sortByEmailCommand);
        assertNotEquals(sortByNameCommand, sortByAddressCommand);
        assertNotEquals(sortByPhoneCommand, sortByEmailCommand);
        assertNotEquals(sortByPhoneCommand, sortByAddressCommand);
        assertNotEquals(sortByEmailCommand, sortByAddressCommand);
        assertNotEquals(sortByNameCommand, null);
        assertNotEquals(sortByPhoneCommand, null);
        assertNotEquals(sortByEmailCommand, null);
        assertNotEquals(sortByAddressCommand, null);
    }

    @Test
    public void testOrderOfSortedListOfModel() {
        assertEquals(Arrays.asList(CARL, BENSON, ALICE), model.getSortedPersonList());
    }

    @Test
    public void execute_sortByName() {
        String expectedMessage = String.format(MESSAGE_PERSONS_SORTED_OVERVIEW, nameComparator.toString());
        model.updateSortedPersonList(nameComparator);
        assertCommandSuccess(sortByNameCommand, model, commandHistory, expectedMessage, model);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getSortedPersonList());
    }

    @Test
    public void execute_sortByPhone() {
        String expectedMessage = String.format(MESSAGE_PERSONS_SORTED_OVERVIEW, phoneComparator.toString());
        model.updateSortedPersonList(phoneComparator);
        assertCommandSuccess(sortByPhoneCommand, model, commandHistory, expectedMessage, model);
        assertEquals(Arrays.asList(ALICE, CARL, BENSON), model.getSortedPersonList());
    }

    @Test
    public void execute_sortByEmail() {
        String expectedMessage = String.format(MESSAGE_PERSONS_SORTED_OVERVIEW, emailComparator.toString());
        model.updateSortedPersonList(emailComparator);
        assertCommandSuccess(sortByEmailCommand, model, commandHistory, expectedMessage, model);
        assertEquals(Arrays.asList(ALICE, CARL, BENSON), model.getSortedPersonList());
    }

    @Test
    public void execute_sortByAddress() {
        String expectedMessage = String.format(MESSAGE_PERSONS_SORTED_OVERVIEW, addressComparator.toString());
        model.updateSortedPersonList(addressComparator);
        assertCommandSuccess(sortByAddressCommand, model, commandHistory, expectedMessage, model);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getSortedPersonList());
    }
}
