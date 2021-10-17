package com.company.processing_units.stats_processing_units;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassWithMostAttrAndMethodsSPU extends StatisticProcessingUnit {

    double percent;

    public ClassWithMostAttrAndMethodsSPU(double percent) {
        if (percent > 1) {
            this.percent = 1;
        } else if (percent < 0) {
            this.percent = 0;
        } else this.percent = percent;
        description = this.percent * 100 + " % des classes ayant le plus d'attributs et le plus de méthodes:";
    }

    @Override
    protected void process() {
        ClassWithMostMethodsSPU mostMeth = new ClassWithMostMethodsSPU(this.percent);
        ClassWithMostAttributesSPU mostAttr = new ClassWithMostAttributesSPU(this.percent);
        mostAttr.register(compilationUnits);
        mostMeth.register(compilationUnits);

        List<ClassWithMostAttributesSPU.DeclarationSet> mostAttrOrder = mostAttr.getOrder();
        List<ClassWithMostMethodsSPU.ClassDeclarationSet> mostMethOrder = mostMeth.getOrder();

        int toKeep = (int) Math.ceil(mostAttrOrder.size() * percent);

        List<ClassWithMostAttributesSPU.DeclarationSet> mostAttrOrderPercent = mostAttrOrder.subList(0, toKeep);
        List<ClassWithMostMethodsSPU.ClassDeclarationSet> mostMethOrderPercent = mostMethOrder.subList(0, toKeep);

        List<TypeDeclaration> clas = new ArrayList<TypeDeclaration>();
        for (int i = 0; i < toKeep; i++) {
            for (int j = 0; j < mostAttrOrderPercent.size(); j++) {
                for (int k = 0; k < mostMethOrderPercent.size(); k++) {
                    if (mostAttrOrderPercent.get(j).declaration.getName().equals(mostMethOrderPercent.get(k).declaration.getName())) {
                        if (!clas.contains(mostMethOrderPercent.get(i).declaration)) {
                            clas.add(mostMethOrderPercent.get(i).declaration);
                        }
                    }
                }
            }
        }

        result = "\n";
        for (TypeDeclaration dec :
                clas) {
            result += dec.getName() + "\n";
        }
    }
}
