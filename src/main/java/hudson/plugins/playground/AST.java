package hudson.plugins.playground;

import hudson.plugins.analysis.util.model.FileAnnotation;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Class represents the Abstract Syntax Tree (AST) and operates with it.
 *
 * @author Christian M&ouml;stl
 */
public abstract class AST {

    private DetailAST abstractSyntaxTree;

    private String filename;

    private List<DetailAST> elementsInSameLine;

    private FileAnnotation fileAnnotation;

    /**
     * Creates a new instance of {@link AST}.
     *
     * @param filename
     *            the filename
     * @param fileAnnotation
     *            the FileAnnotation
     */
    public AST(final String filename, final FileAnnotation fileAnnotation) {
        this.filename = filename;
        abstractSyntaxTree = createAST(filename);
        elementsInSameLine = new ArrayList<DetailAST>();
        this.fileAnnotation = fileAnnotation;
        runThroughAST(abstractSyntaxTree, fileAnnotation.getPrimaryLineNumber());
    }

    /**
     * Sets the ast to the specified value.
     *
     * @param abstractSyntaxTree
     *            the value to set
     */
    public void setAbstractSyntaxTree(final DetailAST abstractSyntaxTree) {
        this.abstractSyntaxTree = abstractSyntaxTree;
    }

    /**
     * Returns the DetailAST.
     *
     * @return the DetailAST
     */
    public DetailAST getAbstractSyntaxTree() {
        return abstractSyntaxTree;
    }

    /**
     * Sets the filename to the specified value.
     *
     * @param filename
     *            the value to set
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * Returns the filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the elementsInSameLine to the specified value.
     *
     * @param elementsInSameLine
     *            the value to set
     */
    public void setElementsInSameLine(final List<DetailAST> elementsInSameLine) {
        this.elementsInSameLine = elementsInSameLine;
    }

    /**
     * Returns the elementsInSameLine.
     *
     * @return the elementsInSameLine
     */
    public List<DetailAST> getElementsInSameLine() {
        return elementsInSameLine;
    }

    /**
     * Sets the fileAnnotation to the specified value.
     *
     * @param fileAnnotation the value to set
     */
    public void setFileAnnotation(final FileAnnotation fileAnnotation) {
        this.fileAnnotation = fileAnnotation;
    }

    /**
     * Returns the fileAnnotation.
     *
     * @return the fileAnnotation
     */
    public FileAnnotation getFileAnnotation() {
        return fileAnnotation;
    }

    /**
     * Creates the DetailAST of the specified Java-source-file.
     *
     * @param file
     *            the filename
     * @return the DetailAST
     */
    private DetailAST createAST(final String file) {
        try {
            return TreeWalker.parse(new FileContents(new FileText(new File(file), "UTF-8")));
        }
        catch (RecognitionException exception) {
            exception.printStackTrace();
        }
        catch (TokenStreamException exception) {
            exception.printStackTrace();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Tests, if two AST are equal. For example permutations of methods are indifferent and therefore the ast returns
     * true.
     *
     * @param ast1
     *            The first AST.
     * @param ast2
     *            The second AST.
     * @return <code>true</code>, if both ast are equal, otherwise return <code>false</code>.
     */
    public boolean isEqualAST(final DetailAST ast1, final DetailAST ast2) {
        return ast1.equalsTree(ast2);
    }

    /**
     * Runs through the AST.
     *
     * @param root
     *            Expects the root of the AST which is run through
     * @param line
     *            the linenumber
     */
    public void runThroughAST(final DetailAST root, final int line) {
        if (root != null) {
            if (root.getLineNo() == line) {
                elementsInSameLine.add(root);
            }
            if (root.getFirstChild() != null) {
                runThroughAST(root.getFirstChild(), line);
            }
            if (root.getNextSibling() != null) {
                runThroughAST(root.getNextSibling(), line);
            }
        }
    }

    /**
     * Runs entirely through the AST.
     *
     * @param root
     *            Expects the root of the AST which is run through
     */
    public void runThroughAST(final DetailAST root) {
        if (root != null) {
            System.out.println(TokenTypes.getTokenName(root.getType()));

            if (root.getFirstChild() != null) {
                runThroughAST(root.getFirstChild());
            }
            if (root.getNextSibling() != null) {
                runThroughAST(root.getNextSibling());
            }
        }
    }

    /**
     * Calculates the Hashcode.
     *
     * @param list
     *            nodes of the AST.
     * @return the hashcode.
     */
    // TODO: Parameter von Warning miteinbeziehen...
    public long calcHashcode(final List<DetailAST> list) {
        if (list == null) {
            return 0;
        }
        else {
            long primenumber = 7;
            long hashCode = 0;

            for (DetailAST element : list) {
                hashCode += element.getType() * primenumber;
            }

            return hashCode;
        }
    }

    /**
     * Prints the list.
     *
     * @param list
     *            List which should be printed.
     */
    public void printList(final List<DetailAST> list) {
        if (list != null) {
            for (DetailAST ast : list) {
                System.out.println(TokenTypes.getTokenName(ast.getType()));
            }
        }
        else {
            System.out.println("Keine Elemente...");
        }
    }

    /**
     * Choose the Area around the warning.
     *
     * @return the Area
     */
    public abstract List<DetailAST> chooseArea();

    /**
     * Calculates the Hashcode (SHA-1) for the list.
     *
     * @param list
     *            the list.
     * @return the hashcode
     */
    public String calcSHA1(final List<DetailAST> list) {
        try {
            if (list != null) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < list.size(); i++) {
                    stringBuilder.append(TokenTypes.getTokenName(list.get(i).getType()));
                    stringBuilder.append(" ");
                }

                byte[] digest = md.digest(stringBuilder.toString().getBytes());
                stringBuilder.setLength(0);
                for (byte b : digest) {
                    stringBuilder.append(String.format("%02x", b));
                }
                return stringBuilder.toString();
            }
        }
        catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}