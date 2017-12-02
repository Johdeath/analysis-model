package edu.hm.hafner.analysis;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static edu.hm.hafner.analysis.assertj.Assertions.*;

/**Test class for the {@link IssuesFilterBuilder}.
 *
 * @author Johannes Arzt
 */

class IssuesFilterBuilderTest {
    @Test
    void shouldCreateEmptyIssuesFilter() {

        IssuesFilter filter;
        IssuesFilterBuilder builder = new IssuesFilterBuilder();

        filter = builder.build();
        assertThat(filter.getCategories().isEmpty()).as("Categories are not empty").isEqualTo(true);
        assertThat(filter.getFileNames().isEmpty()).as("FileNames are not empty").isEqualTo(true);
        assertThat(filter.getModuleNames().isEmpty()).as("Modules are not empty").isEqualTo(true);
        assertThat(filter.getPackageNames().isEmpty()).as("Packages are not empty").isEqualTo(true);
        assertThat(filter.getTypes().isEmpty()).as("Types are not empty").isEqualTo(true);

    }

    @Test
    void shoudCreateFilledIssuesFilter() {
        List<String> categories = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        List<String> moduleNames = new ArrayList<>();
        List<String> packageNames = new ArrayList<>();
        List<String> types = new ArrayList<>();

        categories.add("categorie");
        fileNames.add("filename");
        moduleNames.add("module");
        packageNames.add("packageNames");
        types.add("type");

        IssuesFilter filter;
        IssuesFilterBuilder builder = new IssuesFilterBuilder();

        builder.setCategories(categories)
                .setFileNames(fileNames)
                .setModuleNames(moduleNames)
                .setPackageNames(packageNames)
                .setTypes(types);

        filter = builder.build();

        assertThat(filter.getCategories()).as("Categories are not equal to the input value").isEqualTo(categories);
        assertThat(filter.getFileNames()).as("FileNames are not equal to the input value").isEqualTo(fileNames);
        assertThat(filter.getModuleNames()).as("Modules are not equal to the input value").isEqualTo(moduleNames);
        assertThat(filter.getPackageNames()).as("Packages are not equal to the input value").isEqualTo(packageNames);
        assertThat(filter.getTypes()).as("Types are not equal to the input value").isEqualTo(types);


    }

}