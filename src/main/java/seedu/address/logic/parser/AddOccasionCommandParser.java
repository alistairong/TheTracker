package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddOccasionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;


public class AddOccasionCommandParser implements Parser<AddOccasionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns a new Occasion object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOccasionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OCCASION_NAME, PREFIX_OCCASION_DATE, PREFIX_LOCATION,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_OCCASION_NAME, PREFIX_OCCASION_DATE, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        OccasionName occasionName = ParserUtil.parseOccasionName(argMultimap.getValue(PREFIX_OCCASION_NAME).get());
        OccasionDate occasionDate = ParserUtil.parseOccasionDate(argMultimap.getValue(PREFIX_OCCASION_DATE).get());
//        Person organizer = new Person(new Name(PREFIX_ORGANIZER.toString()), new Phone("123"), new Email("asd@cd.com"),
//                new Address("abc"), new HashSet<Tag>());
        OccasionLocation location = ParserUtil.parseOccasionLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Occasion occasion = new Occasion(occasionName, occasionDate, location, tagList, TypeUtil.OCCASION);
        return new AddOccasionCommand(occasion);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
