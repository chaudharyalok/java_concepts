package in.alok.rule21D;

import java.util.LinkedList;

public class Ds {
	
	private static class Node {
		int data;
		Node next;
		Node prev;
		
		Node(){
			
		}
		
		Node(int data){
			this.data = data;
		}
		
	}
	
	static Node pointer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ds ds = new Ds();
		ds.addElement(3);
		ds.addElement(6);
		
		ds.traverse(pointer);
		ds.removeLast();
		System.out.println();
		ds.traverse(pointer);
		System.out.println();
		ds.removeLast();
		ds.traverse(pointer);
	}
	
	private void addElement(int data){
		// linkedlist is empty
		
		if(pointer == null) {
			pointer = new Node(data);
		}
		else {
		while(pointer.next!= null) {
			pointer = pointer.next;
		}
		pointer.next = new Node(data);
		}
	}
	
	private void traverse(Node pointer) {
		
		if(pointer == null) {
			System.out.println("Linked list is empty");
		}
		else {
			while(pointer != null) {
				System.out.print(pointer.data + "->");
				
				pointer = pointer.next;
			}
		}
	}
	
	private void removeLast() {
		Node first = pointer;
		if(pointer == null) {
			System.out.println("Linked list is empty");
		} else if(pointer.next == null) {
			pointer = null;
		}
		else {
			Node previous = null;
			while(pointer.next != null) {
				previous = pointer;
				pointer = pointer.next;
			}
			previous.next = null;
			pointer = first;
		}
		
	}
	

	
	

}
