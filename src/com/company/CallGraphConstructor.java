package com.company;

import com.company.processing_units.common_processing_units.CommonProcessingUnit;
import com.company.visitors.MethodDeclarationVisitor;
import com.company.visitors.MethodInvocationVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class CallGraphConstructor extends CommonProcessingUnit {
    List<JTree> trees = new ArrayList<JTree>();

    public CallGraphConstructor() {
    }

    public void showCallGraph() {
        JFrame frame = new JFrame();

    }

    private void displayTree() {
        for (JTree tree :
                trees) {
            JFrame jFrame = new JFrame();
            jFrame.add(tree);
            jFrame.setSize(550, 400);
            jFrame.setVisible(true);
        }

    }

    @Override
    protected void process() {
        for (CompilationUnit unit :
                compilationUnits) {
            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            unit.accept(visitor);
            for (MethodDeclaration dec :
                    visitor.getMethods()) {
                trees.add(createTreeForMethod(dec));
            }
        }
        displayTree();
    }


    private JTree createTreeForMethod(MethodDeclaration method) {
        //La racine de notre arbre qui est la déclaration de la méthode
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(method.getName());
        MethodInvocationVisitor visitor = new MethodInvocationVisitor();
        method.accept(visitor);
        for (MethodInvocation invoc :
                visitor.getMethods()) {
            List<MethodInvocation> visited = new ArrayList<MethodInvocation>();
            //visited.add(invoc);
            recursiveSearch(root, invoc, visited);
        }
        return new JTree(root);
    }

    void recursiveSearch(DefaultMutableTreeNode previousNode, MethodInvocation current, List<MethodInvocation> visited) {
        MethodInvocationVisitor visitor = new MethodInvocationVisitor();
        current.accept(visitor);
        if (visited.contains(current)) return;
        for (MethodInvocation invoc :
                visitor.getMethods()) {
            visited.add(invoc);
            DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(invoc.getName());
            previousNode.add(toAdd);
            recursiveSearch(toAdd, invoc, visited);
        }
    }
}
