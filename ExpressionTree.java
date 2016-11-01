import java.util.NoSuchElementException;

/**
 * Represents an equation as a tree of binary expressions.
 * @author Maryam Husain
 * @version 10/31/2016
 */
public class ExpressionTree {
	ExpressionNode root; //the root of our expression tree
	boolean parseSucceeded;
	
	public ExpressionTree(String expressionString) {
		try {
			root = parseString(expressionString);
			parseSucceeded = true;
		} catch(IllegalArgumentException e) {
			System.out.println("Error: Invalid postfix expression");
			parseSucceeded = false;
		}
	}
	
	/**
	 * Parses an expression in the form of a string into an expression tree.
	 * @param expressionString - the string we want to convert into an expression tree
	 * @return the root of the expression tree
	 */
	private ExpressionNode parseString(String expressionString) {
		MyStack<ExpressionNode> expressionStack = new MyStack<ExpressionNode>();
		String[] expressions = expressionString.split(" ");
		
		for(String op: expressions) {
			// if we encounter an operator, create an expression node and pop the previous two nodes on
			// the stack to be its operands. Otherwise, just push the operand as a node onto the stack.
			if(isOperator(op)){
				try {
					expressionStack.push(new ExpressionNode(op, expressionStack.pop(), expressionStack.pop()));
				} catch(NoSuchElementException e) {
					// if we pop an empty stack, throw an exception
					throw new IllegalArgumentException();
				}
			} else {
				//make sure the expression is actually a number before pushing it onto the stack
				try {
					Integer.parseInt(op);
					expressionStack.push(new ExpressionNode(op));
				} catch (Exception e) {
					System.out.println("Error: Invalid operand " + op);
					throw new IllegalArgumentException();
				}
			}
		}
		
		// If all works out as planned, there should be only one node remaining on our stack. This should
		// be the root. If not, the expression is bad - throw an exception.
		if(expressionStack.size() == 1) {
			return expressionStack.pop();
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Return whether or not a string is an operator character.
	 * @param op - the string to check
	 * @return whether or not the string is an operator character
	 */
	private boolean isOperator(String op) {
		return (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"));
	}
	
	/**
	 * Return whether or not the data of a node is an operator character.
	 * @param op - the node to check
	 * @return whether or not the node's data is an operator character
	 */
	private boolean isOperator(ExpressionNode op) {
		return (op.data.equals("+") || op.data.equals("-") || op.data.equals("*") || op.data.equals("/"));
	}
	
	/**
	 * Evaluate the whole expression tree.
	 * @return the result of the evaluation
	 */
	public int eval() {
		if(!parseSucceeded) {
			System.out.println("Error: Bad expression - cannot evaluate");
			return 0;
		}
		
		return eval(root);
	}
	
	/**
	 * Evaluate an expression subtree.
	 * @param node - the root of the subtree to evaluate
	 * @return the result of the evalution
	 */
	private int eval(ExpressionNode node) {
		if(isOperator(node)) {
			switch(node.data) {
				case "+":
					return eval(node.leftChild) + eval(node.rightChild);
				case "-":
					return eval(node.leftChild) - eval(node.rightChild);
				case "*":
					return eval(node.leftChild) * eval(node.rightChild);
				case "/":
					return eval(node.leftChild) / eval(node.rightChild);
				default:
					System.out.println("Error: Invalid expression tree");
					return 0;
			}
		} else {
			return Integer.parseInt(node.data);
		}
	}
	
	/**
	 * Print the whole expression tree in postfix
	 * @return the expression tree in postfix
	 */
	public String postfix() {
		if(!parseSucceeded) {
			return "Bad Expression - cannot print";
		}
		
		return postfix(root).trim();
	}
	
	/**
	 * Print a subtree in postfix
	 * @param node - the root of the subtree to print
	 * @return the subtree in postfix
	 */
	private String postfix(ExpressionNode node) {
		if(isOperator(node)) {
			switch(node.data) {
				case "+":
					return " " + postfix(node.leftChild) + " " + postfix(node.rightChild) + " +";
				case "-":
					return " " + postfix(node.leftChild) + " " + postfix(node.rightChild) + " -";
				case "*":
					return " " + postfix(node.leftChild) + " " + postfix(node.rightChild) + " *";
				case "/":
					return " " + postfix(node.leftChild) + " " + postfix(node.rightChild) + " /";
				default:
					System.out.println("Error: Invalid expression tree");
					return "#";
			}
		} else {
			return node.data;
		}
	}
	
	/**
	 * Print the whole expression tree in prefix
	 * @return the expression tree in prefix
	 */
	public String prefix() {
		if(!parseSucceeded) {
			return "Bad Expression - cannot print";
		}
		
		return prefix(root).trim();
	}
	
	/**
	 * Print a subtree in prefix
	 * @param node - the root of the subtree to print
	 * @return the subtree in prefix
	 */
	private String prefix(ExpressionNode node) {
		if(isOperator(node)) {
			switch(node.data) {
				case "+":
					return " +" + prefix(node.leftChild) + prefix(node.rightChild);
				case "-":
					return " -" + prefix(node.leftChild) + prefix(node.rightChild);
				case "*":
					return " *" + prefix(node.leftChild) + prefix(node.rightChild);
				case "/":
					return " /" + prefix(node.leftChild) + prefix(node.rightChild);
				default:
					System.out.println("Error: Invalid expression tree");
					return "#";
			}
		} else {
			return " " + node.data;
		}
	}
	
	/**
	 * Print the whole expression tree in infix
	 * @return the expression tree in infix
	 */
	public String infix() {
		if(!parseSucceeded) {
			return "Bad Expression - cannot print";
		}
		
		return infix(root);
	}
	
	/**
	 * Print a subtree in infix
	 * @param node - the root of the subtree to print
	 * @return the subtree in infix
	 */
	public String infix(ExpressionNode node) {
		if(isOperator(node)) {
			switch(node.data) {
				case "+":
					return "(" + infix(node.leftChild) + " + " +  infix(node.rightChild) + ")";
				case "-":
					return "(" + infix(node.leftChild) + " - " +  infix(node.rightChild) + ")";
				case "*":
					return infix(node.leftChild) + " * " +  infix(node.rightChild);
				case "/": //ADD TRY/CATCH ARITHMETIC EXCEPTION HERE
					return infix(node.leftChild) + " / " +  infix(node.rightChild);
				default:
					System.out.println("Error: Invalid expression tree");
					return "#";
			}
		} else {
			return node.data;
		}
	}
	

	// ExpressionNode Class
	public class ExpressionNode {
		String data;
		ExpressionNode rightChild;
		ExpressionNode leftChild;
	
		public ExpressionNode(String data) {
			this.data = data;
		}
		
		public ExpressionNode(String data, ExpressionNode rightChild, ExpressionNode leftChild) {
			this.data = data;
			this.rightChild = rightChild;
			this.leftChild = leftChild;
		}
	}

}
