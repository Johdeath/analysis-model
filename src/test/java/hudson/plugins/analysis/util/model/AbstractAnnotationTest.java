package hudson.plugins.analysis.util.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.*;

/**
 * Tests the class {@link AbstractAnnotation}.
 *
 * @author Ulli Hafner
 */
public class AbstractAnnotationTest {
    /**
     * Verifies that the comparator sorts by filename, then linenumber, then column.
     */
    @Test
    public void testCompareTo() {
        List<AbstractAnnotation> warnings = Lists.newArrayList();
        warnings.add(createWarning(6, "cccc", 2, 2));
        warnings.add(createWarning(4, "cccc", 2, 0));
        warnings.add(createWarning(0, "aaaa", 0, 0));
        warnings.add(createWarning(3, "cccc", 1, 0));
        warnings.add(createWarning(2, "cccc", 0, 0));
        warnings.add(createWarning(8, "cccc", 3, 1));
        warnings.add(createWarning(5, "cccc", 2, 1));
        warnings.add(createWarning(1, "bbbb", 0, 0));
        warnings.add(createWarning(7, "cccc", 2, 3));

        Collections.sort(warnings);
        verifyOrder(warnings, true);

        Collections.reverse(warnings);
        verifyOrder(warnings, false);
    }

    private void verifyOrder(final List<AbstractAnnotation> warnings, final boolean isAscending) {
        int position = 0;
        for (FileAnnotation warning : warnings) {
            int actualPosition = isAscending ? position : warnings.size() - position - 1;
            assertEquals("Wrong position: ", String.valueOf(actualPosition), warning.getMessage());

            position++;
        }
    }

    private Warning createWarning(final int id, final String fileName, final int line, final int column) {
        Warning warning = new Warning(Priority.HIGH, String.valueOf(id), line, line, "category", "type");
        warning.setFileName(fileName);
        warning.setColumnPosition(column, column);
        return warning;
    }

    /**
     * A concrete warning that will be sorted.
     */
    private static class Warning extends AbstractAnnotation {
        private static final long serialVersionUID = -8082297852537003624L;

        Warning(final Priority priority, final String message, final int start, final int end, final String category, final String type) {
            super(priority, message, start, end, category, type);
        }

            @Override
        public String getToolTip() {
            return StringUtils.EMPTY;
        }
    }
}

