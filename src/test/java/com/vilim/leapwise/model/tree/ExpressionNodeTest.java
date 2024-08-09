package com.vilim.leapwise.model.tree;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionNodeTest {

    @Test
    void evaluate_withSimpleEquality() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.firstName");
        node.setRelationalOperator("==");
        node.setRoValue2("JOHN");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.firstName", "john");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "terry");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withSimpleInequality() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.firstName");
        node.setRelationalOperator("!=");
        node.setRoValue2("JOHN");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.firstName", "terry");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "john");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withSimpleGreaterThan() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.firstName");
        node.setRelationalOperator(">");
        node.setRoValue2("JOHN");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.firstName", "JOHNNY");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "JOHN");
        assertFalse(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "JOH");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withSimpleGreaterThanOrEqual() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.firstName");
        node.setRelationalOperator(">=");
        node.setRoValue2("JOHN");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.firstName", "JOHNNY");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "JOHN");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.firstName", "JOH");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withSimpleLessThan() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.salary");
        node.setRelationalOperator("<");
        node.setRoValue2("999.9");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.salary", "99.9");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.salary", "999.9");
        assertFalse(node.evaluate(flatJson));

        flatJson.put("customer.salary", "1000");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withSimpleLessThanOrEqual() {
        ExpressionNode node = new ExpressionNode();
        node.setRoValue1("customer.salary");
        node.setRelationalOperator("<=");
        node.setRoValue2("999.9");

        Map<String, String> flatJson = new HashMap<>();

        flatJson.put("customer.salary", "99.9");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.salary", "999.9");
        assertTrue(node.evaluate(flatJson));

        flatJson.put("customer.salary", "1000");
        assertFalse(node.evaluate(flatJson));
    }

    @Test
    void evaluate_withComplexStructure() {
        ExpressionNode leftChild = new ExpressionNode();
        leftChild.setRoValue1("customer.firstName");
        leftChild.setRelationalOperator("==");
        leftChild.setRoValue2("JOHN");

        ExpressionNode rightChild = new ExpressionNode();
        rightChild.setRoValue1("customer.lastName");
        rightChild.setRelationalOperator("!=");
        rightChild.setRoValue2("TERRY");

        ExpressionNode parent = new ExpressionNode();
        parent.setLogicalOperator("AND");
        parent.setChildLeft(leftChild);
        parent.setChildRight(rightChild);

        Map<String, String> flatJson = new HashMap<>();
        flatJson.put("customer.firstName", "JOHN");
        flatJson.put("customer.lastName", "DOE");

        assertTrue(parent.evaluate(flatJson));

        flatJson.put("customer.firstName", "SAM");
        flatJson.put("customer.lastName", "TERRY");
        assertFalse(parent.evaluate(flatJson));
    }
}