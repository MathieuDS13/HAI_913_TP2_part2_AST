package com.company.processing_units.common_processing_units;

public class AverageMethPerClassCPU extends CommonProcessingUnit{

    ClassCounterCPU classCounterCPU;
    MethodCounterCPU methodCounterCPU;

    public AverageMethPerClassCPU() {
        this.description = "Nombre moyen de méthodes par classe :";
        this.classCounterCPU = new ClassCounterCPU();
        this.methodCounterCPU = new MethodCounterCPU();
    }

    @Override
    protected void process() {
        classCounterCPU.register(compilationUnits);
        methodCounterCPU.register(compilationUnits);
        double classCount, methodCount;
        classCount = Double.parseDouble(classCounterCPU.getResult());
        methodCount = Double.parseDouble(methodCounterCPU.getResult());
        double res = methodCount / classCount;
        result = String.valueOf(res);
    }
}
