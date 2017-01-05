package com.github.kmizu.nub;

import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by nomaddo on 2017/01/04.
 */
public class Translator implements AstNode.ExpressionVisitor<List<IL>> {
    private int id = 0;
    private HashMap<String, Integer> compEnv = new HashMap<>();

    private int gensym()
    {
        id++;
        return id;
    }

    public String compile(AstNode.Expression program) {
        return program.accept(this).toString();
    }

    @Override
    public List<IL> visitBinaryOperation(AstNode.BinaryOperation node) {
        List<IL> list = new ArrayList<>();
        IL il = null;
        list.addAll(node.lhs().accept(this));
        list.addAll(node.rhs().accept(this));

        switch (node.operator())
        {
            case "+":
                il = new IL.Iadd();
            case "-":
                il = new IL.Isub();
            case "*":
                il = new IL.Imul();
            case "/":
                il = new IL.Idiv();
            default:
                assert(false);
        }

        list.add(il);
        return list;
    }

    @Override
    public List<IL> visitNumber(AstNode.Number node) {
        List<IL> list = new ArrayList<>(10);
        list.add(new IL.Ldc (node.value()));
        return list;
    }

    @Override
    public List<IL> visitLetExpression(AstNode.LetExpression node) {
        List<IL> list = new ArrayList<>(10);
        list.addAll(node.expression().accept(this));
        Integer id = gensym();
        list.add(new IL.Istore(id));
        compEnv.put(node.variableName(), id);
        return list;
    }

    @Override
    public List<IL> visitIdentifier(AstNode.Identifier node) {
        List<IL> list = new ArrayList<>(10);
        Integer id = compEnv.get(node.name());
        IL il = new IL.Iload(id);
        list.add(il);
        return list;
    }

    @Override
    public List<IL> visitPrintExpression(AstNode.PrintExpression node) {
        List<IL> list = new ArrayList<>();
        list.addAll(node.target().accept(this));
        list.add(new IL.Print());
        return list;
    }

    @Override
    public List<IL> visitExpressionList(AstNode.ExpressionList node) {
        List<IL> list = new ArrayList<>();
        for (AstNode.Expression e: node.expressions()){
            list.addAll(e.accept(this));
        }
        return list;
    }

    @Override
    public List<IL> visitIfExpression(AstNode.IfExpression node) {
        return null;
    }

    @Override
    public List<IL> visitWhileExpression(AstNode.WhileExpression node) {
        return null;
    }

    @Override
    public List<IL> visitForExpression(AstNode.ForExpression node) {
        return null;
    }

    @Override
    public List<IL> visitAssignmentOperation(AstNode.AssignmentOperation node) {
        return null;
    }

    @Override
    public List<IL> visitPrintlnExpression(AstNode.PrintlnExpression node) {
        return null;
    }

    @Override
    public List<IL> visitDefFunction(AstNode.DefFunction node) {
        return null;
    }

    @Override
    public List<IL> visitFunctionCall(AstNode.FunctionCall node) {
        return null;
    }

    @Override
    public List<IL> visitReturn(AstNode.Return node) {
        return null;
    }
}
