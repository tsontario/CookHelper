package com.example.caitlin.cookhelper.database;

import java.util.LinkedList;
import java.util.Stack;

public class SQLParser {

    public static String generateSQLQuery(String q, String prefix) throws IllegalArgumentException {

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

        String result = "";
        LinkedList<String> copyStack = new LinkedList<>(operandStack);
        while (!copyStack.isEmpty()) {
            result += copyStack.removeLast() + ", ";
        }
        System.out.println(result);

        BinaryExpressionTree binaryExpressionTree1;
        BinaryExpressionTree binaryExpressionTree2;

        BinaryExpressionTree.Node node1;
        BinaryExpressionTree.Node node2;
        BinaryExpressionTree<String> newBinaryExpressionTree;


        // Now make the BinaryExpressionTree Stack
        Stack<BinaryExpressionTree> stack = new Stack<>();
        while (!operandStack.isEmpty()) {
            String element = operandStack.removeLast();
            if (isBinaryOperator(element)) {
                // Pop two operands
                newBinaryExpressionTree = new BinaryExpressionTree<>(element);
                binaryExpressionTree1 = stack.pop();
                binaryExpressionTree2 = stack.pop();
                node1 = binaryExpressionTree1.getRoot();
                node2 = binaryExpressionTree2.getRoot();

                // Make a new tree with operator at root
                newBinaryExpressionTree.getRoot().setLeft(node1);
                node1.setParent(newBinaryExpressionTree.getRoot());

                newBinaryExpressionTree.getRoot().setRight(node2);
                node2.setParent(newBinaryExpressionTree.getRoot());
                stack.push(newBinaryExpressionTree);

            }
            else if (isUnaryOperator(element)) {
                newBinaryExpressionTree = new BinaryExpressionTree<>(element);
                binaryExpressionTree1 = stack.pop();
                node1 = binaryExpressionTree1.getRoot();

                newBinaryExpressionTree.getRoot().setRight(node1);
                node1.setParent(newBinaryExpressionTree.getRoot());

                stack.push(newBinaryExpressionTree);
            }
            else {
                stack.push(new BinaryExpressionTree<>(element));
            }
        }


        BinaryExpressionTree root = stack.pop();
        return inOrder(root, prefix);
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


    private static String inOrder(BinaryExpressionTree binaryExpressionTree, String cond) {
        String result = "";
        BinaryExpressionTree.Node root = binaryExpressionTree.getRoot();
        if (root.getLeft() == null && root.getRight() == null) {
            return cond + " LIKE \"%" + root.getElement() + "%\" ";
        }

        return result + inOrder(root, cond);
    }
    private static String inOrder(BinaryExpressionTree.Node node, String cond) {
        String result = "";

        if (node.getParent() != null && isUnaryOperator((String)node.getParent().getElement())) {
            return "\"%" + node.getElement() + "%\"";
        }
        else if (isUnaryOperator((String) node.getElement())) {
            return cond + " NOT LIKE " + inOrder(node.getRight(), cond);
        }
        else if (node.isLeftChild() && node.isLeaf()) {
            result = cond + " LIKE \"%" + node.getElement() + "%\"";
            return result;
        }
        else if (node.isRightChild() && node.isLeaf()) {
            result = cond + " LIKE " + "\"%" + node.getElement() + "%\"";
            return result;
        }
        else {
            result += inOrder(node.getLeft(), cond);
            result += " " + node.getElement() + " ";
            result += inOrder(node.getRight(), cond);
        }
        return result ;
    }

}


