package com.company.processing_units.stats_processing_units;

import com.company.processing_units.common_processing_units.ClassCounterCPU;
import com.company.visitors.MethodDeclarationVisitor;
import com.company.visitors.TypeDeclarationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassWithMostAttributesSPU extends StatisticProcessingUnit{

    double percent;
    List<DeclarationSet> order = new ArrayList<DeclarationSet>();

    public ClassWithMostAttributesSPU(double percent) {
        if(percent > 1) {
            this.percent = 1;
        } else if(percent < 0) {
            this.percent = 0;
        } else this.percent = percent;
        description = this.percent * 100 + " % des classes ayant le plus d'attributs :";
    }

    @Override
    protected void process() {

        ClassCounterCPU classCounterCPU = new ClassCounterCPU();
        classCounterCPU.register(compilationUnits);
        int classCount = Integer.parseInt(classCounterCPU.getResult());
        int toKeep = (int) Math.ceil(classCount * percent);

        List<DeclarationSet> sortedClass = new ArrayList<DeclarationSet>();

        for (CompilationUnit unit :
                compilationUnits) {
            TypeDeclarationVisitor visitor = new TypeDeclarationVisitor();
            unit.accept(visitor);
            for (TypeDeclaration dec :
                    visitor.getTypes()) {
                sortedClass.add(new DeclarationSet(dec.getFields().length, dec));
            }
        }
        Collections.sort(sortedClass);
        Collections.reverse(sortedClass);
        order = sortedClass;
        result = "\n";
        for (int i = 0; i < toKeep; i++) {
            result += sortedClass.get(i).declaration.getName() + " => " + sortedClass.get(i).attCount+" attributs;\n";
        }
    }

    public List<DeclarationSet> getOrder() {
        process();
        return this.order;
    }

    class DeclarationSet implements Comparable<DeclarationSet> {

        int attCount;
        TypeDeclaration declaration;

        public DeclarationSet(int methodCount, TypeDeclaration declaration) {
            this.attCount = methodCount;
            this.declaration = declaration;
        }

        @Override
        public int compareTo(DeclarationSet o) {
            //if (methodCount == o.methodCount) return 0;
            return attCount - o.attCount;
        }
    }
}
