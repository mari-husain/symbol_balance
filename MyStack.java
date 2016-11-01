import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * An implementation of a stack using a LinkedList.
 * @author Mari Husain
 * @version October 2nd, 2016
 */
public class MyStack<AnyType> {
	public LinkedList<AnyType> myStack = new LinkedList<AnyType>(); //the list in which we store all our data
	
	/**
	 * Checks whether or not the stack is empty.
	 * @return whether or not the stack is empty
	 */
	public boolean isEmpty() {
		return myStack.isEmpty();
	}
	
	/**
	 * Checks whether or not the stack is empty.
	 * @return whether or not the stack is empty
	 */
	public int size() {
		return myStack.size();
	}
	
	
	/**
	 * Adds an element to the top of the stack.
	 * @param a - the element we want to add to the stack
	 */
	public void push(AnyType a) {
		myStack.add(0, a);
	}
	
	/**
	 * Remove and return the element on top of the stack.
	 * @return the element on top of the stack
	 */
	public AnyType pop() {
		//if the list is empty, there's nothing to return - throw an exception
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			AnyType data = myStack.get(0);
			myStack.remove(0);
			return data;
		}
	} 
	
	/**
	 * Look at the element on top of the stack without removing it from the stack.
	 * @return the element on top of the stack
	 */
	public AnyType peek() {
		//if the list is empty, there's nothing to return - throw an exception
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return myStack.get(0);
		}
	}
}