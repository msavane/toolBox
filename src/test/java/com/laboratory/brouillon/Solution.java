package com.laboratory.brouillon;

/*
Given an integer,n , perform the following conditional actions:

If  is odd, print Weird
If  is even and in the inclusive range of 2 to 5, print Not Weird
If  is even and in the inclusive range 6 of  to 20 , print Weird
If  is even and greater than 20 , print Not Weird

 */

import java.util.Scanner;

public class Solution {

    static boolean even;


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int N = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        if ( N % 2 == 0 ){
            even  = false;
            System.out.println(N + " Not Weird");

        }else if (2 <= N && N <= 5){
            even  = true;
            System.out.println(N + "  Weird");

        }else if (6 <= N && N <= 20){

            even  = true;
            System.out.println(N + " Weird");

        }else if (N > 20){

            even  = true;
            System.out.println(N + " weird");

        }else{

            even = false;
        }

        scanner.close();

        for(int i=1; i <= 10; i++)  {
            //prints table of the entered number
            System.out.println(N+" * "+i+" = "+N*i);
        }


    }
}

