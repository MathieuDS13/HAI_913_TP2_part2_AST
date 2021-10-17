package com.company;

import com.company.processing_units.ProcessingUnit;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Processor {

    public String projectPath;
    public String projectSourcePath;
    public String jrePath;

    List<ProcessingUnit> units;
    List<CompilationUnit> compilationUnits;

    public Processor(String projectPath, String jrePath) {
        this.projectPath = projectPath;
        this.jrePath = jrePath;
        this.projectSourcePath = projectPath + "\\src";
        this.units = new ArrayList<ProcessingUnit>();
        this.compilationUnits = new ArrayList<CompilationUnit>();
    }

    public void addProcessingUnit(ProcessingUnit unit) {
        units.add(unit);
    }

    public List<String> getResults() throws IOException {
        this.compilationUnits = getCompilationUnits();
        registerCU();

        List<String> results = new ArrayList<String>();
        for (ProcessingUnit unit :
                units) {
            results.add(unit.getVerboseResult());
        }
        return results;
    }

    private void registerCU() {
        for (ProcessingUnit unit :
                units) {
            unit.register(this.compilationUnits);
        }
    }

    private ArrayList<File> listJavaFilesForFolder(final File folder) {
        ArrayList<File> javaFiles = new ArrayList<File>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                javaFiles.addAll(listJavaFilesForFolder(fileEntry));
            } else if (fileEntry.getName().contains(".java")) {
                // System.out.println(fileEntry.getName());
                javaFiles.add(fileEntry);
            }
        }

        return javaFiles;
    }

    private List<CompilationUnit> getCompilationUnits() throws IOException {
        // read java files
        final File folder = new File(projectSourcePath);
        ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
        List<CompilationUnit> units = new ArrayList<CompilationUnit>();
        //
        for (File fileEntry : javaFiles) {
            String content = FileUtils.readFileToString(fileEntry);
            // System.out.println(content);

            CompilationUnit parse = parse(content.toCharArray());
            units.add(parse);
        }
        return units;
    }

    // create AST
    private CompilationUnit parse(char[] classSource) {
        ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setBindingsRecovery(true);

        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        parser.setUnitName("");

        String[] sources = {projectSourcePath};
        String[] classpath = {jrePath};

        parser.setEnvironment(classpath, sources, new String[]{"UTF-8"}, true);
        parser.setSource(classSource);

        return (CompilationUnit) parser.createAST(null); // create and parse
    }
}
