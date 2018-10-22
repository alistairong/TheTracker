package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteOccasionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteOccasionCommand object
 */
public class DeleteOccasionCommandParser implements Parser<DeleteOccasionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteOccasionCommand
     * and returns an DeleteOccasionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteOccasionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteOccasionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOccasionCommand.MESSAGE_USAGE), pe);
        }
    }

}
