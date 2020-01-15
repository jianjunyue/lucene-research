package org.lucene.research.vectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	float f=12.110f;
    	int i=Float.floatToIntBits(f);
    	float f2=Float.intBitsToFloat(i);
        System.out.println(i );
        System.out.println(f2 );
        System.out.println( "Hello World!" );
    }
}
