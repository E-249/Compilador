package planning;

public interface Planning {
	
	public int getIzqType();
	public int getOperator();
	public int getDerType();
	
	public boolean isSame();
	public boolean isSameType();
	
	public boolean isInUse();
	
	
	
	public static enum ArgType { REG, ACC, IMM }
	public static enum OpType  { MATH, CMP, LOGIC }

}
