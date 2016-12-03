package com.example.caitlin.cookhelper.database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * A (static) utility class for generating SQL statements and gathering auxiliary information
 * regarding queries. The main motivation of this class is to translate infix user-entered
 * queries into binary expression trees to ease in translating queries to SQL. The tree is
 * also used to get additional information about operands (leaf nodes) for ranking query results.
 */

public class SQLParser {

    /**
     * Converts a well-formed infix boolean expression to it's corresponding SQL select statement
     * that searches the table given by prefix. rankArgs is a secondary structure that records
     * information useful for ranking search results.
     * @param q The raw infix boolean expression
     * @param prefix The SQLite syntax for forming the expression (e.g. "WHERE col_name LIKE ..."
     * @param rankArgs An ArrayList holding the search criteria to be used for ranking
     * @return A well-formed SQL query
     * @throws IllegalArgumentException
     */
    public static String generateSQLQuery(String q, String prefix, ArrayList<String> rankArgs)
            throws IllegalArgumentException {

        // This regex tokenizes our String properly (but still needs trimming).
        String tokenizer = "(?=AND)|(?<=AND )|(?=OR)|(?<=OR )|(?=NOT)|(?<=NOT )|(?=\\()|(?<=\\()|(?=\\))|(?<=\\) )";
        String[] elements = q.split(tokenizer);
        for (int i=0; i<elements.length; i++) {
            elements[i] = elements[i].trim();
        }

        // Convert to Postfix expression
        LinkedList<String>postFixTree = convertToPostfix(elements);
        // Create Binary Expression Tree
        Stack<BinaryExpressionTree> stack = getBinaryExpressionTree(postFixTree);

        //Traverse the Expression Tree
        BinaryExpressionTree root = stack.pop();
        leafOrder(root, rankArgs);
        return inOrder(root, prefix);
    }

    /**
     * Return a tree representing the given infix expression.
     * @param inFixList A linked list representing an infix algebraic expression
     * @return A stack containing one element: a tree whose inorder traversal represents the given
     *          infix algebraic expression
     * @throws EmptyStackException
     */
    public static Stack<BinaryExpressionTree> getBinaryExpressionTree(LinkedList<String> inFixList)
    throws EmptyStackException {
        BinaryExpressionTree binaryExpressionTree1;
        BinaryExpressionTree binaryExpressionTree2;

        BinaryExpressionTree.Node node1;
        BinaryExpressionTree.Node node2;
        BinaryExpressionTree<String> newBinaryExpressionTree;


        // Now make the BinaryExpressionTree Stack
        Stack<BinaryExpressionTree> stack = new Stack<>();
        while (!inFixList.isEmpty()) {
            String element = inFixList.removeLast();
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
        return stack;
    }


    /**
     * Converts an array representing an infix expression into a postfix expression. The precedence
     * and operators are defined specifically within the class, though could be extracted and
     * generified elsewhere.
     * @param elements An array representing an infix expression of operands and operators
     * @return
     */
    private static LinkedList<String> convertToPostfix(String[] elements) {
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
        return operandStack;
    }

    /**
     * Utility methods
     */
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


    /**
     * Traversals. inOrder yields a legal SQL statement, leafOrder iterates over all leaf values
     * (guaranteed to be operands) to be used for subsequent ranking of search results.
     */
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

    private static ArrayList<String> leafOrder(BinaryExpressionTree binaryExpressionTree,
                                                 ArrayList<String> rankArgs) {
        BinaryExpressionTree.Node root = binaryExpressionTree.getRoot();
        if (root.getLeft() == null && root.getRight() == null) {
            rankArgs.add("");
        }

        leafOrder(root, rankArgs);
        return rankArgs;
    }
    private static void leafOrder(BinaryExpressionTree.Node node, ArrayList<String> rankArgs) {

        if (node == null) {
            return;
        }
        if (node.getParent() != null && isUnaryOperator((String)node.getParent().getElement())) {
            return;
        }
        else if ((node.isLeftChild() || node.isRightChild()) && node.isLeaf()) {
            rankArgs.add(""+node.getElement());
            return;
        }
        else {
            leafOrder(node.getLeft(), rankArgs);
            leafOrder(node.getRight(), rankArgs);
        }
        return;
    }

}


