package com.company.processing_units.common_processing_units;

import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class AverageAttributCountPerClassCPU extends CommonProcessingUnit {

    public AverageAttributCountPerClassCPU() {
        this.description = "Nombre moyen d'attributs par classe :";
    }

    @Override
    protected void process() {
        double accField = 0, accType = 0;
        for (CompilationUnit unit :
                compilationUnits) {
            TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
            unit.accept(visitor);
            for (TypeDeclaration dec : visitor.getTypes()) {
                accType++;
                for (FieldDeclaration field : dec.getFields()) {
                    accField++;
                }
            }
        }
        result = String.valueOf(accField / accType);
    }
}
