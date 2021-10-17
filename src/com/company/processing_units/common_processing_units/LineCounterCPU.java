package com.company.processing_units.common_processing_units;

import com.company.visitors.MethodDeclarationVisitor;
import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.Arrays;
import java.util.List;

public class LineCounterCPU extends CommonProcessingUnit {

    public LineCounterCPU() {
        this.description = "Nombre de lignes de code :";
    }

    @Override
    protected void process() {
        int accumulator = 0;
        List<String> temp = Arrays.asList(compilationUnits.toString().split("\n"));
        accumulator += temp.size();
        result = String.valueOf(accumulator);

    }
}
