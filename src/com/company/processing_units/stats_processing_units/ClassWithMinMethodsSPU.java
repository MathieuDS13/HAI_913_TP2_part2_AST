package com.company.processing_units.stats_processing_units;

import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassWithMinMethodsSPU extends StatisticProcessingUnit {

    int minMeth;

    public ClassWithMinMethodsSPU(int minMeth) {
        this.minMeth = minMeth;
        this.description = "Classes ayant plus de " + this.minMeth + " méthodes :";
    }

    @Override
    protected void process() {
        List<TypeDeclaration> validClass = new ArrayList<TypeDeclaration>();

        for (CompilationUnit unit :
                compilationUnits) {
            TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
            unit.accept(visitor);

            for (TypeDeclaration dec :
                    visitor.getTypes()) {
                if (dec.getMethods().length > minMeth) {
                    validClass.add(dec);
                }
            }
        }

        result = "\n";
        for(TypeDeclaration dec : validClass) {
            result += dec.getName() + "\n";
        }
    }
}
