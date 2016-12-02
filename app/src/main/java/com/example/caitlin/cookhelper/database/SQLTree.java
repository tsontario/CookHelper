package com.example.caitlin.cookhelper.database;

import java.util.LinkedList;
import java.util.Stack;

public class SQLTree {

    public static void main(String[] args) {
        generateSQLQuery("a OR (b AND c) OR d");
    }

    private static String generateSQLQuery(String q) throws IllegalArgumentException {

        // This regex tokenizes our String properly (but still needs trimming).
        String tokenizer = "(?=AND)|(?<=AND )|(?=OR)|(?<=OR )|(?=NOT)|(?<=NOT )|(?=\\()|(?<=\\()|(?=\\))|(?<=\\) )";
        String[] elements = q.split(tokenizer);
        for (int i=0; i<elements.length; i++) {
            elements[i] = elements[i].trim();
        }

        // Convert to Postfix expression
        LinkedList<String> operandStack = new LinkedList<>();
        Stack<String> operatorStack = new Stack<>();
        String operator;

        boolean unaryFlag = false;

        /** Constructing the postfix Expression */
        for (String el : elements) {
            if (unaryFlag) {
                operandStack.push(el);
                operandStack.push(operatorStack.pop());
                unaryFlag = false;
            }

            else if (isLeftParens(el)) {
                operatorStack.push(el);
            }
            else if (isRightParens(el)) {
                while(!isLeftParens(operatorStack.peek())) {
                    operator = operatorStack.pop();

                    operandStack.push(operator);
                }
                // remove the left parens from the stack
                operatorStack.pop();
            }

            else if (isUnaryOperator(el)) {
                unaryFlag = true;
                operatorStack.push(el);
            }

            else if (isBinaryOperator(el)) {
                while(!operatorStack.empty() && hasPrecedence(operatorStack.peek())) {
                    operator = operatorStack.pop();
                    operandStack.push(operator);
                }
                operatorStack.push(el);
            }
            else {
                operandStack.push(el);
            }

        }

        while (!operatorStack.empty()) {
            operandStack.push(operatorStack.pop());
        }

        /** COMMENTED OUT --- CHECKS POSTFIX CONVERSION ALGORITHM
         *
         */

//        String result = "";
//        LinkedList<String> copyStack = new LinkedList<>(operandStack);
//        while (!operandStack.isEmpty()) {
//            result += copyStack.removeLast() + ", ";
//        }
//        System.out.println(result);

        Tree tree1;
        Tree tree2;

        Tree.Node node1;
        Tree.Node node2;
        Tree<String> newTree;


        // Now make the Tree Stack
        Stack<Tree> stack = new Stack<>();
        while (!operandStack.isEmpty()) {
            String element = operandStack.removeLast();
            if (isBinaryOperator(element)) {
                // Pop two operands
                newTree = new Tree<>(element);
                tree1 = stack.pop();
                tree2 = stack.pop();
                node1 = tree1.getRoot();
                node2 = tree2.getRoot();

                // Make a new tree with operator at root
                newTree.getRoot().setLeft(node1);
                node1.setParent(newTree.getRoot());

                newTree.getRoot().setRight(node2);
                node2.setParent(newTree.getRoot());
                stack.push(newTree);

            }
            else if (isUnaryOperator(element)) {
                newTree = new Tree<>(element);

            }
            else {
                stack.push(new Tree<>(element));
            }
        }


        /** Once you have a tree, do an inOrder traversal, and at every operand that is a left child, place a left parens,
         * and at every operand that is a right child, place a right parens. worry about unary operator later (NOT)
         *
         */

        return "";
    }

    private static boolean isBinaryOperator(String s) {
        return (s.equals("AND") || s.equals("OR"));
    }

    private static boolean hasPrecedence(String s1) {
        // TODO Method stub
        return (s1.equals("AND"));
    }

    private static boolean isUnaryOperator(String s) {
        return s.equals("NOT");
    }

    private static boolean isLeftParens(String s) {
        return s.equals("(");
    }

    private static boolean isRightParens(String s) {
        return s.equals(")");
    }

    private static void inOrder(Tree.Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft());
        System.out.print(" " + node.getElement());
        inOrder(node.getRight());

    }
}
