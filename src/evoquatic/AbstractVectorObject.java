package evoquatic;

public abstract class AbstractVectorObject {
	
	public AbstractVectorObject(AbstractNodeObject n1, AbstractNodeObject n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	
	boolean collides = false;
	
	AbstractNodeObject n1;
	AbstractNodeObject n2;
	
	public void code(int time) {}
}
