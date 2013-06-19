
public class Matrix {
	private int r, c;
	public int[][] data;
	public Matrix(int r, int c)
	{
		this.r = r; this.c = c;
		this.data = new int[r][c];//default zeros
	}
	public void show() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) 
                System.out.printf("%4d", data[i][j]);
            System.out.println();
        }
        System.out.println();
    }
	public Matrix plus(Matrix B)
	{
		Matrix A = this;
        Matrix C = new Matrix(A.r, A.c);
        for (int i = 0; i < A.r; i++)
            for (int j = 0; j < A.c; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;	
	}
	
	public static Matrix zero = new Zero();
	public static Matrix row (Matrix A, Matrix B){
		if (A == zero && B == zero)
			return zero;
		else return new Row(A, B);
	}
	public static Matrix col (Matrix A,Matrix B){
		if (A == zero && B == zero)
			return zero;
		else return new Col(A, B);
	}
	public static Matrix one(Matrix A)
	{
		if(A == zero)
			return zero;
		else return new One(A);
	}
	public static Matrix quad (Matrix A,Matrix B, Matrix C, Matrix D){
		if (A == zero && B == zero && C == zero && D == zero )
			return zero;
		else return new Quad(A, B, C, D);
	}
	
	public static Matrix add(Matrix A, Matrix B)
	{
		if(A == zero) return B;
		if(B == zero) return A;
		if(A instanceof Row && B instanceof Row)
			return row(((Row) A).left.plus(((Row) B).left),((Row) A).right.plus(((Row) B).right));
		if(A instanceof Col && B instanceof Col)
			return col( ((Col) A).upper.plus( ((Col) B).upper), ((Col) A).lower.plus( ((Col) B).lower));
		else return null;
	}
	
	
	public static class One extends Matrix
	{
		public Matrix one;
		public One()
		{
			super(1,1);
			super.data[0][0] = 1;
		}
		public One(Matrix A)
		{
			super(A.r, A.c);
			this.one = A;
		}
	}
	public static class Zero extends Matrix
	{
		public Zero()
		{
			super(1,1);
		}
	}
	public static class Row extends Matrix
	{
		public Matrix left, right;
		public Row(Matrix left, Matrix right)
		{
			super(left.r, left.c+right.c);
			for(int i = 0; i< super.r;i++)
				for(int j = 0; j < super.c; j++)
					if(j< left.c)
						super.data[i][j] = left.data[i][j];
					else
						super.data[i][j] = right.data[i][j-left.c];
			this.left = left;
			this.right = right;
		}
//		public void setLeft(Matrix l){this.left = l;}
//		public void setRight(Matrix r){this.right = r;}
	}
	public static class Col extends Matrix
	{
		public Matrix upper, lower;
		public Col(Matrix upper, Matrix lower)
		{
			super(upper.r + lower.r, upper.c);
			for(int i = 0 ; i < super.r ; i ++)
				for(int j = 0 ; j < super.c ; j ++)
					if(i < upper.r)
						super.data[i][j] =upper.data[i][j];
					else
						super.data[i][j] = lower.data[i-upper.r][j];
			this.upper = upper;
			this.lower = lower;
		}
	}
	public static class Quad extends Matrix
	{
		public Quad(Matrix x, Matrix y, Matrix z, Matrix w)
		{
			super(x.r+y.r, x.c+z.c);
		}
	}
	
	//test
	public static void main(String args[])
	{
		
		Matrix x = new Matrix(1,1);
		x.data[0][0] = 1;
		Matrix y = new Matrix(1,3);
		y.data[0][2] = 1;
		Row r1 = new Row(x,y);
		r1.show();
		Matrix x2 = new Matrix(1,1);
		Matrix y2 = new Matrix(1,3);
		x2.data[0][0] = 2;
		y2.data[0][1] = 1;
		Row r2 = new Row(x2, y2);
		r2.show();
		Matrix result = add(r1,r2);
		((Row) result).left.show();
		
	}
}
